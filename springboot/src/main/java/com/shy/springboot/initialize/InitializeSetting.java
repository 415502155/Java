package com.shy.springboot.initialize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.shy.springboot.utils.StaticVariable;
/***
 * @deprecated 初始化配置信息
 * 两种方式：①：实现CommandLineRunner，重写run()
 *       ②：实现ApplicationRunner，重写run()
 * 
 *
 */
@Component
@Order(3)//根据order的值的大小决定启动顺序
public class InitializeSetting implements ApplicationRunner {
	
	@Value("${PROJECT_NAME}")
	private String PROJECT_NAME;
	
	@Value("${PACKAGE_NAME}")
	private String PACKAGE_NAME;
	
	@Value("${SETTING_NAME}")
	private String SETTING_NAME;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		StaticVariable.projectName = PROJECT_NAME;
		StaticVariable.packageName = PACKAGE_NAME;
		StaticVariable.settingName = SETTING_NAME;
	}
	
}
