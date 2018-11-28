package cn.edugate.esb.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edugate.esb.dao.DBListener;

public class DBListenerStrategy <T> implements ExpiredStrategy<T>,DBListener,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	static Logger logger=LSLoggerFactory.getLogger();
	static Logger logger=LoggerFactory.getLogger(DBListenerStrategy.class);
	private CacheProvider<T> provider;	

	/**
	 * 数据是否过期
	 * @param info 缓存信息
	 * @return 数据是否过期
	 */ 
	@Override
	public boolean isExpired(CacheInfo<T> info) {
		// TODO Auto-generated method stub
		boolean expired=(info==null);
	
		return expired;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changed(ChangeType changeType, Class<?> enType, Object en) {
		// TODO Auto-generated method stub
		
		if(this.provider!=null){
			switch (changeType) {
			case Insert:
				this.provider.add((T) en);
				break;
			case Update:
				this.provider.update((T) en);
				break;
			case Delete:
				this.provider.delete((T) en);
				break;
			default:
				break;
			}
			
		}
	}

	@Override
	public void updateCache(CacheInfo<T> info) {
	
	}
	
	public CacheProvider<T> getProvider() {
		return provider;
	}

	public void setProvider(CacheProvider<T> provider) {
		this.provider = provider;
	}

}
