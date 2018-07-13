package com.wx.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChenYaLan on 2018/7/13
 * Redis的Shiro缓存对象实现
 **/
public class RedisCache<K,V> implements Cache<K, V> {
    private static final Logger log=LoggerFactory.getLogger(RedisCache.class);
    private RedisTemplate<K,V> redisTemplate;
    private final String PREFIX="shiro-cache";
    private String cacheKey;
    private long globExpire=30;

    @SuppressWarnings({"rawtypes","unchecked"})
    public RedisCache(final String name,final RedisTemplate redisTemplate){
        this.cacheKey=PREFIX+name+":";
        this.redisTemplate=redisTemplate;
    }

    @Override
    public V get(K key) throws CacheException {
        log.debug("Shiro从缓存中获取数据KEY值["+key+"]");
        redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
        return redisTemplate.boundValueOps(getCacheKey(key)).get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
       V old=get(key);
       redisTemplate.boundValueOps(getCacheKey(key)).set(value);
       return old;
    }

    @Override
    public V remove(K key) throws CacheException {
       V old=get(key);
       redisTemplate.delete(getCacheKey(key));
       return old;
    }

    @Override
    public void clear() throws CacheException {
             redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set=keys();
        List<V> list=new ArrayList<>();
        for(K s:set){
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k){
        return (K) (this.cacheKey+k);
    }
}
