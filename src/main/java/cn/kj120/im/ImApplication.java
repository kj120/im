package cn.kj120.im;

import cn.kj120.im.server.init.DefaultChannelInitializer;
import cn.kj120.im.server.server.NioServer;
import io.netty.channel.ChannelInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("cn.kj120.im")
public class ImApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(ImApplication.class, args);
    }

}
