package cn.kj120.im.common;

import java.util.Map;

/**
 * 缓存管理
 * @param <K> 缓存键
 * @param <V> 缓存值
 * @author pcg
 * @date 2019-12-24 17:07:17
 */
public interface CacheManage<K, V> {

    /**
     * 检查缓存是否存在
     * @param k  缓存键
     * @return 是否存在
     */
    boolean isExists(K k);

    /**
     * 获取缓存值
     * @param k 缓存键
     * @return 缓存值
     */
    V get(K k);

    /**
     * 新增缓存数据
     * @param k 缓存键
     * @param v 缓存值
     * @return
     */
    boolean put(K k, V v);

    /**
     * 删除指定key的缓存
     * @param k
     * @return
     */
    boolean remove(K k);

    /**
     * 清除所有缓存
     */
    void clear();

    /**
     * 获取所有缓存信息
     * @return
     */
    Map<K,V> getAll();
}
