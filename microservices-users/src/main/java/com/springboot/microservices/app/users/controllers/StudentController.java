package com.springboot.microservices.app.users.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.commons.controllers.CommonController;
import com.springboot.app.students.models.entity.Student;
import com.springboot.microservices.app.users.services.IStudentService;

@RestController
public class StudentController extends CommonController<Student, IStudentService> {
	
	@GetMapping("/students-by-course")
	public ResponseEntity<?> getStudentsByCourse(@RequestParam List<Long> ids) {
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> viewPhoto (@PathVariable Long id) {
		Optional<Student> o = service.findById(id);
		if (o.isEmpty() || o.get().getPhoto() == null) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Resource image = new ByteArrayResource(o.get().getPhoto());
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
	}
			
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Validated @RequestBody Student student, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return this.validated(result);
		}
		Optional<Student> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Student studentDB = o.get();
		studentDB.setName(student.getName());
		studentDB.setLastName(student.getLastName());
		studentDB.setEmail(student.getEmail());		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studentDB));		
	}	
	
	@GetMapping("/filter/{term}")
	public ResponseEntity<?> filter(@PathVariable String term) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findByNameOrLastName(term));	
	}

	@PostMapping("/create-photo")
	public ResponseEntity<?> createPhoto(@Validated Student student, BindingResult result, @RequestParam MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			student.setPhoto(file.getBytes());
		}
		return super.create(student, result);
	}
	
	@PutMapping("/edit-photo/{id}")
	public ResponseEntity<?> updatePhoto(@Validated Student student, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile file) throws IOException{
		if (result.hasErrors()) {
			return this.validated(result);
		}
		Optional<Student> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		Student studentDB = o.get();
		studentDB.setName(student.getName());
		studentDB.setLastName(student.getLastName());
		studentDB.setEmail(student.getEmail());	
		if (!file.isEmpty()) {
			studentDB.setPhoto(file.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studentDB));		
	}	
	
	
}
