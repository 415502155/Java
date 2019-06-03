package com.shy.springboot.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Component
@Configuration
@EnableSwagger
public class SwaggerConfig {
	
   /*private SpringSwaggerConfig springSwaggerConfig;

   public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
	   this.springSwaggerConfig = springSwaggerConfig;
   }
   
   @Bean
   public SwaggerSpringMvcPlugin customImplementation() {
      return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo());
   }

   private ApiInfo apiInfo() {
      ApiInfo apiInfo = new ApiInfo("", "", "", "", "", "");
      return apiInfo;
   }*/
}
