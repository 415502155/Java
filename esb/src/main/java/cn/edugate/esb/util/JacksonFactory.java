package cn.edugate.esb.util;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonFactory {
	static Logger logger=LoggerFactory.getLogger(JacksonFactory.class);
	
	private static final  ObjectMapper oMapper=new ObjectMapper();
	private static final Map<JacksonSetting,ObjectMapper> caches = new HashMap<JacksonSetting,ObjectMapper>();
	private static final JacksonSetting nullSetting = JacksonSetting.c();
	
	static
	{
		oMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
	}
	
	public static ObjectMapper getMapper(JacksonSetting setting)
	{
		ObjectMapper mapper=null;
		JacksonSetting key=null;
		if(setting!=null)
		{
			key=setting.copy();
		}else
		{
			key=nullSetting;
		}
		synchronized (caches) {
			logger.debug("getMapper for {} ",key);
			mapper=caches.get(key);
			if(mapper==null)
			{
				mapper = createMapperAddCache(key);
			}
		}
		return mapper;
	}

	private static ObjectMapper createMapperAddCache(JacksonSetting key) {
		logger.debug("createMapperAddCache for {}",key);
		ObjectMapper mapper=null;
		SerializationConfig serializationConfig=oMapper.copySerializationConfig();
		DeserializationConfig deserializationConfig=oMapper.copyDeserializationConfig();
		LSAnnotationIntrospector ai=LSAnnotationIntrospector.getAnnotationIntrospector(key.getIgnores());
		
		if(ai!=null)
		{
			serializationConfig=serializationConfig.withInsertedAnnotationIntrospector(ai);
			deserializationConfig=deserializationConfig.withInsertedAnnotationIntrospector(ai);
		}
		if(!key.getMixins().isEmpty())
		{
			serializationConfig.setMixInAnnotations(key.getMixins());
			deserializationConfig.setMixInAnnotations(key.getMixins());
		}
		mapper= new ObjectMapper(oMapper.getJsonFactory(),null,null,serializationConfig,deserializationConfig);
		synchronized (caches) {
			caches.put(key, mapper);
		}
		return mapper;
	}
	
}
