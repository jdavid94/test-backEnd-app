package com.springboot.commons.tests.models.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="tests")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 4 , max = 30)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"test"}, allowSetters = true)
	@OneToMany(mappedBy = "test" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // RELATION BIDIRECTIONAL AND REMOVE IN CASCADE
	private List<Question> questions;
	
	@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Subject subjectFather;	
	
	@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Subject subjectSon;	
	
	@Transient // Is not mapping to DataBase
	private boolean answered;
	
	public Test() {
		this.questions = new ArrayList<>();
	}

	@PrePersist
	public void prePresist() {
		this.createAt = new Date();
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions.clear();
		questions.forEach(this::addQuestions);
		//questions.forEach(q -> {
		//this.addQuestions(q);
		//});;
	}
		
	
	public Subject getSubjectFather() {
		return subjectFather;
	}

	public void setSubjectFather(Subject subjectFather) {
		this.subjectFather = subjectFather;
	}

	public Subject getSubjectSon() {
		return subjectSon;
	}

	public void setSubjectSon(Subject subjectSon) {
		this.subjectSon = subjectSon;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	// PERSONALIZE METHODS
	public void addQuestions(Question question) {
		this.questions.add(question);
		question.setTest(this);
	}
	
	public void removeQuestions(Question question) {
		this.questions.remove(question);
		question.setTest(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Test)) {
			return false;
		}
		Test t = (Test) obj;
		return this.id != null && this.id.equals(t.getId());
	}		

}
