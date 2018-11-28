package cn.edugate.esb;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EduApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;//声明一个静态变量保存
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.context=applicationContext;
	}
	public ApplicationContext getContext(){
	  return context;
	}
}
