package cn.kj120.im.server.util;


import io.netty.channel.Channel;

import java.util.UUID;

public class SessionUtil {

    public static String createSessionId(Channel channel) {
        return String.valueOf(channel.id());
    }

    public static String createSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
