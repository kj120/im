package cn.kj120.im.server;

import cn.kj120.im.common.codec.ReceiveMessageToStringEncoder;
import cn.kj120.im.common.codec.StringToSendMessageDecoder;
import cn.kj120.im.common.codec.SendMessageToStringEncoder;
import cn.kj120.im.server.handler.ActiveHandler;
import cn.kj120.im.server.handler.ExceptionHandler;
import cn.kj120.im.server.handler.ReadHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NioServer implements Server {

    private EventLoopGroup boss;

    private EventLoopGroup work;

    private ServerBootstrap bootstrap;

    private Class<NioServerSocketChannel> serverSocketChannel;

    {
        serverSocketChannel = NioServerSocketChannel.class;
    }

    @Override
    public void start(int port) {
        try {
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .channel(serverSocketChannel)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast("encode", new StringEncoder())
                                .addLast("decode", new StringDecoder())
                                .addAfter("decode", "messageDe",new StringToSendMessageDecoder())
                                .addAfter("encode", "messageEn",new ReceiveMessageToStringEncoder())
                                .addLast(new ExceptionHandler())
                                .addLast(new ActiveHandler())
                                .addLast(new ReadHandler());

                    }
                });

            ChannelFuture sync = bootstrap.bind(port).sync();
            log.info("服务器启动成功,监听端口号: {}", port);
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
