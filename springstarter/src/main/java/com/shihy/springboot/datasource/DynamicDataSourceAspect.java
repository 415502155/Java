package com.shihy.springboot.datasource;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.reflect.MethodSignature;
/***
 * @Title: springstarter
 * @author shy
 * @Description AOP的方式实现数据源动态切换
 * @data 2019年3月28日 下午3:59:04
 *
 */
@Aspect
@Order(-10)//保证该AOP在@Transactional之前执行
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @SuppressWarnings("rawtypes")
	@Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point) {//, DS ds
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        log.info("当前访问的CLASS ：" + className.getName());
        log.info("当前访问的METHOD ：" + methodName);
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
                log.info("取出注解中的数据源名 :" + dataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("beforeSwitchDS Exception : " + e);
        }
        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);
    }
    
    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point){
        DataSourceContextHolder.clearDB();
    }
    
/*    @AfterThrowing("@annotation(DS)")
    public void afterThrowing(Exception e) {
    	
    }*/
}
