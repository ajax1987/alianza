package com.alianza.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alianza.demo.dto.Mensaje;
import com.alianza.demo.dto.PersonDto;
import com.alianza.demo.entity.Person;
import com.alianza.demo.errorhandling.PersonIdNotFoundException;
import com.alianza.demo.errorhandling.SharedKeyAlreadyExistException;
import com.alianza.demo.errorhandling.SharedKeyNotFoundException;
import com.alianza.demo.service.PersonService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/alianza")
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping("/list")
	public ResponseEntity<List<Person>> list() {
		List<Person> list = personService.list();
		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<Person>> getSearchBySharedKey(@PathVariable("keyword") String keyword) {

		if (!personService.existsBySharedKey(keyword)) {
			log.info("The SharedKey not exist!: {}", keyword);
			throw new SharedKeyNotFoundException("The SharedKey " + keyword + "  not exist!");
		}

		List<Person> list = personService.findBySharedKeyContaining(keyword);
		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody PersonDto personDto) {

		if (personService.existsBySharedKey(personDto.getSharedKey())) {
			log.info("The SharedKey is already taken!: {}", personDto.getSharedKey());
			throw new SharedKeyAlreadyExistException(
					"The SharedKey " + personDto.getSharedKey() + "  is already taken!");
		}

		personService.save(personDto.toPerson());
		return new ResponseEntity<>(new Mensaje("Person Create"), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {

		if (!personService.existsById(id)) {
			log.info("PersonID not exist!: {}", id);
			throw new PersonIdNotFoundException("PersonID " + id + "  not exist!");
		}

		personService.delete(id);
		return new ResponseEntity<Object>(new Mensaje("Person Delete"), HttpStatus.ACCEPTED);
	}

}
