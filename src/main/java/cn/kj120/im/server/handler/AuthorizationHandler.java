package cn.kj120.im.server.handler;

import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.auth.Authorization;
import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.store.ChannelGroupStore;
import cn.kj120.im.server.util.SendUtils;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
@Data
public class AuthorizationHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private Authorization authorization;

    @Autowired
    private ChannelGroupStore channelGroupStore;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.isAuth(ctx.channel())) {
            SendMessage sendMessage = (SendMessage) msg;
            Session session = authorization.auth(sendMessage.getBody());
            if (session == null) {
                SendUtils.sendSystemMessage("授权不通过", ctx.channel());
            }else {
                ctx.channel().attr(ChannelAttr.SESSION).set(session);
                channelGroupStore.set(session.getSessionId(), ctx.channel());
            }
            ReferenceCountUtil.release(msg);
        } else {
            super.channelRead(ctx, msg);
        }

    }
}
