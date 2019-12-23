package cn.kj120.im.common.message;

import lombok.Data;

import java.util.List;

/**
 * 消息
 * @author pcg
 * @date 2019-12-23 17:32:05
 */
@Data
public class Message {

    /**
     * 发送者唯一标识
     */
    private String from;

    /**
     * 接收者唯一标识 (全网消息时为null)
     */
    private List<String> tos;

    /**
     * 消息类型
     */
    private MessageType messageType;

    /**
     * 消息体
     */
    private String body;
}
