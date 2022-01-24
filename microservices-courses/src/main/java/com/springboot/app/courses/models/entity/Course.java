package com.springboot.app.courses.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.app.students.models.entity.Student;
import com.springboot.commons.tests.models.entity.Test;

@Entity
@Table(name="courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"course"}, allowSetters = true)
	@OneToMany(fetch= FetchType.LAZY, mappedBy="course", cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<CourseStudent> courseStudent;
	
	//@OneToMany(fetch= FetchType.LAZY)
	@Transient
	private List<Student> students;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Test> tests;
	
	@PrePersist
	public void prePresist() {
		this.createAt = new Date();
	}
	
	
	public Course() {
		this.students = new ArrayList<>();
		this.tests = new ArrayList<>();
		this.courseStudent = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}	
	
	public List<Test> getTests() {
		return tests;
	}


	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
	
	public List<CourseStudent> getCourseStudent() {
		return courseStudent;
	}


	public void setCourseStudent(List<CourseStudent> courseStudent) {
		this.courseStudent = courseStudent;
	}
	
		
	// PERSONALIZE METHODS	
	public void addStudents(Student student) {
		this.students.add(student);
	}	
	
	public void removeStudents(Student student) {
		this.students.remove(student);
	}
	
	public void addTest(Test test) {
		this.tests.add(test);
	}	
	
	public void removeTest(Test test) {
		this.tests.remove(test);
	}	
	
	public void addCourseStudent(CourseStudent courseStudent) {
		this.courseStudent.add(courseStudent);
	}	
	
	public void removeCourseStudent(CourseStudent courseStudent) {
		this.courseStudent.remove(courseStudent);
	}
	
}
