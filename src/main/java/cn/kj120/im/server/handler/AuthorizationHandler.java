package cn.kj120.im.server.handler;

import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.server.auth.Authorization;
import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.store.ChannelGroupStore;
import cn.kj120.im.server.util.SendUtils;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 客户端连接权限认证
 * @author pcg
 * @date 2019-12-28 17:50:03
 */
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
            Channel channel = ctx.channel();
            if (session == null) {
                SendUtils.sendSystemMessage("授权不通过", channel);
                log.info("客户端授权未通过: {}", channel);
            }else {
                channel.attr(ChannelAttr.SESSION).set(session);
                channelGroupStore.set(session.getSessionId(), channel);
                authorization.bind(sendMessage.getBody(), session.getSessionId());
                SendUtils.sendSystemMessage("当前连接sessionId: " + session.getSessionId(), channel);
            }
            ReferenceCountUtil.release(msg);
        } else {
            super.channelRead(ctx, msg);
        }

    }
}
