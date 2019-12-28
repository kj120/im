package cn.kj120.im.server.config;

import io.netty.util.AttributeKey;

public interface ChannelAttr {

    /**
     * 会话信息
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
