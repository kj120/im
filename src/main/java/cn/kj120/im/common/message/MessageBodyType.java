package cn.kj120.im.common.message;

import cn.kj120.im.common.message.MessageException;

/**
 * 消息体类型
 * @author pcg
 * @2019-12-23 17:35:23
 */
public enum  MessageBodyType {

    /**
     * 字符串类型 (未定义的消息类型)
     */
    STRING,

    /**
     * json格式
     */
    JSON,

    /**
     * 图片 (链接地址)
     */
    IMAGE,

    /**
     * 视频消息 (链接地址)
     */
    VIODE,

    /**
     * 语音消息 (链接地址)
     */
    VOICE;
}
