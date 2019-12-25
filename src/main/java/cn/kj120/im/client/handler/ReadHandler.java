package cn.kj120.im.client.handler;

import cn.kj120.im.client.Client;
import cn.kj120.im.common.message.SendMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ReadHandler extends SimpleChannelInboundHandler<SendMessage> {

    private Client client;

    public ReadHandler(Client client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessage sendMessage) throws Exception {
        client.onMessage(sendMessage);
    }
}
