package cn.edugate.esb.redis;

/**
 * 缓存过期策略接口
 * 
 * @Name: 缓存过期策略接口
 * @Description: 缓存过期策略接口
 * @version V1.0
 */
public interface ExpiredStrategy<T> {
	
	/**
	 * 缓存更新
	 * @param info 缓存信息
	 */
	public void updateCache(CacheInfo<T> info);
	
	/**
	 * 数据是否过期
	 * @param info 缓存信息
	 * @return 数据是否过期
	 */ 
	public boolean isExpired(CacheInfo<T> info);
}
