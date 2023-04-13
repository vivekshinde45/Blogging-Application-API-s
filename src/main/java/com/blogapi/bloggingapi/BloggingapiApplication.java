package com.blogapi.bloggingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class BloggingapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggingapiApplication.class, args);
		System.out.println("Application Started.................................");
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
