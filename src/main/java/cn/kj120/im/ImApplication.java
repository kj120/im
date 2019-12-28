package cn.kj120.im;

import cn.kj120.im.server.init.DefaultChannelInitializer;
import cn.kj120.im.server.server.NioServer;
import cn.kj120.im.server.store.ChannelGroupStore;
import io.netty.channel.ChannelInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
@Component("cn.kj120.im")
public class ImApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(ImApplication.class, args);

        ChannelGroupStore bean = run.getBean(ChannelGroupStore.class);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            System.out.println(bean.size());
        }
    }

}
