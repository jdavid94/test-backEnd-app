package com.springboot.app.courses.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.commons.controllers.CommonController;
import com.springboot.app.courses.models.entity.Course;
import com.springboot.app.courses.models.entity.CourseStudent;
import com.springboot.app.courses.services.ICourseService;
import com.springboot.app.students.models.entity.Student;
import com.springboot.commons.tests.models.entity.Test;


@RestController
public class CourseController extends CommonController<Course, ICourseService> {
	
	@Value("${config.balancer.test}")
	private String testBalancer;
	
	@DeleteMapping("/delete-student/{id}")
	public ResponseEntity<?> deleteCourseStudentById(@PathVariable Long id) {
		service.deleteCourseStudentById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Override
	public ResponseEntity<?> list() {
		List<Course> courses = ((List<Course>) service.findAll()).stream().map(c -> {
			c.getCourseStudent().forEach(cs -> {
				Student student = new Student();
				student.setId(cs.getStudentId());
				c.addStudents(student);
			});
			return c;
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(courses);		
	}
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> viewById(@PathVariable Long id) {
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course course = o.get();
		if (course.getCourseStudent().isEmpty() == false) {
			List<Long> ids = course.getCourseStudent().stream().map(cs -> {
				return cs.getStudentId();
			}).collect(Collectors.toList());
			List<Student> students = service.getStudentsByCourse(ids);
			course.setStudents(students);
		}
		return ResponseEntity.ok().body(course);		
	}
	
	@GetMapping("/page")
	@Override
	public ResponseEntity<?> list(Pageable pageable) {
		Page<Course> courses = service.findAll(pageable).map(course -> {
			course.getCourseStudent().forEach(cs -> {
				Student student = new Student();
				student.setId(cs.getStudentId());
				course.addStudents(student);
			});
			return course;
		});
		return ResponseEntity.ok().body(courses);		
	}
	
	@GetMapping("/test-balancer")
	public ResponseEntity<?> testBalancer() {	
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balancer", testBalancer);
		response.put("courses", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Validated @RequestBody Course course, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return this.validated(result);
		}
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course courseDB = o.get();
		courseDB.setName(course.getName());	
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDB));		
	}	
	
	@PutMapping("/{id}/add-students")
	public ResponseEntity<?> addStudents (@RequestBody List<Student> students, @PathVariable Long id) {
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course courseDB = o.get();
		students.forEach(a -> {
			CourseStudent courseStudent = new CourseStudent();
			courseStudent.setStudentId(a.getId());
			courseStudent.setCourse(courseDB);
			courseDB.addCourseStudent(courseStudent);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDB));	
	}
	
	@PutMapping("/{id}/remove-student")
	public ResponseEntity<?> removeStudent (@RequestBody Student student, @PathVariable Long id) {
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course courseDB = o.get();
		CourseStudent courseStudent = new CourseStudent();
		courseStudent.setStudentId(student.getId());
		courseDB.removeCourseStudent(courseStudent);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDB));	
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<?> searchByStudentId(@PathVariable Long id) {
		Course course = service.findCourseByStudentId(id);
		if (course != null) {
			List<Long> testsIds = (List<Long>) service.getTestIdWithStudentsAnswers(id);
			if (testsIds != null && testsIds.size() > 0) {
				List<Test> tests = course.getTests().stream().map(test -> {
					if (testsIds.contains(test.getId())) {
						test.setAnswered(true);
					}
					return test;
				}).collect(Collectors.toList());
				course.setTests(tests);				
			}
		}		
		return ResponseEntity.ok(course);	
	}
	
	@PutMapping("/{id}/add-tests")
	public ResponseEntity<?> addTests (@RequestBody List<Test> tests, @PathVariable Long id) {
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course courseDB = o.get();
		tests.forEach(t -> {
			courseDB.addTest(t);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDB));	
	}
	
	@PutMapping("/{id}/remove-test")
	public ResponseEntity<?> removeTests (@RequestBody Test test, @PathVariable Long id) {
		Optional<Course> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Course courseDB = o.get();
		courseDB.removeTest(test);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDB));	
	}

}
