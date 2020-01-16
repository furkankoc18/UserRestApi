package com.kocfurkan.main;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = {"com.kocfurkan.entity"})  
@EnableJpaRepositories("com.kocfurkan.repository")
@ComponentScan(basePackages = {"com.kocfurkan"})
@EnableConfigurationProperties
@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(UserApplication.class);
		logger.info("application is running");
		SpringApplication.run(UserApplication.class, args);
	}

}
