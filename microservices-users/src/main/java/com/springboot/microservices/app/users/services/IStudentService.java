package com.springboot.microservices.app.users.services;

import java.util.List;


import com.springboot.app.commons.services.ICommonService;
import com.springboot.app.students.models.entity.Student;

public interface IStudentService extends ICommonService<Student> {
	
	public List<Student> findByNameOrLastName(String term);
	public Iterable<Student> findAllById(Iterable<Long> ids);
	public void deleteCourseStudentById(Long id);

}
