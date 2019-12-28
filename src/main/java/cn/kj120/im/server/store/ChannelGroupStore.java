package cn.kj120.im.server.store;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ChannelGroupStore {

    boolean isExists(String sessionId);

    boolean isExists(Channel channel);

    boolean set(String sessionId, Channel channel);

    Channel get(String sessionId);

    Collection<Channel> get(Collection<String> sessionIds);

    Collection<Channel> getAll();

    boolean remove(String sessionId);

    void clear();
}
