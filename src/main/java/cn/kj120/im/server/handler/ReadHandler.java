package cn.kj120.im.server.handler;

import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.message.MessageForward;
import cn.kj120.im.server.store.ChannelGroupStore;
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

    @Autowired
    private MessageForward messageForward;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage msg) throws Exception {
        log.info("服务端收到信息: {}", msg);

        ReceiveMessage receiveMessage = getReceiveMessage(ctx.channel(), msg);

        switch (msg.getSendMessageType()) {
            case UNICAST:
                msg.getTos().forEach(sessionId -> {
                    messageForward.send(sessionId, receiveMessage);
                });

                break;
            case MULTICAST:
                messageForward.send(msg.getTos(), receiveMessage);
                break;
            case ALL:
                messageForward.send(receiveMessage);
                break;
            default:
                log.error("为定义的消息类型: {}", msg);
        }
    }

    public ReceiveMessage getReceiveMessage(Channel ctx, SendMessage msg) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setFrom(ctx.attr(ChannelAttr.SESSION).get().getSessionId());
        receiveMessage.setTime(System.currentTimeMillis());
        receiveMessage.setBody(msg.getBody() + "   " + new Random().nextInt(100));
        return receiveMessage;
    }
}
