package cn.kj120.im.server.store;

import cn.kj120.im.common.CacheManage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChannelHandlerContextCache implements CacheManage<String, ChannelHandlerContext> {

    private ConcurrentHashMap<String, ChannelHandlerContext> concurrentHashMap = new ConcurrentHashMap<>();

    private final static CacheManage<String, ChannelHandlerContext> INSTANCE = new ChannelHandlerContextCache();

    public static CacheManage<String, ChannelHandlerContext> singleten() {
        return INSTANCE;
    }


    @Override
    public boolean isExists(String key) {
        return concurrentHashMap.get(key) != null ? true : false;
    }

    @Override
    public ChannelHandlerContext get(String key) {
        return concurrentHashMap.get(key);
    }

    @Override
    public boolean put(String key, ChannelHandlerContext channelHandlerContext) {
        log.info("缓存key: {}  缓存值: {}", key, channelHandlerContext);
        concurrentHashMap.put(key, channelHandlerContext);
        return true;
    }

    @Override
    public boolean remove(String s) {
        return false;
    }

    @Override
    public void clear() {
        concurrentHashMap.clear();
    }

    @Override
    public Map<String, ChannelHandlerContext> getAll() {
        return concurrentHashMap;
    }
}
