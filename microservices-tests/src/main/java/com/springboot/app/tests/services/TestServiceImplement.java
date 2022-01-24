package com.springboot.app.tests.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.commons.services.CommonServiceImplement;
import com.springboot.app.tests.models.repository.ISubjectRepository;
import com.springboot.app.tests.models.repository.ITestRepository;
import com.springboot.commons.tests.models.entity.Subject;
import com.springboot.commons.tests.models.entity.Test;

@Service
public class TestServiceImplement extends CommonServiceImplement<Test, ITestRepository> implements ITestService {
	
	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Test> findByName(String term) {		
		return repository.findByName(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subject> findAllSubjects() {
		return (List<Subject>) subjectRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findTestIdsWithAnswersByQuestionIds(Iterable<Long> questionIds) {		
		return null;
		//return repository.findTestIdsWithAnswersByQuestionIds(questionIds);
	}


}
