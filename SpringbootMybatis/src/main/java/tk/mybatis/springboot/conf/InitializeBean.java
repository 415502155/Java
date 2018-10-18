package tk.mybatis.springboot.conf;


import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.stereotype.Service;  
@Service  
public class InitializeBean implements ApplicationContextAware {  
    private static ApplicationContext applicationContext;  
  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
    	InitializeBean.applicationContext = applicationContext;  
    }  
  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
    public static Object getBean(String beanName) {  
        return applicationContext.getBean(beanName);  
    }  
      
    public static <T>T getBean(String beanName , Class<T>clazz) {  
        return applicationContext.getBean(beanName , clazz);  
    }  
}  
//
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
//
//public class InitializeBean implements ApplicationContextAware {
//
//    private static ApplicationContext applicationContext;
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        InitializeBean.applicationContext = applicationContext;
//    }
//
//    public static ApplicationContext getApplicationContext() {
//        //checkApplicationContext();
//        return applicationContext;
//    }
//
//@SuppressWarnings("unchecked")
//    public static <T> T getBean(String name) {
//        //checkApplicationContext();
//        return (T) applicationContext.getBean(name);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> T getBean(Class<T> clazz) {
//        //checkApplicationContext();
//        return (T) applicationContext.getBeansOfType(clazz);
//    }
//
//    private static void checkApplicationContext() {
//        if (applicationContext == null) {
//            throw new IllegalStateException("applicaitonContext装载失败");
//        }
//    }
//
//    public static void refreshBean() {
//        ((AbstractRefreshableWebApplicationContext) applicationContext).refresh();
//    }
//}

