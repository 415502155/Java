package cn.edugate.esb.util;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;


@Component
public class JacksonConfig implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof MappingJacksonHttpMessageConverter) {
            MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) bean;
            jsonConverter.setObjectMapper(JacksonFactory.getMapper(null));
           
        }
        return bean;
    }
}
