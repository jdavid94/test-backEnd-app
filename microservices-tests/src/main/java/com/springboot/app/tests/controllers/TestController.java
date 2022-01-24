package com.springboot.app.tests.controllers;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.commons.controllers.CommonController;
import com.springboot.app.tests.services.ITestService;
import com.springboot.commons.tests.models.entity.Question;
import com.springboot.commons.tests.models.entity.Test;

@RestController
public class TestController extends CommonController<Test, ITestService> {
	
	@GetMapping("/answered-by-questions")
	public ResponseEntity<?> getTestIdsByQuestionsIdsAnswered(@RequestParam List<Long> questionIds) {
		return ResponseEntity.ok().body(service.findTestIdsWithAnswersByQuestionIds(questionIds));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Test test, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validated(result);
		}
		Optional<Test> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Test testDB = o.get();		
		testDB.setName(test.getName());	
		
		// GET QUESTIONS AND CONVERT TO STREAM 
		List<Question> deleted = testDB.getQuestions()
		.stream()
		.filter(qdb -> !test.getQuestions().contains(qdb)) // IF CONTAINS OR NOT
		.collect(Collectors.toList());
		deleted.forEach(testDB::removeQuestions);	
		
		testDB.setQuestions(test.getQuestions());	
		testDB.setSubjectSon(test.getSubjectSon());
		testDB.setSubjectFather(test.getSubjectFather());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(testDB));
	}
	
	@GetMapping("/filter/{term}")
	public ResponseEntity<?> filter (@PathVariable String term) {
		return ResponseEntity.ok(service.findByName(term));
	}
	
	@GetMapping("/subjects")
	public ResponseEntity<?> listSubjects () {
		return ResponseEntity.ok(service.findAllSubjects());
	}

}
