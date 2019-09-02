package com.SpringBoot.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.SpringBoot.main.interceptor.admin.LoginInterceptor;

@SpringBootApplication
@MapperScan(basePackages = "com.SpringBoot.main.dao.admin",annotationClass = Repository.class)
@ComponentScan
public class SpringBootNewsApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootNewsApplication.class, args);
	}
		
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
		ir.addPathPatterns("/admin/**","/system/*").excludePathPatterns("/system/login","/system/loginAct","/system/cpache","/resources/**");
	}
}
