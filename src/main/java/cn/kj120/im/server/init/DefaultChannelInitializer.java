package cn.kj120.im.server.init;

import cn.kj120.im.common.codec.ReceiveMessageToStringEncoder;
import cn.kj120.im.common.codec.StringToSendMessageDecoder;
import io.netty.channel.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;

@Component
@Data
public class DefaultChannelInitializer extends ChannelInitializer<Channel> {

    @Resource
    private ChannelHandler authorizationHandler;

    @Resource
    private ChannelHandler stringDecoder;

    @Resource
    private ChannelHandler stringEncoder;

    @Resource
    private ChannelHandler stringToSendMessageDecoder;

    @Resource
    private ChannelHandler receiveMessageToStringEncoder;

    @Resource
    private ChannelHandler exceptionHandler;

    @Resource
    private ChannelHandler activeHandler;

    @Resource
    private ChannelHandler readHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast("stringEncoder", new StringEncoder(Charset.defaultCharset()))
                .addLast("stringDecoder", new StringDecoder(Charset.defaultCharset()))
                .addAfter("stringDecoder", "stringToSendMessageDecoder", new StringToSendMessageDecoder())
                .addAfter("stringEncoder", "receiveMessageToStringEncoder",new ReceiveMessageToStringEncoder())
                .addLast(exceptionHandler)
                .addLast(activeHandler)
                .addLast(authorizationHandler)
                .addLast(readHandler);
    }
}
