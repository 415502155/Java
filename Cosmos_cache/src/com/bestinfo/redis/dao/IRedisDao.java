package com.bestinfo.redis.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author yangyuefu
 */
public interface IRedisDao {

    /**
     * 添加纪录
     *
     * @param key
     * @param value
     * @return
     */
    boolean redisSet(String key, String value);

    /**
     * 通过key获得数据
     *
     * @param key
     * @return
     */
    String redisLoad(String key);

    /**
     * 删除指定key，分片
     *
     * @param key
     * @return 删除的数据条数
     */
    public Long redisDel(String key);

    /**
     * hash值设置
     *
     * @param key
     * @param filed
     * @param value
     * @return
     */
    public boolean redisHashSet(String key, String filed, String value);

    /**
     * hash值获取--获取哈希表中指定字段的值
     *
     * @param key
     * @param filed
     * @return
     */
    public String redisHashGet(final String key, String filed);

    /**
     * hash值获取--获取所有哈希表中的字段(只是name)
     *
     * @param key
     * @return
     */
    public Set<String> redisHashKeys(final String key);

    /**
     * hash值获取--获取哈希表中所有值(只是value)
     *
     * @param key
     * @return
     */
    public List<String> redisHashVals(final String key);

    /**
     * hash值获取--获取哈希表中指定 key 的所有字段和值(包括name和value)
     *
     * @param key
     * @return
     */
    public Map<String, String> redisHashGetAll(final String key);

    /**
     * hash值获取--获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public long redisHashLen(final String key);

    /**
     * set值设置
     *
     * @param key
     * @param members
     * @return
     */
    public boolean redisSetSet(String key, String... members);

    /**
     * set获取
     *
     * @param key
     * @return
     */
    public Set<String> redisSetLoad(final String key);

    /**
     * 随机移出set里面某个值
     *
     * @param key
     * @return
     */
    public String redisSetPop(final String key);

    /**
     * 在key集合中移除指定的元素
     *
     * @param key
     * @param members
     * @return
     */
    public long redisSetRem(final String key, String... members);

    /**
     * 获取Set集合中元素的数量
     *
     * @param key
     * @return
     */
    public long redisSetLength(final String key);

    /**
     * 查找Set集合中是否存在该元素
     *
     * @param key
     * @param member
     * @return
     */
    public boolean redisSetExist(final String key, String member);

    /**
     * 管道插入,key-value
     *
     * @param map
     * @return
     */
    public boolean pipeline(Map<String, String> map);

    /**
     * 管道插入,hset
     *
     * @param map
     * @return
     */
    public boolean pipelineHashSet(Map<String, String> map);
     /**
     * 查找所有符合给定模式pattern（正则表达式）的 key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern);

}
