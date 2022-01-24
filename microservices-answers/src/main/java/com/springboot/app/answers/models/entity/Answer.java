package com.springboot.app.answers.models.entity;


import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.springboot.app.students.models.entity.Student;
import com.springboot.commons.tests.models.entity.Question;


@Document(collection="answers")
public class Answer {
	
	@Id
	private String id;
	
	private String text;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@Transient
	private Student student;
	

	private Long studentId;
	
	//@Transient
	private Question question;
	
	private Long answerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}	
	
	
	

}
