package cn.kj120.im.server.handler;

import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.schdule.CheckNoAuthorization;
import cn.kj120.im.server.store.ChannelGroupStore;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
@Component
public class ActiveHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private ChannelGroupStore channelGroupStore;

    @Resource
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端连接上10秒后未授权断开连接
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.schedule(new CheckNoAuthorization(ctx.channel()), 10, TimeUnit.SECONDS);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("断开tcp连接: {}", ctx.channel());
        Session session = ctx.channel().attr(ChannelAttr.SESSION).get();
        if (session != null) {
            channelGroupStore.remove(session.getSessionId());
        }
        super.channelInactive(ctx);
    }



}
