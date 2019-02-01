package com.shihy.springboot.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 加载Bean类
 * @data 2019年1月24日 下午4:18:26
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String beanName) {
		return  applicationContext.getBean(beanName);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(Class c) {
		return applicationContext.getBean(c);
	}
		
}
