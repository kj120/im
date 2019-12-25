package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class ActiveHandler extends ChannelInboundHandlerAdapter {

    private CacheManage<String, ChannelHandlerContext> cacheManage;

    {
        cacheManage = ChannelHandlerContextCache.singleten();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        cacheManage.put(ctx.channel().id().toString(), ctx);
        ctx.writeAndFlush("服务器接受客户端链接");
        super.channelActive(ctx);
    }
}
