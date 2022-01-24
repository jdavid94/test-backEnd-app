package com.springboot.app.courses.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservice-answers")
public interface IAnswerFeignClient {
	
	@GetMapping("/student/{studentId}/test-answered")
	public Iterable<Long> getTestIdWithStudentsAnswers(@PathVariable Long studentId);
}
