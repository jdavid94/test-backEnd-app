package com.springboot.microservices.app.users.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.commons.services.CommonServiceImplement;
import com.springboot.app.students.models.entity.Student;
import com.springboot.microservices.app.users.clients.ICourseFeignClient;
import com.springboot.microservices.app.users.models.repository.IStudentRepository;

@Service
public class StudentServiceImplement extends CommonServiceImplement<Student, IStudentRepository> implements IStudentService {
	
	@Autowired
	private ICourseFeignClient courseClient;

	@Override
	@Transactional(readOnly = true)
	public List<Student> findByNameOrLastName(String term) {		
		return repository.findByNameOrLastName(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Student> findAllById(Iterable<Long> ids) {		
		return repository.findAllById(ids);
	}

	@Override
	public void deleteCourseStudentById(Long id) {	
		courseClient.deleteCourseStudentById(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {		
		super.delete(id);
		this.deleteCourseStudentById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Student> findAll() {		
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Student> findAll(Pageable pageable) {		
		return repository.findAllByOrderByIdAsc(pageable);
	}
	
	
	


}
