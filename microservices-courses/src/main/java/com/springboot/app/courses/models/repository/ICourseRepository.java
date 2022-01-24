package com.springboot.app.courses.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.courses.models.entity.Course;

public interface ICourseRepository extends PagingAndSortingRepository<Course, Long> {
	
	@Query("Select c from Course c join fetch c.courseStudent s where s.studentId=?1")
	public Course findCourseByStudentId(Long id);
	
	@Modifying
	@Query("Delete from CourseStudent cs where cs.studentId=?1")
	public void deleteCourseStudentById(Long id);

}
