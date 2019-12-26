package cn.kj120.im.server;

import cn.kj120.im.common.codec.ReceiveMessageToStringEncoder;
import cn.kj120.im.common.codec.StringToSendMessageDecoder;
import cn.kj120.im.server.handler.ActiveHandler;
import cn.kj120.im.server.handler.ExceptionHandler;
import cn.kj120.im.server.handler.ReadHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class DefaultChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast("encode", new StringEncoder())
                .addLast("decode", new StringDecoder())
                .addAfter("decode", "messageDe",new StringToSendMessageDecoder())
                .addAfter("encode", "messageEn",new ReceiveMessageToStringEncoder())
                .addLast(new ExceptionHandler())
                .addLast(new ActiveHandler())
                .addLast(new ReadHandler());
    }
}
