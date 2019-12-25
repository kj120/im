package cn.kj120.im.client;

import cn.kj120.im.client.handler.ReadHandler;
import cn.kj120.im.common.codec.StringToMessageDecoder;
import cn.kj120.im.common.codec.MessageToStringEncoder;
import cn.kj120.im.common.message.Message;
import cn.kj120.im.common.message.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class NioClient implements Client {

    private EventLoopGroup work = new NioEventLoopGroup();

    private Channel channel;

    @Override
    public void connect(String host, int port) {
        try {
            Bootstrap bootstrap = new Bootstrap();

            ReadHandler readHandler = new ReadHandler(this);

            bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("encode", new StringEncoder())
                                    .addLast("decode", new StringDecoder())
                                    .addAfter("decode", "messageDe",new StringToMessageDecoder())
                                    .addAfter("encode", "messageEn",new MessageToStringEncoder())
                                    .addLast(readHandler);
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


    @Override
    public void send(String message, String to) {
        sendBatch(message, Arrays.asList(to));
    }

    @Override
    public void sendBatch(String message, List<String> tos) {
        Message serverMessage = new Message();
        serverMessage.setTos(tos);
        serverMessage.setBody(message);
        send(serverMessage);
    }

    @Override
    public void sendAll(String message) {
        Message serverMessage = new Message();
        serverMessage.setMessageType(MessageType.ALL);
        serverMessage.setBody(message);

        send(serverMessage);
    }

    @Override
    public void onMessage(Message message) {
        log.info("receive message :  {}", message);
    }

    @Override
    public void onOpen(String host, int port) {
        log.info("连接上服务器了, 服务器地址: {}  IP: {}", host, port);
    }

    @Override
    public void onError(String error) {
        log.error(error);
    }

    @Override
    public void onClose() {
        work.shutdownGracefully();
        if (channel != null) {
            channel.close();
        }
    }

    private void send(Message message) {
        if (channel == null) {
            new RuntimeException("未连接上服务器");
        }
        channel.writeAndFlush(message);
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
            String to = scanner.nextLine();
            client.send("哈喽", to);
        }

    }

}
