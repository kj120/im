package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;


public class ActiveHandler extends ChannelInboundHandlerAdapter {

    private CacheManage<String, ChannelHandlerContext> cacheManage;

    {
        cacheManage = ChannelHandlerContextCache.singleten();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sessionId = SessionUtil.createSessionId(ctx.channel());

        Attribute<String> attr = ctx.channel().attr(ChannelAttr.SESSION_ID);
        attr.set(sessionId);

        cacheManage.put(sessionId, ctx);
        super.channelActive(ctx);
    }
}
