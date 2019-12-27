package cn.kj120.im.server.util;


import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import io.netty.channel.Channel;

import java.util.UUID;

public class SessionUtil {

    public final static String SYSTEM_SESSION_ID = "system";

    public static String createSessionId(Channel channel) {
        return String.valueOf(channel.id());
    }

    public static String createSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isAuth(Channel channel) {
        return getSession(channel) == null ? false : true;
    }


    public static <T> Session<T> getSession(Channel channel) {
        return channel.attr(ChannelAttr.SESSION).get();
    }

}
