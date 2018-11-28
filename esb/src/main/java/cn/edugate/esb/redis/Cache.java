package cn.edugate.esb.redis;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE})
@Retention(RUNTIME)
public @interface Cache {
	/**
	 * 缓存名称
	 * @return 缓存名称
	 */
	public String name() default "";
	
	/**
	 * 过期时间，单位为分钟
	 * @return 过期时间
	 */
	public int expired() default 1440;
	
	/**
	 * 跨时间段变更类型
	 * @return 跨时间段变更类型
	 */
	public TimeField changeField() default TimeField.NONE;
	
	/**
	 * 关联的实体类
	 * @return 关联的实体类
	 */
	public Class<?>[] entities() default {};
	
}
