package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.store.ChannelGroupStore;
import cn.kj120.im.server.store.ChannelHandlerContextCache;
import cn.kj120.im.server.config.ChannelAttr;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Slf4j
@ChannelHandler.Sharable
@Component
public class ReadHandler extends SimpleChannelInboundHandler<SendMessage> {

    @Autowired
    private ChannelGroupStore channelGroupStore;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage msg) throws Exception {
        log.info("服务端收到信息: {}", msg);

        ReceiveMessage receiveMessage = getReceiveMessage(ctx.channel(), msg);

        Collection<Channel> ctxs = new ArrayList<>();
        switch (msg.getSendMessageType()) {
            case UNICAST:
                msg.getTos().forEach(sessionId -> {
                    ctxs.add(channelGroupStore.get(sessionId));
                });
                forward(ctxs, receiveMessage);
                break;
            case MULTICAST:
                msg.getTos().forEach(sessionId ->
                    ctxs.add(channelGroupStore.get(sessionId))
                );
                forward(ctxs, receiveMessage);
                break;
            case ALL:
                forward(channelGroupStore.getAll(), receiveMessage);
                break;
            default:
                log.error("为定义的消息类型: {}", msg);
        }
    }

    public void forward(Collection<Channel> contexts, ReceiveMessage message) {
        contexts.forEach(ctx -> ctx.writeAndFlush(message));
    }

    public ReceiveMessage getReceiveMessage(Channel ctx, SendMessage msg) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setFrom(ctx.attr(ChannelAttr.SESSION).get().getSessionId());
        receiveMessage.setTime(System.currentTimeMillis());
        receiveMessage.setBody(msg.getBody() + new Random().nextInt(100));
        return receiveMessage;
    }
}
