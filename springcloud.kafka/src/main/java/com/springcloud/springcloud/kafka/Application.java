package com.springcloud.springcloud.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.springcloud.springcloud")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
