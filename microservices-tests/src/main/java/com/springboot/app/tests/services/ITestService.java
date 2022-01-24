package com.springboot.app.tests.services;

import java.util.List;

import com.springboot.app.commons.services.ICommonService;
import com.springboot.commons.tests.models.entity.Subject;
import com.springboot.commons.tests.models.entity.Test;

public interface ITestService extends ICommonService<Test>  {
	
	public List<Test> findByName(String term);
	public List<Subject> findAllSubjects();
	public Iterable<Long> findTestIdsWithAnswersByQuestionIds(Iterable<Long> questionIds);

}
