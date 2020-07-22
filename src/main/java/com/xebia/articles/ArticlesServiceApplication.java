package com.xebia.articles;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author dhekumar2
 *
 */
@SpringBootApplication
public class ArticlesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticlesServiceApplication.class, args);
	}
	
	@Bean
	public Mapper mapper() {
	    return new DozerBeanMapper();
	}

}
