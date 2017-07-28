package com.bestinfo.redis.dao.impl;

import com.bestinfo.redis.dao.IRedisDao;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

/**
 *
 * @author yangyuefu
 */
@Repository
public class RedisDaoImpl implements IRedisDao {

    Logger logger = Logger.getLogger(RedisDaoImpl.class);

    @Resource
    public ShardedJedisPool shardedJedisPool;

//    @Resource
//    public ShardedJedisSentinelPool shardedJedisPool;
    /**
     * redis添加纪录（如果key重复则覆盖），分片
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean redisSet(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * 获取记录值，分片
     */
    @Override
    public String redisLoad(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 删除指定key，分片
     *
     * @param key
     * @return 删除的数据条数
     */
    @Override
    public Long redisDel(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.del(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return -1L;
    }

    /**
     * hash值设置
     *
     * @param key
     * @param filed
     * @param value
     * @return
     */
    @Override
    public boolean redisHashSet(String key, String filed, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            long a = shardedJedis.hset(key, filed, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * hash值获取--获取某一域的值
     *
     * @param key
     * @param filed
     * @return
     */
    @Override
    public String redisHashGet(final String key, String filed) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hget(key, filed);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * hash值获取--获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> redisHashKeys(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hkeys(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * hash值获取--获取哈希表中所有值(只是value)
     *
     * @param key
     * @return
     */
    @Override
    public List<String> redisHashVals(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hvals(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * hash值获取--获取哈希表中指定 key 的所有字段和值(包括name和value)
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, String> redisHashGetAll(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * hash值获取--获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    @Override
    public long redisHashLen(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hlen(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return 0;
    }

    /**
     * set值设置
     *
     * @param key
     * @param members
     * @return
     */
    @Override
    public boolean redisSetSet(String key, String... members) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            long a = shardedJedis.sadd(key, members);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * set获取
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> redisSetLoad(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.smembers(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 随机移出set里面某个值
     *
     * @param key
     * @return
     */
    @Override
    public String redisSetPop(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.spop(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 在key集合中移除指定的元素
     *
     * @param key
     * @param members
     * @return
     */
    @Override
    public long redisSetRem(final String key, String... members) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.srem(key, members);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return 0;
    }

    /**
     * 获取Set集合中元素的数量
     *
     * @param key
     * @return
     */
    @Override
    public long redisSetLength(final String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.scard(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return -1;
    }

    /**
     * 查找Set集合中是否存在该元素
     *
     * @param key
     * @param member
     * @return
     */
    @Override
    public boolean redisSetExist(final String key, String member) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.sismember(key, member);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * 管道插入,key-value
     *
     * @param map
     * @return
     */
    @Override
    public boolean pipeline(Map<String, String> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipeline = shardedJedis.pipelined();
            for (String key : map.keySet()) {
                pipeline.set(key, map.get(key));
            }
            pipeline.syncAndReturnAll();
            return true;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * 管道插入,hashset
     *
     * @param map
     * @return
     */
    @Override
    public boolean pipelineHashSet(Map<String, String> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipeline = shardedJedis.pipelined();
            for (String key : map.keySet()) {
                String[] keyArr = key.split("#");
                pipeline.hset(keyArr[0], keyArr[1], map.get(key));
            }
            pipeline.syncAndReturnAll();
            return true;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }
    
     @Override
    public Set<String> keys(String pattern) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hkeys(pattern);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }
}
