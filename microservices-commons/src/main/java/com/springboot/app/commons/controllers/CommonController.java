package com.springboot.app.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.springboot.app.commons.services.ICommonService;

//@CrossOrigin({"http://localhost:4200"})
public class CommonController<E, S extends ICommonService<E>> {

	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> list() {
		return ResponseEntity.ok().body(service.findAll());		
	}
	
	@GetMapping("/page")
	public ResponseEntity<?> list(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAll(pageable));		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> viewById(@PathVariable Long id) {
		Optional<E> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build(); // BUILD BODY WITHOUT CONTENT
		}
		return ResponseEntity.ok(o.get());		
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Validated @RequestBody E entity, BindingResult result){
		if (result.hasErrors()) {
			return this.validated(result);
		}
		E entitytDB = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entitytDB);		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();		
	}
	
	protected ResponseEntity<?> validated (BindingResult result) {
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());			
		});
		return ResponseEntity.badRequest().body(errors);
	}
	
	
	
}
