package com.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan(basePackages = {"com.academy.dao", "com.academy.service", "com.academy.vo"})
public class AngelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngelApplication.class, args);
	}

}
