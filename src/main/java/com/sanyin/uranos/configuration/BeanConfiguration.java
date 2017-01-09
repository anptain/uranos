package com.sanyin.uranos.configuration;

import java.util.Properties;

import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class BeanConfiguration {

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(FreeMarkerProperties properties) {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPaths(properties.getTemplateLoaderPath());
		configurer.setPreferFileSystemAccess(properties.isPreferFileSystemAccess());
		configurer.setDefaultEncoding(properties.getCharsetName());
		Properties settings = new Properties();
		settings.putAll(properties.getSettings());
		configurer.setFreemarkerSettings(settings);
		return configurer;
	}

	@Bean
	public DefaultKaptcha defaultKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties prop = new Properties();
		// prop.put("kaptcha.border", "no");
		// prop.put("kaptcha.border", "no");
		// prop.put("kaptcha.border.color", "105,179,90");
		// prop.put("kaptcha.textproducer.font.color", "red");
		// prop.put("kaptcha.image.width", "250");
		// prop.put("kaptcha.textproducer.font.size", "80");
		// prop.put("kaptcha.image.height", "90");
		// prop.put("kaptcha.session.key", "code");
		// prop.put("kaptcha.textproducer.char.length", "4");
		// prop.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		defaultKaptcha.setConfig(new Config(prop));
		return defaultKaptcha;
	}
}
