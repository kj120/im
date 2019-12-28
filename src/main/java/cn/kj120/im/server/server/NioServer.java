package cn.kj120.im.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@Data
public class NioServer implements Server {

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup work = new NioEventLoopGroup();

    private int port = 996;

    private ServerBootstrap bootstrap;

    private Class<NioServerSocketChannel> serverSocketChannel = NioServerSocketChannel.class;

    @Autowired
    private ChannelInitializer<Channel> channelInitializer;

    @PostConstruct
    public void init() throws InterruptedException {
        start();
    }


    @Override
    public void start() throws InterruptedException {


        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .channel(serverSocketChannel)
                .childHandler(channelInitializer);

        ChannelFuture sync = bootstrap.bind(port).sync();
        sync.addListener(future -> {
           if (future.isSuccess()) {
               log.info("服务器启动成功,监听端口号: {}", port);
           } else {
               log.error("服务器启动失败: {}", future.cause().getMessage());
           }
        });
    }

    @Override
    @PreDestroy
    public void shop() {
        boss.shutdownGracefully().awaitUninterruptibly();
        work.shutdownGracefully().awaitUninterruptibly();
    }
}
