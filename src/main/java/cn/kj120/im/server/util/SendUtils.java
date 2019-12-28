package cn.kj120.im.server.util;

import cn.kj120.im.common.message.ReceiveMessage;
import io.netty.channel.Channel;

public class SendUtils {

    /**
     * 发送系统消息
     * @param msg
     * @param channel
     */
    public static void sendSystemMessage(String msg, Channel channel) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setFrom(SessionUtil.SYSTEM_SESSION_ID);
        receiveMessage.setTime(System.currentTimeMillis());
        receiveMessage.setBody(msg);
        channel.writeAndFlush(receiveMessage);
    }
}
