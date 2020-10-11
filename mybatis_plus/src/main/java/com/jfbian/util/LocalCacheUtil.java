package com.jfbian.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : 董硕
 * @time: 2019-08-13 16:28
 * @desc: 利用guava实现的本地缓存工具类
 */
public class LocalCacheUtil {
    /**
     * 缓存容器
     */
    private static Map<String,Cache<String, Object>> cacheContainer = new ConcurrentHashMap<String,Cache<String, Object>>();

    /**
     * 获取默认的配置的localcache
     * @param cacheKey
     * @return
     */
    public static Cache<String,Object> getLocalCache(String cacheKey) {
        Cache<String, Object> localCache = cacheContainer.get(cacheKey);
        if (null != localCache) {
            return localCache;
        }
        synchronized (LocalCacheUtil.cacheContainer) {
            if (null == localCache) {
                localCache = CacheBuilder.newBuilder()
                        .expireAfterWrite(1L, TimeUnit.HOURS)
                        .initialCapacity(2000)
                        .maximumSize(10000)
                        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                        .recordStats()
                        .build();
                cacheContainer.put(cacheKey,localCache);
            }
            return localCache;
        }
    }

    /**
     * 自定义配置
     * @param cacheKey
     * @param duration 时间系数
     * @param unit 时间单位
     * @param initialCapacity 初始化容量
     * @param maximumSize 最大容量
     * @param concurrencyLevel 并发级别
     * @return
     */
    public static Cache<String,Object> getLocalCache(String cacheKey,long duration,TimeUnit unit,int initialCapacity,int maximumSize,int concurrencyLevel) {
        Cache<String, Object> localCache = cacheContainer.get(cacheKey);
        if (null != localCache) {
            return localCache;
        }
        synchronized (LocalCacheUtil.cacheContainer) {
            if (null == localCache) {
                //recordStats开启缓存状况统计,expireAfterAccess过期时间,initialCapacity初始化大小,maximumSize最大值
                localCache = CacheBuilder.newBuilder()
                        .expireAfterWrite(duration, unit)
                        .initialCapacity(initialCapacity)
                        .maximumSize(maximumSize)
                        .concurrencyLevel(concurrencyLevel)
                        .recordStats()
                        .build();
                cacheContainer.put(cacheKey,localCache);
            }
            return localCache;
        }
    }
}
