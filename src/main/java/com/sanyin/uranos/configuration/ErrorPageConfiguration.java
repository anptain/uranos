package com.sanyin.uranos.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfiguration {

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
    	return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
				container.addErrorPages(error400Page, error401Page, error403Page,error404Page, error500Page);
			}
		};
    }
}