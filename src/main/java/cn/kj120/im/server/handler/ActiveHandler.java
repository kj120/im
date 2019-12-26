package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.server.ChannelHandlerContextCache;
import cn.kj120.im.server.Session;
import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;


public class ActiveHandler extends ChannelInboundHandlerAdapter {

    private CacheManage<String, ChannelHandlerContext> cacheManage;

    {
        cacheManage = ChannelHandlerContextCache.singleten();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sessionId = SessionUtil.createSessionId(ctx.channel());

        Attribute<Session> attr = ctx.channel().attr(ChannelAttr.SESSION);
        Session session = new Session(sessionId, new Date());
        attr.set(session);

        cacheManage.put(sessionId, ctx);
        super.channelActive(ctx);
    }
}
