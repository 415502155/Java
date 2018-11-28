package cn.edugate.esb.imp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.Modifier;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.edugate.esb.dao.DBListenerService;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheInfo;
import cn.edugate.esb.redis.CacheObject;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.DBListenerStrategy;
import cn.edugate.esb.redis.TimeChangeStrategy;
import cn.edugate.esb.redis.TimeField;
import cn.edugate.esb.redis.TimeStrategy;
import cn.edugate.esb.redis.cache.Caches;
import cn.edugate.esb.service.CacheService;
import cn.edugate.esb.service.CacheServiceListener;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Select;
import cn.edugate.esb.util.Selector;
import cn.edugate.esb.util.Util;

/**
 * 
 * 
 * @Name: 数据缓存服务
 * @Description: 
 */
@Component
public class CacheServiceImpl implements CacheService,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	static Logger logger=LSLoggerFactory.getLogger();
	static Logger logger=LoggerFactory.getLogger(CacheServiceImpl.class);
	
	private Map<CacheProvider<?>,CacheObject<?>> caches=new HashMap<CacheProvider<?>,CacheObject<?>>();

	private DBListenerService dblService;
	
	@Autowired
	public void setDblService(DBListenerService dblService) {
		this.dblService = dblService;
	}
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}


	@PostConstruct
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void buildCaches()
	{
		synchronized (caches) {
			caches.clear();
			Field[] fields=Caches.class.getDeclaredFields();
			
			for(Field field:fields)
			{
				if(CacheProvider.class.isAssignableFrom(field.getType()))
				{
					if(Modifier.isStatic(field.getModifiers())){
						field.setAccessible(true);
						CacheProvider provider=null;
						try {
							provider = (CacheProvider)field.get(null);
						}catch (Exception e) {
							logger.error("",e);
						}
						if(provider!=null)
						{
							LSHelper.processInjection(provider);
							
							CacheObject<?> o=new CacheObject(field.getName(),this.util);
							Cache c=provider.getClass().getAnnotation(Cache.class);
							o.setCacheName(c.name());
							o.setProvider(provider);
							if(c.expired()>=0)
							{
								TimeStrategy es=new TimeStrategy();
								es.setExpiredTime(c.expired()*60*1000);
								o.getEsm().register(es);
							}
							if(c.changeField()!=TimeField.NONE)
							{
								TimeChangeStrategy es = new TimeChangeStrategy();
								es.setChageField(c.changeField());
								o.getEsm().register(es);
							}
							if(c.entities().length>0)
							{
								DBListenerStrategy es=new DBListenerStrategy();
								es.setProvider(provider);
								this.dblService.register(es, c.entities());
								o.getEsm().register(es);
							}
							
							caches.put(provider, o);
						}
					}
				}
			}
			
		}
	}
	
	

	@Override
	public <T> T getData(CacheProvider<T> provider) {
		T data=null;
		synchronized (caches) {
			if(provider!=null)
			{
				CacheObject<?> cache= caches.get(provider);
				if(cache!=null)
				{
					@SuppressWarnings("unchecked")
					T data2 = (T)cache.getData();
					data= data2;
				}
			}
			return data;
		}
	}
	
	@Override
	public <T> long getVersion(CacheProvider<T> provider) {
		long data=0;
		synchronized (caches) {
			if(provider!=null)
			{
				CacheObject<?> cache= caches.get(provider);
				if(cache!=null)
				{
					data = cache.getVersion();
				}
			}
			return data;
		}
	}

	@Override
	public <T> void clearCache(CacheProvider<T> provider) {
		// TODO Auto-generated method stub
		synchronized (caches) {
			if(provider!=null)
			{
				CacheObject<?> cache= caches.get(provider);
				if(cache!=null)
				{
					cache.clearCache();
				}
			}
		}
	}

	@Override
	public void clearCache(String cacheKey) {
		// TODO Auto-generated method stub
		for(CacheObject<?> co:caches.values())
		{
			if(co.getCacheKey().equals(cacheKey))
			{
				co.clearCache();
				break;
			}
		}
	}

	@Override
	public CacheObject<?> getCacheInfo(String keyname) {
		// TODO Auto-generated method stub
		CacheObject<?> ret=null;
		for(CacheObject<?> co:caches.values())
		{
			if(co.getCacheKey().equals(keyname))
			{
				ret = co;
				break;
			}
		}
		return ret;	
	}

	@Override
	public List<CacheInfo<?>> getCacheInfos() {
		// TODO Auto-generated method stub
		return Selector.from(this.caches.values()).orderBy(cn.edugate.esb.util.Order.asc("cacheKey"))
				.select(new Select<CacheInfo<?>, CacheObject<?>>() {

					@Override
					public CacheInfo<?> apply(CacheObject<?> e) {
						// TODO Auto-generated method stub
						return e.getCacheInfo();
					}
					
				});
	}



	@Override
	public void clearAllCache() {
		// TODO Auto-generated method stub
		for(CacheObject<?> co:caches.values())
		{
			co.clearCache();
		}
	}


	@Override
	public void reloadCache(String cacheKey) {
		// TODO Auto-generated method stub
		for(CacheObject<?> co:caches.values())
		{
			if(co.getCacheKey().equals(cacheKey))
			{
				co.reloadCache();
				break;
			}
		}
	}



	@Override
	public void reloadAllCache() {
		// TODO Auto-generated method stub
		for(CacheObject<?> co:caches.values())
		{
			co.reloadCache();
		}
	}



	@Override
	public <T> CacheInfo<T> getCacheInfo(CacheProvider<T> provider) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		CacheInfo<T> info = (CacheInfo<T>)caches.get(provider).getCacheInfo();
		return info;
	}




	@Override
	public <T> void addListener(CacheProvider<T> provider,
			CacheServiceListener<T> listener) {
		// TODO Auto-generated method stub
		synchronized (caches) {
			if(provider!=null)
			{
				@SuppressWarnings("unchecked")
				CacheObject<T> cache= (CacheObject<T>)caches.get(provider);
				if(cache!=null)
				{
					cache.addListener(listener);
				}
			}
		}
	}



	@Override
	public <T> void removeListener(CacheProvider<T> provider,
			CacheServiceListener<T> listener) {
		// TODO Auto-generated method stub
		synchronized (caches) {
			if(provider!=null)
			{
				@SuppressWarnings("unchecked")
				CacheObject<T> cache= (CacheObject<T>)caches.get(provider);
				if(cache!=null)
				{
					cache.removeListener(listener);
				}
			}
		}
	}
	

}
