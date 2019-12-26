package cn.kj120.im.common.message;

import lombok.Data;

/**
 * 消息
 * @author pcg
 * @date 2019-12-23 17:32:05
 */
@Data
public class ReceiveMessage {

    /**
     * 发送者唯一标识
     */
    private String from;

    /**
     * 消息体
     */
    private String body;
}
