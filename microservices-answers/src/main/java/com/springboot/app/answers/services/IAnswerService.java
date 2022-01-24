package com.springboot.app.answers.services;

import com.springboot.app.answers.models.entity.Answer;

public interface IAnswerService {
	
	public Iterable<Answer> saveAllIterable(Iterable<Answer> answers);
	public Iterable<Answer> findAnswerByStudentByTest(Long studentId, Long testId);
	public Iterable<Long> findTestIdsWithAnswersAndStudentId(Long studentId);
	public Iterable<Answer> findByStudentId(Long studentId);
}
