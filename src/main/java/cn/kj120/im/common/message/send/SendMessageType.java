package cn.kj120.im.common.message.send;

/**
 * 消息类型
 * @author pcg
 * @date 2019-12-23 17:57:40
 */
public enum SendMessageType {
    /**
     * 单播 (一对一消息)
     */
    UNICAST,

    /**
     * 组播 (批量消息）
     */
    MULTICAST,

    /**
     * 全网消息
     */
    ALL;
}
