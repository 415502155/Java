package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.redis.CacheInfo;
import cn.edugate.esb.redis.CacheObject;
import cn.edugate.esb.redis.CacheProvider;

/**
 * 
 * 
 * @Name: 数据缓存服务接口
 * @Description: 
 */
public interface CacheService {
	/**
	 * 获取缓存数据
	 * @param provider 缓存提供器
	 * @return 缓存数据
	 */
	public <T> T getData(CacheProvider<T> provider);
	/**
	 * 获取缓存信息
	 * @param provider 缓存提供器
	 * @return 缓存信息
	 */
	public <T> CacheInfo<T> getCacheInfo(CacheProvider<T> provider);
	/**
	 * 清除缓存数据
	 * @param provider 缓存提供器
	 */
	public <T> void clearCache(CacheProvider<T> provider);
	/**
	 * 清除缓存数据
	 * @param cacheKey 缓存Key
	 */
	public void clearCache(String cacheKey);
	/**
	 * 清除所有缓存数据
	 */
	public void clearAllCache();
	/**
	 * 重新加载缓存数据
	 * @param cacheKey 缓存Key
	 */
	public void reloadCache(String cacheKey);
	
	/**
	 * 重新加载所有缓存数据
	 */
	public void reloadAllCache();
	
	/**
	 * 通过keyname获取缓存信息
	 * @return 缓存信息
	 */
	public CacheObject<?> getCacheInfo(String keyname);
	/**
	 * 获取缓存信息
	 * @return 缓存信息
	 */
	public List<CacheInfo<?>> getCacheInfos();
	
	public <T> void addListener(CacheProvider<T> provider,CacheServiceListener<T> listener);
	public <T> void removeListener(CacheProvider<T> provider,CacheServiceListener<T> listener);
	
	public <T> long getVersion(CacheProvider<T> provider);
	
}
