package cn.kj120.im.server.store;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * channel内存存储
 * @author pcg
 * @date 2019-12-28 11:23:07
 */
@Component
public class MapChannelGroupStore implements ChannelGroupStore {

    private ConcurrentHashMap<String, Channel> channelGroup;

    {
        channelGroup = new ConcurrentHashMap<>(64);
    }

    @Override
    public boolean isExists(String sessionId) {
        return get(sessionId) != null;
    }

    @Override
    public boolean isExists(Channel channel) {
        return channelGroup.containsValue(channel);
    }

    @Override
    public boolean set(String sessionId, Channel channel) {
        channelGroup.put(sessionId, channel);
        return true;
    }

    @Override
    public Channel get(String sessionId) {
        return channelGroup.get(sessionId);
    }

    @Override
    public Collection<Channel> get(Collection<String> sessionIds) {
        Set<Channel> channels = new HashSet<>();
        if (sessionIds.size() / channelGroup.size() < 0.5) {
            sessionIds.forEach(k -> {
                Channel channel = channelGroup.get(k);
                if (channel != null) {
                    channels.add(channel);
                }
            });
        } else {
            channelGroup.entrySet()
                    .stream()
                    .filter((entry) -> sessionIds.contains(entry.getKey()))
                    .forEach(entry -> channels.add(entry.getValue()));
        }

        return channels;
    }

    @Override
    public Collection<Channel> getAll() {
        return channelGroup.values();
    }

    @Override
    public boolean remove(String sessionId) {
        channelGroup.remove(sessionId);
        return true;
    }

    @Override
    public void clear() {
        channelGroup.clear();
    }
}
