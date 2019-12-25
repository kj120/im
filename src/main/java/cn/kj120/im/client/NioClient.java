package cn.kj120.im.client;

import cn.kj120.im.client.handler.ReadHandler;
import cn.kj120.im.common.codec.MessageDecoder;
import cn.kj120.im.common.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class NioClient {

    private EventLoopGroup work = new NioEventLoopGroup();

    private Channel channel;

    public void connect(String host, int port) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("encode", new StringEncoder())
                                    .addLast("decode", new StringDecoder())
                                    .addLast(new ReadHandler());
                        }
                    });

            ChannelFuture sync = bootstrap.connect(host, port).sync();

            sync.addListener(future -> {
                if (future.isSuccess()) {
                    channel = sync.channel();
                    log.info("连接上服务器了, 服务器地址: {}  IP: {}", host, port);
                } else {
                    System.err.println("链接失败: " + future.cause().getMessage());
                }
            });

            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        NioClient client = new NioClient();
        new Thread() {
            @Override
            public void run() {
                client.connect("127.0.0.1", 996);
            }
        }.start();

        while (scanner.hasNext()) {
            String msg = scanner.nextLine();
            client.channel.writeAndFlush(msg);
        }

    }

}
