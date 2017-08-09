package tk.mybatis.springboot.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class CacheManager {
    private static HashMap cacheMap = new HashMap();


    /**
     * This class is singleton so private constructor is used.
     */
    public CacheManager() {
            super();
    }


    /**
     * returns cache item from hashmap
     * @param key
     * @return Cache
     */
    private synchronized static Cache getCache(String key) {
            return (Cache)cacheMap.get(key);
    }


    /**
     * Looks at the hashmap if a cache item exists or not
     * @param key
     * @return Cache
     */
    private synchronized static boolean hasCache(String key) {
            return cacheMap.containsKey(key);
    }


    /**
     * Invalidates all cache
     */
    public synchronized static void invalidateAll() {
            cacheMap.clear();
    }


    /**
     * Invalidates a single cache item
     * @param key
     */
    public synchronized static void invalidate(String key) {
            cacheMap.remove(key);
    }


    /**
     * Adds new item to cache hashmap
     * @param key
     * @return Cache
     */
    public synchronized static void putCache(String key, Cache cache) {
       cacheMap.put(key, cache);
    }


    /**
     * Reads a cache item's content
     * @param key
     * @return
     */
    public static Cache getContent(String key) {
             if (hasCache(key)) {
                    Cache cache = getCache(key);
                    if (cacheExpired(cache)) {
                            cache.setExpired(true);
                    }
                    return cache;
             } else {
                     return null;
             }
    }


    /**
     * 
     * @param key
     * @param content
     * @param ttl
     */
    public static void putContent(String key, Object content, long ttl) {
            Cache cache = new Cache();
            cache.setKey(key);
            cache.setValue(content);
            cache.setTimeOut(ttl + new Date().getTime());
            cache.setExpired(false);
            putCache(key, cache);
    }
    
    /** @modelguid {172828D6-3AB2-46C4-96E2-E72B34264031} */
    private static boolean cacheExpired(Cache cache) {
            if (cache == null) {
                    return false;
            }
            long milisNow = new Date().getTime();
            long milisExpire = cache.getTimeOut();
            if (milisExpire < 0) {                // Cache never expires 
                    return false;
            } else if (milisNow >= milisExpire) {
                    return true;
            } else {
                    return false;
            }
    }
    
    public static void main(String [] args) {
    	
    	Cache cache = new Cache();
    	File filepath = FileUtil.getFile("D:\\test.txt");
    	String content = FileUtil.getFileContent(filepath);
    	putContent("test", content, 6000);
    	//System.err.println(content);
    	System.err.println(getContent("test").getValue());

    }

}