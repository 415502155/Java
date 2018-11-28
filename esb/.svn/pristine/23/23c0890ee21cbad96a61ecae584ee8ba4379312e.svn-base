package cn.edugate.esb.version;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.Mapping; 
/**
 * 
 * 
 * @Name: 版本号控制类
 * @Description: 
 * @date  2013-4-8 
 * @version V1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})  
@Retention(RUNTIME)  
@Documented  
@Mapping 
public @interface ApiVersion {
	/** 
     * 标识版本号 
     * @return 
     */
    int value(); 
}
