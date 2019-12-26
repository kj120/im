package cn.kj120.im.client;

import cn.kj120.im.common.codec.SendMessageToStringEncoder;
import cn.kj120.im.common.codec.StringToReceiveMessageDecoder;
import cn.kj120.im.client.handler.ReadHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;


@Data
public class DefaultChannelInitializer extends ChannelInitializer<Channel> {

    private Client client;

    private ReadHandler readHandler;

    public DefaultChannelInitializer(Client client) {
        this.client = client;
        this.readHandler = new ReadHandler(this.client);
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast("encode", new StringEncoder())
                .addLast("decode", new StringDecoder())
                .addAfter("decode", "messageDe",new StringToReceiveMessageDecoder())
                .addAfter("encode", "messageEn",new SendMessageToStringEncoder())
                .addLast(readHandler);
    }
}
