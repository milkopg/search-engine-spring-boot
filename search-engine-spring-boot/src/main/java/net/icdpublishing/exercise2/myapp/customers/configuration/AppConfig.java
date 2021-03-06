package net.icdpublishing.exercise2.myapp.customers.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")

@ComponentScan(basePackages= {"net.icdpublishing.exercise2"})
@Configuration 
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    return messageSource;
	}
}
