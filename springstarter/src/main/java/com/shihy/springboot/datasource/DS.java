package com.shihy.springboot.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 自定义注解
 * @data 2019年3月28日 下午3:52:49
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DS {
	 String value();
}
