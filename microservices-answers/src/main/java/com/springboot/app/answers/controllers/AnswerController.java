package com.springboot.app.answers.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.answers.models.entity.Answer;
import com.springboot.app.answers.services.AnswerServiceImplement;

@RestController
public class AnswerController {
	
	@Autowired
	private AnswerServiceImplement service;
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Iterable<Answer> answers) {
		answers = ((List<Answer>)answers).stream().map(a -> {
			a.setStudentId(a.getStudent().getId());
			a.setQuestion(a.getQuestion());
			return a;
		}).collect(Collectors.toList());		
		Iterable<Answer> answersDB = service.saveAllIterable(answers);
		return ResponseEntity.status(HttpStatus.CREATED).body(answersDB);
	}
	
	@GetMapping("/student/{studentId}/test/{testId}")
	public ResponseEntity<?> getAnswerByStudentByTest (@PathVariable Long studentId, @PathVariable Long testId) {
		Iterable<Answer> answers = service.findAnswerByStudentByTest(studentId, testId);
		return ResponseEntity.ok(answers);		
	}
	
	@GetMapping("/student/{studentId}/test-answered")
	public ResponseEntity<?> getTestIdWithStudentsAnswers(@PathVariable Long studentId) {
		Iterable<Long> answersIds = service.findTestIdsWithAnswersAndStudentId(studentId);
		return ResponseEntity.ok(answersIds);		
	}
	
}
