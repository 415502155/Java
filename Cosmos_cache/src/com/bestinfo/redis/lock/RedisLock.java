package com.bestinfo.redis.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 *
 * @author chenliping
 */
@Repository
public class RedisLock {

    private final Logger logger = Logger.getLogger(RedisLock.class);

    public static boolean islock = true; //是否启用锁
    public static int threadWaitTimeout = 3000;//等待获取锁的时间，毫秒为单位
    public static String lockTimeout = "15000";//锁超时时间，毫秒为单位    
    public static int intervalTime = 100;//获取锁间隔时间，即多长时间重新获取一次锁

    @Resource
    private ShardedJedisPool shardedJedisSentinelPool;

//    @Resource
//    public ShardedJedisSentinelPool shardedJedisSentinelPool;
    private static final String getlock = "local now = tonumber(ARGV[1]);"
            + "local timeout = tonumber(ARGV[2]);"
            + "local to = now + timeout;"
            + "local locked = redis.call('SETNX', KEYS[1], to);"
            + "if (locked == 1) then"
            + "    return 0;"
            + "end;"
            + "local keyValue = tonumber(redis.call('get', KEYS[1]));"
            + "if (now > keyValue) then"
            + "    redis.call('set', KEYS[1], to);"
            + "    return 0;"
            + "end;"
            + "return 1;";

    private static final String removelock = "local begin = tonumber(ARGV[1]);"
            + "local timeout = tonumber(ARGV[2]);"
            + "local kt = redis.call('type', KEYS[1]);"
            + "    local keyValue = tonumber(redis.call('get', KEYS[1]));"
            + "    if ((keyValue - begin) == timeout) then"
            + "        redis.call('del', KEYS[1]);"
            + "        return 0;"
            + "    end;"
            + "return 1;";

    private static final String GET_SET_TERMINAL_SESSION = ""
            + "local session = redis.call('get', KEYS[1]);"
            + "if session then"
            + " local x = cjson.decode(session);"
            + "	if x[KEYS[2]] > tonumber(ARGV[1]) then"//redis里面的时间大于传入的时间,直接返回错误
            + "     return 'transTime:'..x[KEYS[2]];"
            + "	elseif x[KEYS[2]] == tonumber(ARGV[1]) then"//redis里面的时间等于传入的时间,比较counter
            + "     if x[KEYS[3]] >= tonumber(ARGV[2]) then"//redis里面的counter大于等于传入的counter,直接返回错误
            + "		return 'counter:'..x[KEYS[3]];"
            + "     else"//redis里面的counter小于传入的counter,更新redis
            + "         x[KEYS[2]] = tonumber(ARGV[1]);"//更新transTime
            + "         x[KEYS[3]] = tonumber(ARGV[2]);"//更新counter
            + "         x[KEYS[4]] = tonumber(ARGV[3]);"//更新actionId
            + "         local val = string.gsub(cjson.encode(x),'\\\\','');"// 替换反斜杠
            + "         local re = redis.call('set', KEYS[1], val);"
            + "         if re then"
            + "             return 'success';"
            + "          else"
            + "             return 'fail';"
            + "         end;"
            + "     end;"
            + "	else"//redis里面的时间小于传入的时间,更新redis
            + "     x[KEYS[2]] = tonumber(ARGV[1]);"//更新transTime
            + "     x[KEYS[3]] = tonumber(ARGV[2]);"//更新counter
            + "     x[KEYS[4]] = tonumber(ARGV[3]);"//更新actionId
            + "     local val = string.gsub(cjson.encode(x),'\\\\','');"// 替换反斜杠
            + "     local re = redis.call('set', KEYS[1], val);"
            + "     if re then"
            + "         return 'success';"
            + "     else"
            + "         return 'fail';"
            + "     end;"
            + "	end;"
            + "else"
            + " return 'nonexist';"
            + "end;";

    /**
     * 执行lua脚本，进行交易时间和计数器的比较，并更新字段的值
     *
     * @param keys identity，transTime,counter,actionId
     * @param args transTime,counter,actionId
     * @return
     */
    public boolean terminalSessionLock(List<String> keys, List<String> args) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisSentinelPool.getResource();
            Jedis j = shardedJedis.getShard(keys.get(0));
            String re = (String) j.eval(GET_SET_TERMINAL_SESSION, keys, args);
            if (re.equalsIgnoreCase("success")) {//成功
                logger.info("check and set terminal session success,key: " + keys.get(0));
                return true;
            }
            if (re.equalsIgnoreCase("fail")) {//赋值错误
                logger.error("check and set terminal session error,set fail,key: " + keys.get(0));
                return false;
            }
            if (re.equalsIgnoreCase("nonexist")) {//key不存在
                logger.error("check and set terminal session error,the key does not exist,key: " + keys.get(0));
                return false;
            }
            if (re.indexOf("transTime") == 0) {//交易时间错误
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                logger.error("check and set terminal session error,transTime error,key: " + keys.get(0)
                        + ",new transTime:" + formater.format(new Date(Long.valueOf(args.get(0))))
                        + ",redis transTime:" + formater.format(new Date(Long.valueOf(re.split(":")[1]))));
                return false;
            }
            if (re.indexOf("counter") == 0) {//计数器错误
                logger.error("check and set terminal session error,counter error,key: " + keys.get(0)
                        + ",new counter:" + args.get(1)
                        + ",redis counter:" + re.split(":")[1]);
                return false;
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedisSentinelPool.returnResource(shardedJedis);
            }
        }
        return false;
    }

    /**
     * 获取锁
     *
     * @param threadTimeout 锁超时时间
     * @param keys
     * @param args
     * @return
     */
    public boolean acquire(String threadTimeout, List<String> keys, List<String> args) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisSentinelPool.getResource();
            Jedis j = shardedJedis.getShard(keys.get(0));
            int tempThreadWaitTimeout = threadWaitTimeout;//把threadWaitTimeout赋给一个临时变量
            while (tempThreadWaitTimeout >= 0) {
                long nowtime = System.currentTimeMillis();
                args.add(Long.toString(nowtime));
                args.add(threadTimeout == null ? lockTimeout : threadTimeout);
                long a = (Long) j.eval(getlock, keys, args);
                if (a == 0) {
                    logger.info("receive the redis lock,key: " + keys.get(0) + ",value:" + args.get(0) + ",timeout:" + args.get(1));
                    return true;
                } else {
                    logger.error("receive the redis lock error:" + a + ",key:" + keys.get(0) + ",value:" + args.get(0) + ",timeout:" + args.get(1));
                }
                tempThreadWaitTimeout -= 100;
                Thread.sleep(100);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedisSentinelPool.returnResource(shardedJedis);
            }
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param keys
     * @param args
     */
    public void release(List<String> keys, List<String> args) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisSentinelPool.getResource();
            Jedis j = shardedJedis.getShard(keys.get(0));
            long b = (Long) j.eval(removelock, keys, args);
            if (b != 0) {
                logger.error("release lock error," + b);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedisSentinelPool.returnResource(shardedJedis);
            }
        }
    }
    
     /**
     * 获取流水号锁
     *
     * @param threadTimeout 锁超时时间
     * @param keys
     * @param args
     * @return
     */
    public boolean acquire4SerialNo(String threadTimeout, List<String> keys, List<String> args) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisSentinelPool.getResource();
            Jedis j = shardedJedis.getShard(keys.get(0));
            int tempThreadWaitTimeout = threadWaitTimeout;//把threadWaitTimeout赋给一个临时变量
            while (tempThreadWaitTimeout >= 0) {
                long nowtime = System.currentTimeMillis();
                args.add(Long.toString(nowtime));
                args.add(threadTimeout == null ? lockTimeout : threadTimeout);
                long a = (Long) j.eval(getlock, keys, args);
                if (a == 0) {
                    logger.info("receive app TmnSerialNo redis lock,key:----" + keys.get(0));
                    return true;
                }
                logger.info("do not receive app TmnSerialNo redis lock,key----" + keys.get(0));
                tempThreadWaitTimeout -= intervalTime;
                Thread.sleep(intervalTime);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }
}
