package com.springboot.app.courses.services;



import java.util.List;


import com.springboot.app.commons.services.ICommonService;
import com.springboot.app.courses.models.entity.Course;
import com.springboot.app.students.models.entity.Student;

public interface ICourseService extends ICommonService<Course> {
	
	public Course findCourseByStudentId(Long id);
	public Iterable<Long> getTestIdWithStudentsAnswers(Long studentId);
	public List<Student> getStudentsByCourse(List<Long> ids);
	public void deleteCourseStudentById(Long id);


}
