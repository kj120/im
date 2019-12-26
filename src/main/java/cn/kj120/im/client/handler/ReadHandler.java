package cn.kj120.im.client.handler;

import cn.kj120.im.client.Client;
import cn.kj120.im.common.message.receive.ReceiveMessage;
import cn.kj120.im.common.message.send.SendMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ReadHandler extends SimpleChannelInboundHandler<ReceiveMessage> {

    private Client client;

    public ReadHandler(Client client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveMessage message) throws Exception {
        client.onMessage(message);
    }
}
