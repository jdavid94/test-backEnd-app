package com.springboot.app.courses.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.app.students.models.entity.Student;

@FeignClient(name="microservice-users")
public interface IStudentFeignClient {
	
	@GetMapping("/students-by-course")
	public List<Student> getStudentsByCourse(@RequestParam List<Long> ids);
}
