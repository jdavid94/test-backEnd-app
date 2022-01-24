package com.springboot.app.tests.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.commons.tests.models.entity.Test;

public interface ITestRepository extends PagingAndSortingRepository<Test, Long> {
	
	@Query("Select t from Test t where t.name like %?1%")
	public List<Test> findByName(String term);
	
	//@Query("Select t.id from Question q join q.test t where q.Id in ?1 group by t.id")
	//public Iterable<Long> findTestIdsWithAnswersByQuestionIds(Iterable<Long> questionIds);
}
