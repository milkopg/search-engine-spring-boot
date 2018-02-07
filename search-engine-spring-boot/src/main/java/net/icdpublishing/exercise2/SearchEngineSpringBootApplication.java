package net.icdpublishing.exercise2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchEngineSpringBootApplication/* extends SpringBootServletInitializer*/ {
	/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SearchEngineSpringBootApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineSpringBootApplication.class, args);
	}
}
