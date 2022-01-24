package com.springboot.microservices.app.users.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.students.models.entity.Student;

public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
	
	@Query("Select s from Student s where upper(s.name) like upper(concat('%',?1,'%')) or upper(s.lastName) like upper(concat('%',?1,'%'))")
	public List<Student> findByNameOrLastName(String term);
	
	public Iterable<Student> findAllByOrderByIdAsc();
	public Page<Student> findAllByOrderByIdAsc(Pageable pageable);

}
