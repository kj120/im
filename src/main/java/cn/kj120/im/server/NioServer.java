package cn.kj120.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NioServer implements Server {

    private EventLoopGroup boss;

    private EventLoopGroup work;

    private ServerBootstrap bootstrap;

    private Class<NioServerSocketChannel> serverSocketChannel;

    private ChannelInitializer<Channel> channelInitializer;

    {
        serverSocketChannel = NioServerSocketChannel.class;

        channelInitializer = new DefaultChannelInitializer();
    }

    @Override
    public void start(int port) {
        try {
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();

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

        sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shop() {
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
