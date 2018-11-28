package cn.edugate.esb.redis;

import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnore;

public class CacheInfoImpl<T> implements CacheInfo<T>,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cacheKey;
	private String cacheName;
	private UUID uuid;
	private long version;
	private Date updateTime;
	private Date lastTime;
	
	private T data;
	private long cacheSize;
	
	private boolean expired;
	
	@JsonIgnore
	private final ExpiredStrategyManager<T> esm;
	
	public boolean isExpired() {
		return expired||this.esm.isExpired(this);
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public CacheInfoImpl(String cacheKey, String cacheName, UUID uuid,
			long version, Date updateTime,  T data,
			long cacheSize,ExpiredStrategyManager<T> esm) {
		this.cacheKey = cacheKey;
		this.cacheName = cacheName;
		this.uuid = uuid;
		this.version = version;
		this.updateTime = updateTime;
		this.data = data;
		this.cacheSize = cacheSize;
		this.esm=esm;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public String getCacheName() {
		return cacheName;
	}
	public UUID getUuid() {
		return uuid;
	}
	public long getVersion() {
		return version;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
//	public String getUpdateTimeStr(){
//		return DateUtil.formatDateTime(updateTime);
//	}
	public Date getLastTime() {
		return lastTime;
	}
	@JsonIgnore
	public T getData() {
		return data;
	}
	public long getCacheSize() {
		return cacheSize;
	}
	@Override
	public void setLastTime(Date lastTime) {
		// TODO Auto-generated method stub
		this.lastTime=lastTime;
	}
	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}
	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		
	}
}
