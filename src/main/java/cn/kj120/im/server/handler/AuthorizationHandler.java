package cn.kj120.im.server.handler;

import cn.kj120.im.common.CacheManage;
import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.common.message.SendMessageType;
import cn.kj120.im.server.auth.Authorization;
import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.store.ChannelHandlerContextCache;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthorizationHandler extends ChannelInboundHandlerAdapter {

    private Authorization authorization;

    private CacheManage<String, ChannelHandlerContext> cacheManage;

    {
        cacheManage = ChannelHandlerContextCache.singleten();
    }

    public AuthorizationHandler(Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.isAuth(ctx.channel())) {
            SendMessage sendMessage = (SendMessage) msg;
            Session session = authorization.auth(sendMessage.getBody());
            if (session == null) {
                ReceiveMessage receiveMessage = new ReceiveMessage();
                receiveMessage.setFrom(SessionUtil.SYSTEM_SESSION_ID);
                receiveMessage.setBody("授权不通过");
                ctx.writeAndFlush(receiveMessage);
            }else {
                ctx.channel().attr(ChannelAttr.SESSION).set(session);
                cacheManage.put(session.getSessionId(), ctx);
            }
            ReferenceCountUtil.release(msg);
        }
        super.channelRead(ctx, msg);
    }
}
