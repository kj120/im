package cn.kj120.im.server.config;

import cn.kj120.im.server.Session;
import io.netty.util.AttributeKey;

public interface ChannelAttr {

    /**
     * 会话信息
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
