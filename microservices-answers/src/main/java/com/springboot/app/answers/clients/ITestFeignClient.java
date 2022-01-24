package com.springboot.app.answers.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.commons.tests.models.entity.Test;

@FeignClient(name="microservice-tests")
public interface ITestFeignClient {
	
	@GetMapping("/{id}")
	public Test getTestById(@PathVariable Long id);
	
	@GetMapping("/answered-by-questions")
	public List<Long> getTestIdsByQuestionsIdsAnswered(@RequestParam List<Long> questionIds);
}
