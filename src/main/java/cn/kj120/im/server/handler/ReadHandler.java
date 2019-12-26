package cn.kj120.im.server.handler;

import cn.kj120.im.common.message.receive.ReceiveMessage;
import cn.kj120.im.common.message.send.SendMessage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import cn.kj120.im.server.config.ChannelAttr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ReadHandler extends SimpleChannelInboundHandler<SendMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage msg) throws Exception {
        System.err.println("服务端收到信息: " + msg);

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setFrom(ctx.channel().attr(ChannelAttr.SESSION_ID).get());
        receiveMessage.setBody(msg.getBody() + new Random().nextInt(100));

        ChannelHandlerContext handlerContext = ChannelHandlerContextCache.INSTANCE.get(msg.getTos().get(0));

        handlerContext.writeAndFlush(receiveMessage);

    }
}
