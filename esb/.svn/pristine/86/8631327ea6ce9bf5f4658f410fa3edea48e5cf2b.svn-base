package cn.edugate.esb.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import net.sourceforge.sizeof.SizeOf;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.core.convert.Property;




/**
 * 
 * 
 * @Name: 
 * @Description:  
 * @date  2013-4-12 
 * @version V1.0
 */
public abstract class LSHelper {
	static Logger logger=LoggerFactory.getLogger(LSHelper.class);
	static ThreadLocal<AutowiredAnnotationBeanPostProcessor> local_bpp = new ThreadLocal<AutowiredAnnotationBeanPostProcessor>();
	static BeanFactory beanfactory=null;
	
	public static BeanFactory getBeanfactory() {
		return beanfactory;
	}


	private static AutowiredAnnotationBeanPostProcessor getBpp()
	{
		AutowiredAnnotationBeanPostProcessor bpp=local_bpp.get();
		if(bpp==null)
		{
			bpp=new AutowiredAnnotationBeanPostProcessor();
			local_bpp.set(bpp);
		}
		return bpp;
	}
	
	
	public static void processInjection(Object target) {
		if(beanfactory!=null)
		{
			AutowiredAnnotationBeanPostProcessor bpp=getBpp();
			bpp.setBeanFactory(beanfactory);
			bpp.processInjection(target);
		}
	}
	
	public static long sizeOf(Object o)
	{
		long size=0;
		if(o!=null)
		{
			try
			{
				size=SizeOf.deepSizeOf(o);
			}catch(Exception ex)
			{
				logger.warn("SizeOf error,是否未加载 SizeOf.jar");
			}
		}
		return size;
	}
		
	
	public static <T> List<T> getPojoFromRequest(Object data, Class<T> pojoClass,JacksonSetting setting) {

		List<T> list;

		// it is an array - have to cast to array object
		if (data.toString().trim().startsWith("[")) {

			list = getPojosFromJSON(data, pojoClass,setting);

		} else { // it is only one object - cast to object/bean

			T pojo = getPojoFromJSON(data, pojoClass,setting);

			list = new ArrayList<T>();
			if(pojo!=null)list.add(pojo);
		}

		return list;
	}
	
	
	
	public static <T> T getPojoFromJSON(Object data, Class<T> pojoClass,JacksonSetting setting) {
		
		T pojo = null;
		try {
			pojo = JacksonFactory.getMapper(setting).readValue((String)data, pojoClass);
			if(pojo!=null)processInjection(pojo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pojo;
	}
	

	
	/**
	 * 
	 * @param data
	 * @param typeRef
	 * @param setting
	 * @return
	 * 
	 * 用法：
	 * List<PNode> ns2=LSHelper.getPojoFromJSON(s2, new TypeReference<List<PNode>>(){},null);
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPojoFromJSON(Object data, TypeReference<T> typeRef,JacksonSetting setting) {
		T pojo = null;
		try {
			pojo = ((T)JacksonFactory.getMapper(setting).readValue((String)data, typeRef));
			if(pojo!=null)processInjection(pojo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pojo;
	}
	


	public static <T> List<T> getPojosFromJSON(Object data, Class<T> pojoClass,JacksonSetting setting) {
		List<T> pojos = null;
		try {
			TypeReference<List<T>> typeRef = buildTypeRef(pojoClass);
			pojos = JacksonFactory.getMapper(setting).readValue((String)data, typeRef);
			if(pojos!=null)
			{
				for(T pojo:pojos)
				{
					if(pojo!=null)processInjection(pojo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pojos;
	}

	private static <T> TypeReference<List<T>> buildTypeRef(Class<T> pojoClass)
			throws NoSuchFieldException, IllegalAccessException {
		TypeReference<List<T>> typeRef=new TypeReference<List<T>>() {};
		ParameterizedType t=(ParameterizedType)typeRef.getType();
		Field field=t.getClass().getDeclaredField("actualTypeArguments");
		if(field!=null)
		{
			field.setAccessible(true);
			field.set(t, new Type[]{pojoClass});
		}
		return typeRef;
	}

	public static String getJSONFromPOJO(Object data,JacksonSetting setting) {
		String s="";
		try {
			s=JacksonFactory.getMapper(setting).writeValueAsString(data);
		}catch (Exception e) {
			s="";
			logger.error("",e);
		}
		return s;
	}
	
	
	public  static <T> int hashCodeForPOJO(Class<T> clazz,T s)
	{
		int code=clazz.hashCode();
		Field[] fields=clazz.getDeclaredFields();
		for(Field f:fields)
		{
			if(f.getAnnotation(Transient.class)!=null)
			{
				f.setAccessible(true);
				try {
					Object o=f.get(s);
					if(o!=null)
					{
						code+=o.hashCode();
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return code;
	}
	
	public static <T> boolean equalsForPOJO(Class<T> clazz,T s,T t)
	{
		boolean equals=true;
		Field[] fields=clazz.getDeclaredFields();
		for(Field f:fields)
		{
			if(f.getAnnotation(Transient.class)==null)
			{
				f.setAccessible(true);
				try {
					Object so=f.get(s);
					Object to=f.get(t);
					equals=equals(so,to);
					if(!equals)break;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return equals;
	}
	
	public static boolean equals(Object s,Object t)
	{
		boolean equals=false;
		if(s!=null)
		{
			if(t!=null)
			{
				if(s.getClass().isArray()&&t.getClass().isArray())
				{
					if(Array.getLength(s)==Array.getLength(t))
					{
						equals=true;
						int len=Array.getLength(s);
						for(int i=0;i<len;i++)
						{
							equals=equals(Array.get(s, i),Array.get(t, i));
							if(!equals)break;
						}
					}else
					{
						equals=false;
					}
					
				}else
				{
					equals=s.equals(t);
				}
				
			}else
			{
				equals=false;
			}
			
		}else
		{
			equals=(s==t);
		}
		return equals;
	}
	
	public static List<Property> getProperties(Class<?> clazz)
	{
		List<Property> properties=new ArrayList<Property>();
		PropertyDescriptor[] pds=BeanUtils.getPropertyDescriptors(clazz);
		for(PropertyDescriptor pd :pds)
		{
			properties.add(property(pd));
		}
		return properties;
	}
	
	private static Property property(PropertyDescriptor pd) {
		return new Property(pd.getPropertyType(), pd.getReadMethod(), pd.getWriteMethod(), pd.getName());
	}
	final static DateFormat time_fmt=new SimpleDateFormat("HH:mm:ss");
	
	public static Date parseTime(String s)
	{
		Date time=null;
		try {
			time= time_fmt.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	
	public static Date now() {
		
		return new Date();
	}

	
	public static Date getDate(Date date) {
		// TODO Auto-generated method stub
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	public static Date getTime(Date date) {
		// TODO Auto-generated method stub
		//TimeZone.getTimeZone("GMT")
		Calendar cal=Calendar.getInstance();		 		
		cal.setTime(date);
		cal.set(Calendar.YEAR, 1970);
	    cal.set(Calendar.MONTH, 0);
	    cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	
	
}
