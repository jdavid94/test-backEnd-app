package com.springboot.app.answers.services;


import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.springboot.app.answers.clients.ITestFeignClient;
import com.springboot.app.answers.models.entity.Answer;
import com.springboot.app.answers.models.repository.IAnswerRepository;
//import com.springboot.commons.tests.models.entity.Question;
//import com.springboot.commons.tests.models.entity.Test;

@Service
public class AnswerServiceImplement implements IAnswerService {

	@Autowired
	private IAnswerRepository repository;
	
	//@Autowired
	//private ITestFeignClient testClient;
	
	@Override
	public Iterable<Answer> saveAllIterable(Iterable<Answer> answers) {		
		return repository.saveAll(answers);
	}

	@Override	
	public Iterable<Answer> findAnswerByStudentByTest(Long studentId, Long testId) {	
		/* Test test = testClient.getTestById(testId);
		List<Question> questions = test.getQuestions();
		List<Long> questionIds = questions.stream().map(q -> q.getId()).collect(Collectors.toList());
		List<Answer> answers = (List<Answer>) repository.findAnswerByStudentByQuestionIds(studentId, questionIds);
		answers = answers.stream().map(a -> {
			questions.forEach(q -> {
				if (q.getId() == a.getQuestion().getId()) {
					a.setQuestion(q);
				}
			});
			return a;
		}).collect(Collectors.toList());*/
		List<Answer> answers = (List<Answer>) repository.findAnswerByStudentByTest(studentId, testId);
		return answers;
	}

	@Override	
	public Iterable<Long> findTestIdsWithAnswersAndStudentId(Long studentId) {	
		/*List<Answer> answersStudent = (List<Answer>) repository.findByStudentId(studentId);
		List<Long> testIds = Collections.emptyList();
		if (answersStudent.size() > 0) {
			List<Long> questionIds = answersStudent.stream().map(a -> a.getQuestion().getId()).collect(Collectors.toList());
			testIds = testClient.getTestIdsByQuestionsIdsAnswered(questionIds);
		}*/
		List<Answer> answersStudent = (List<Answer>) repository.findTestIdsByAnswersByStudent(studentId);
		List<Long> testIds = answersStudent.stream().map(r -> r.getQuestion().getTest().getId()).distinct().collect(Collectors.toList());
		return testIds;
	}

	@Override
	public Iterable<Answer> findByStudentId(Long studentId) {		
		return repository.findByStudentId(studentId);
	}

}
