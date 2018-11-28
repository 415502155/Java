package cn.edugate.esb.redis;


/**
 * 
 * 
 * @Name: 按时间过期策略
 * @Description: 按时间过期策略
 */
public class TimeStrategy<T> implements ExpiredStrategy<T>,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long expiredTime=-1;
	
	
	public long getExpiredTime() {
		return expiredTime;
	}



	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}
	
	

	public TimeStrategy()
	{
		
	}

	/**
	 * 
	 * @param expiredTime 过期时间，单位为毫秒
	 */
	public TimeStrategy(long expiredTime) {
		this.expiredTime = expiredTime;
	}


	/**
	 * 数据是否过期
	 * @param info 缓存信息
	 * @return 数据是否过期
	 */ 
	public boolean isExpired(CacheInfo<T> info) {
		boolean expired=true;
		if(info.getUpdateTime()==null)
		{
			expired= true;
		}else if(this.expiredTime<0)
		{
			expired= false;
		}else
		{
			expired= (System.currentTimeMillis()-info.getUpdateTime().getTime())>this.expiredTime;
		}
		return expired;
	}



	@Override
	public void updateCache(CacheInfo<T> info) {
		// TODO Auto-generated method stub
		
	}

	

}
