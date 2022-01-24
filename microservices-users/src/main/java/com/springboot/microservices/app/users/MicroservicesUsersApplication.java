package com.springboot.microservices.app.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.springboot.app.students.models.entity"})
public class MicroservicesUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesUsersApplication.class, args);
	}

}
