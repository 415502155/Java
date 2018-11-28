package cn.edugate.esb.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edugate.esb.service.CacheServiceListener;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Stopwatch;
import cn.edugate.esb.util.Util;

@JsonAutoDetect
public class CacheObject<T> implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	static Logger logger=LSLoggerFactory.getLogger();
	static Logger logger=LoggerFactory.getLogger(CacheObject.class);
	
	private String cacheName;
	private String cacheKey;
	private long cacheSize=0;
	private final List<CacheServiceListener<T>> listeners=new ArrayList<CacheServiceListener<T>>();
	private Util util=null;
	private int version=1;
	
	public CacheObject(String cacheKey,Util util) {
		this.cacheKey = cacheKey;
		this.util=util;
	}

	private T data=null;
	private CacheInfoImpl<T> info=null;
	private final ExpiredStrategyManager<T> esm=new ExpiredStrategyManager<T>();
	

	private CacheProvider<T> provider;
	
	@JsonIgnore
	public ExpiredStrategyManager<T> getEsm() {
		return esm;
	}
	@JsonIgnore
	public CacheProvider<T> getProvider() {
		return provider;
	}
	public void setProvider(CacheProvider<T> provider) {
		this.provider = provider;
	}
	
	public String getCacheKey() {
		// TODO Auto-generated method stub
		return cacheKey;
	}
	
	public String getCacheName() {
		return cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	
	private boolean isExpired()
	{
		boolean expired=info==null||this.info.isExpired();
		
		return expired;
	}
	
	public int getVersion()
	{
		return version;
	}
	
	@JsonIgnore
	public synchronized T getData()
	{
		if(this.isExpired())
		{
			Stopwatch sw=Stopwatch.begin();
//			this.data=provider.getData();
			this.cacheSize=LSHelper.sizeOf(this.data);
//			this.version++;			
			this.info=new CacheInfoImpl<T>(this.cacheKey,this.cacheName,UUID.randomUUID(),
					this.version++,this.util.now(),this.data,this.cacheSize,this.esm);
			this.esm.updateCache(this.info);
			sw.log(String.format("缓存数据[%s]更新完毕.", this.cacheName));
			this.notifyLoaded(this.data);
		}
		
//		this.info.setCacheSize(this.cacheSize);
		this.info.setLastTime(this.util.now());
		return data;
	}
	
	public CacheInfo<T> getCacheInfo()
	{
		this.getData();
		return this.info;	
	}
	
	private void notifyLoaded(T data)
	{
		for(CacheServiceListener<T> listener:listeners)
		{
			try
			{
				listener.loaded(data);
			}catch(Exception ex)
			{
				logger.error("",ex);
			}
		}
	}

	/**
	 * 清除缓存数据
	 */
	public void clearCache() {
		// TODO Auto-generated method stub
		if(this.info!=null)this.info.setExpired(true);
		this.info=null;
	}
	
	/**
	 * 重新加载缓存数据
	 */
	public void reloadCache() {
		this.clearCache();
		this.getData();
	}

	public long getCacheSize() {
		// TODO Auto-generated method stub
		return info==null?0:info.getCacheSize();
	}

	public void addListener(CacheServiceListener<T> listener)
	{
		listeners.add(listener);
		
	}

	public void removeListener(CacheServiceListener<T> listener)
	{
		listeners.remove(listener);
		
	}
	public void refreshAll() {
		// TODO Auto-generated method stub
		if(this.provider!=null){
			this.provider.refreshAll();
		}
	}
	public void refreshOrg(String org_id) {
		if(null!=this.provider){
			this.provider.refreshOrg(org_id);
		}
	}
}
