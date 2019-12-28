package cn.kj120.im.server.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class DefaultChannelInitializer extends ChannelInitializer<Channel> {

    @Resource(name = "authorizationHandler")
    private ChannelHandler authorizationHandler;

    @Resource(name = "stringDecoder")
    private ChannelHandler stringDecoder;

    @Resource(name = "stringEncoder")
    private ChannelHandler stringEncoder;

    @Resource(name = "stringToSendMessageDecoder")
    private ChannelHandler stringToSendMessageDecoder;

    @Resource(name = "receiveMessageToStringEncoder")
    private ChannelHandler receiveMessageToStringEncoder;

    @Resource(name = "exceptionHandler")
    private ChannelHandler exceptionHandler;

    @Resource(name = "activeHandler")
    private ChannelHandler activeHandler;

    @Resource(name = "readHandler")
    private ChannelHandler readHandler;


    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast("stringEncoder", stringEncoder)
                .addLast("stringDecoder", stringDecoder)
                .addAfter("stringDecoder", "stringToSendMessageDecoder", stringToSendMessageDecoder)
                .addAfter("stringEncoder", "receiveMessageToStringEncoder", receiveMessageToStringEncoder)
                .addLast(exceptionHandler)
                .addLast(activeHandler)
                .addLast(authorizationHandler)
                .addLast(readHandler);
    }
}
