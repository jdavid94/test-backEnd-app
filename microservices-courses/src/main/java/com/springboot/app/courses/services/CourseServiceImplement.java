package com.springboot.app.courses.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.commons.services.CommonServiceImplement;
import com.springboot.app.courses.clients.IAnswerFeignClient;
import com.springboot.app.courses.clients.IStudentFeignClient;
import com.springboot.app.courses.models.entity.Course;
import com.springboot.app.courses.models.repository.ICourseRepository;
import com.springboot.app.students.models.entity.Student;

@Service
public class CourseServiceImplement extends CommonServiceImplement<Course, ICourseRepository> implements ICourseService {

	@Autowired
	private IAnswerFeignClient client;
	
	@Autowired
	private IStudentFeignClient studenClient;
	
	@Override
	@Transactional(readOnly = true)
	public Course findCourseByStudentId(Long id) {
		return repository.findCourseByStudentId(id);
	}

	@Override
	public Iterable<Long> getTestIdWithStudentsAnswers(Long studentId) {		
		return client.getTestIdWithStudentsAnswers(studentId);
	}

	@Override
	public List<Student> getStudentsByCourse(List<Long> ids) {		
		return studenClient.getStudentsByCourse(ids);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteCourseStudentById(Long id) {		
		repository.deleteCourseStudentById(id);
	}

}
