package cn.kj120.im.server.config;

import io.netty.util.AttributeKey;

public interface ChannelAttr {

    /**
     * 回话唯一标识
     */
    AttributeKey<String> SESSION_ID = AttributeKey.newInstance("sessionId");
}
