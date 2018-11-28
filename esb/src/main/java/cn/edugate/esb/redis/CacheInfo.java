package cn.edugate.esb.redis;

import java.util.Date;
import java.util.UUID;

public interface CacheInfo<T> {
	public String getCacheKey();
	public String getCacheName();
	public UUID getUuid();
	public long getVersion();
	public Date getUpdateTime();
	public Date getLastTime();
	public void setLastTime(Date lastTime);
	
	public T getData();
	public long getCacheSize();
	public boolean isExpired();
	public void refreshAll();
}