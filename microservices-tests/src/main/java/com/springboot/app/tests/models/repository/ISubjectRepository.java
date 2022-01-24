package com.springboot.app.tests.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commons.tests.models.entity.Subject;

public interface ISubjectRepository extends CrudRepository<Subject, Long> {

}
