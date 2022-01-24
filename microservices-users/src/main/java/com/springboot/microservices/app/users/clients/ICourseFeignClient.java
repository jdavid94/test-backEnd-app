package com.springboot.microservices.app.users.clients;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservice-courses")
public interface ICourseFeignClient {
	
	@DeleteMapping("/delete-student/{id}")
	public void deleteCourseStudentById(@PathVariable Long id);
}
