package cn.kj120.im.server.handler;

import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ReadHandler extends SimpleChannelInboundHandler<SendMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage msg) throws Exception {
        System.err.println("服务端收到信息: " + msg);

        ChannelHandlerContext handlerContext = ChannelHandlerContextCache.INSTANCE.get(msg.getTos().get(0));
        msg.setBody(msg.getBody() + new Random().nextInt(100));
        handlerContext.writeAndFlush(msg);

    }
}
