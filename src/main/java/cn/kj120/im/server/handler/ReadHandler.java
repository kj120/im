package cn.kj120.im.server.handler;

import cn.kj120.im.server.ChannelHandlerContextCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ReadHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println("服务端收到信息: " + msg);

        String[] split = msg.split(":");
        if (split.length > 1 ) {
            ChannelHandlerContext handlerContext = ChannelHandlerContextCache.INSTANCE.get(split[0]);
            handlerContext.writeAndFlush(msg + new Random().nextInt(100));
        }
    }
}
