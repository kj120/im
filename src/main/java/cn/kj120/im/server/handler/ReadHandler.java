package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import cn.kj120.im.server.config.ChannelAttr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Handler;

@Slf4j
public class ReadHandler extends SimpleChannelInboundHandler<SendMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage msg) throws Exception {
        log.info("服务端收到信息: {}", msg);

        ReceiveMessage receiveMessage = getReceiveMessage(ctx, msg);

        CacheManage<String,ChannelHandlerContext>  ctxCache = ChannelHandlerContextCache.singleten();
        Collection<ChannelHandlerContext> ctxs = new ArrayList<>();
        switch (msg.getSendMessageType()) {
            case UNICAST:
                msg.getTos().forEach(sessionId -> {
                    ChannelHandlerContext handlerContext = ctxCache.get(sessionId);
                    ctxs.add(handlerContext);
                });
                forward(ctxs, receiveMessage);
                break;
            case MULTICAST:
                msg.getTos().forEach(sessionId ->
                    ctxs.add(ctxCache.get(sessionId))
                );
                forward(ctxs, receiveMessage);
                break;
            case ALL:
                forward(ctxCache.getAll().values(), receiveMessage);
                break;
            default:
                log.error("为定义的消息类型: {}", msg);
        }
    }

    public void forward(Collection<ChannelHandlerContext> contexts, ReceiveMessage message) {
        contexts.forEach(ctx -> ctx.writeAndFlush(message));
    }

    public ReceiveMessage getReceiveMessage(ChannelHandlerContext ctx, SendMessage msg) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setFrom(ctx.channel().attr(ChannelAttr.SESSION_ID).get());
        receiveMessage.setBody(msg.getBody() + new Random().nextInt(100));
        return receiveMessage;
    }
}
