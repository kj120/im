package cn.kj120.im.server;

/**
 * 消息方法结果处理器
 *
 * @author pcg
 * @2019-12-23 17:29:18
 */
public interface MessageSendResultHander {

    /**
     * 消息发送成功
     * todo 入参
     * @return
     */
    boolean success();

    /**
     * 消息发送失败
     * todo 入参
     * @return
     */
    boolean fail();
}
