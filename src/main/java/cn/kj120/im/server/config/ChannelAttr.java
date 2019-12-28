package cn.kj120.im.server.config;

import io.netty.util.AttributeKey;

import java.util.Date;

public interface ChannelAttr {

    /**
     * 会话信息
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    /**
     * tcp链接成功时间
     */
    AttributeKey<Date> CONNECT_DATE = AttributeKey.newInstance("connect_date");


}
