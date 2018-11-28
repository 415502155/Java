package cn.edugate.esb.authentication;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE})
@Retention(RUNTIME)
public @interface FPD {
	/**
	 * 功能名称
	 * @return 功能名称
	 */
	public String value();
	/**
	 * 访问控制权限列表
	 * @return 访问控制权限列表
	 */
	public AC[] acs() default {AC.create};
	

}
