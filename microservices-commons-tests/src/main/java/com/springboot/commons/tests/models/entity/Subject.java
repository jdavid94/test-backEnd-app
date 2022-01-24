package com.springboot.commons.tests.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subjects")
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@JsonIgnoreProperties(value = {"sons","handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Subject father;
	
	@JsonIgnoreProperties(value = {"father","handler","hibernateLazyInitializer"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy="father", cascade = CascadeType.ALL)
	private List<Subject> sons;
	
		
	public Subject() {
		this.sons = new ArrayList<>();
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
	public Subject getFather() {
		return father;
	}
	public void setFather(Subject father) {
		this.father = father;
	}
	public List<Subject> getSons() {
		return sons;
	}
	public void setSons(List<Subject> sons) {
		this.sons = sons;
	}
	
}
