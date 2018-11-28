package cn.edugate.esb.redis;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * 
 * @Name: 过期策略管理器
 * @Description: 
 * @param <T>
 */
public class ExpiredStrategyManager<T> implements  ExpiredStrategy<T>,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Set<ExpiredStrategy<T>> es=new HashSet<ExpiredStrategy<T>>();
	
	public void register(ExpiredStrategy<T> e)
	{
		es.add(e);
	}
	
	public void unregister(ExpiredStrategy<T> e)
	{
		es.remove(e);
	}

	@Override
	public boolean isExpired(CacheInfo<T> info) {
		boolean expired=false;
		for(ExpiredStrategy<T> e:es)
		{
			expired=e.isExpired(info);
			if(expired)break;
		}
		return expired;
	}

	@Override
	public void updateCache(CacheInfo<T> info) {
		// TODO Auto-generated method stub
		for(ExpiredStrategy<T> e:es)
		{
			e.updateCache(info);
		}
	}
}
