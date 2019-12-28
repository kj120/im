package cn.kj120.im.server.schdule;

import cn.kj120.im.server.config.ChannelAttr;
import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.util.SendUtils;
import cn.kj120.im.server.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CheckNoAuthorization implements Runnable{

    private Channel channel;

    public CheckNoAuthorization(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        if (!SessionUtil.isAuth(channel)) {
            SendUtils.sendSystemMessage("授权超时, 请重新链接", channel);
            channel.close();
            log.warn("客户端连接授权超时: {}", channel);
        }
    }
}
