package com.springboot.app.answers.models.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springboot.app.answers.models.entity.Answer;

public interface IAnswerRepository extends MongoRepository<Answer, String> {
	
	
	@Query("{'studentId': ?0, 'questionId': {$in: ?1}}")
	public Iterable<Answer> findAnswerByStudentByQuestionIds(Long studentId, Iterable<Long> questionId);	
	
	@Query("{'studentId':?0}")
	public Iterable<Answer> findByStudentId(Long studentId);
	
	@Query("{'studentId': ?0 }, 'question.test.id': ?1 ")
	public Iterable<Answer> findAnswerByStudentByTest(Long studentId, Long testId);
	
	@Query(value = "{'studentId':?0}", fields = "{'question.test.id': 1}")
	public Iterable<Answer> findTestIdsByAnswersByStudent(Long studentId);
	
	
	//@Query("Select a from Answer a join fetch a.question q join fetch q.test t where a.studentId=?1 and t.id=?2") // fetch All the elements in one call
		//public Iterable<Answer> findAnswerByStudentByTest(Long studentId, Long testId);
	
	//@Query("Select t.id from Answer a join a.question q join q.test t where a.studentId=?1 group by t.id")
	//public Iterable<Long> findTestIdsWithAnswersandStudentId(Long studentId);
	
	

	
}
