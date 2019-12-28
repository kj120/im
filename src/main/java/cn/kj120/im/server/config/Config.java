package cn.kj120.im.server.config;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

@Configuration
public class Config {

    @Bean
    public ChannelHandler stringEncoder() {
        return new StringEncoder(Charset.defaultCharset());
    }

    @Bean
    public ChannelHandler stringDecoder() {
        return new StringDecoder(Charset.defaultCharset());
    }

}
