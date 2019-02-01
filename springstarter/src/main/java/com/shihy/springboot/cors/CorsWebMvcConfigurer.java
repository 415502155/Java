package com.shihy.springboot.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description cors跨域
 * @data 2019年1月23日 上午8:49:34
 *
 */
@Configuration
public class CorsWebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
