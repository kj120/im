package cn.kj120.im.server.message;

import cn.kj120.im.common.message.ReceiveMessage;
import cn.kj120.im.server.store.ChannelGroupStore;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class MessageForward {

    @Autowired
    private ChannelGroupStore channelGroupStore;

    public void send(String sessionId, ReceiveMessage message) {
        Channel channel = channelGroupStore.get(sessionId);
        if (channel != null) {
            channel.writeAndFlush(message);
        }
    }

    public void send(List<String> sessionIds, ReceiveMessage message) {
        Collection<Channel> channels = channelGroupStore.get(sessionIds);
        if (!CollectionUtils.isEmpty(channels)) {
            send(channels, message);
        }
    }

    public void send(ReceiveMessage message) {
        Collection<Channel> all = channelGroupStore.getAll();
        if (!CollectionUtils.isEmpty(all)) {
            send(all, message);
        }
    }

    private void send(Collection<Channel> channels, ReceiveMessage message) {
        channels.forEach(channel -> channel.writeAndFlush(message));
    }

}
