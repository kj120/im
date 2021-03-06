package cn.kj120.im.client;

import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.common.message.SendMessage;
import cn.kj120.im.common.message.SendMessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class NioClient implements Client {

    private EventLoopGroup work = new NioEventLoopGroup();

    private ChannelInitializer<Channel> channelInitializer;

    private Channel channel;

    {
        channelInitializer = new DefaultChannelInitializer(this);
    }

    @Override
    public void connect(String host, int port) throws InterruptedException{
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);

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
    }


    @Override
    public void send(String message, String to) {
        sendBatch(message, Arrays.asList(to));
    }

    @Override
    public void sendBatch(String message, List<String> tos) {
        SendMessage serverSendMessage = new SendMessage();
        serverSendMessage.setTos(tos);
        serverSendMessage.setBody(message);
        send(serverSendMessage);
    }

    @Override
    public void sendAll(String message) {
        SendMessage serverSendMessage = new SendMessage();
        serverSendMessage.setSendMessageType(SendMessageType.ALL);
        serverSendMessage.setBody(message);

        send(serverSendMessage);
    }

    @Override
    public void onMessage(ReceiveMessage message) {
        System.err.println();
        System.err.println("发送者id: " + message.getFrom());
        System.err.println("消息时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(message.getTime())));
        System.err.println("消息内容: " + message.getBody());


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

    private void send(SendMessage sendMessage) {
        if (channel == null) {
            new RuntimeException("未连接上服务器");
        }
        channel.writeAndFlush(sendMessage);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        NioClient client = new NioClient();
        new Thread() {
            @Override
            public void run() {
                try {
                    client.connect("127.0.0.1", 996);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        while (scanner.hasNext()) {
            String to = scanner.nextLine();
            client.sendAll(to);
        }

    }

}
