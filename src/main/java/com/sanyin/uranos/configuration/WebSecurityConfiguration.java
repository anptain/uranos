package com.sanyin.uranos.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/vote","/repo","/","/lib/**","/css/**", "/js/**", "/images/**", "/webjars/**", "/**/favicon.ico");
	}
}