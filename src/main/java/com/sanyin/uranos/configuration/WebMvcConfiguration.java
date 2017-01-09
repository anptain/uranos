package com.sanyin.uranos.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sanyin.uranos.web.interceptor.MyInterceptor;

@Configuration
@EnableWebSecurity
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("error/403");
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
	}
}