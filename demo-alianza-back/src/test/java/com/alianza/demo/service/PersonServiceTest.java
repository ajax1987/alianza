package com.alianza.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.alianza.demo.entity.Person;
import com.alianza.demo.repository.PersonRepository;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	PersonService personService;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	private JdbcTemplate jdbc;

	@Value("${sql.script.create.person}")
	private String sqlAddPerson;

	@Value("${sql.script.delete.persons}")
	private String sqlDeletePerson;

	@Value("${sql.script.select.all.persons}")
	private String sqlSelectAllPerson;

	@BeforeEach
	public void setupDatabase() {
		jdbc.execute(sqlAddPerson);
	}

	@Test
	public void save() throws Exception {
		Person person = new Person();
		person.setSharedKey("jgutierrez1");
		person.setBussinesId("juliana gutierrez");
		person.setEmail("jgutierrez@gmail.com");
		person.setMobileNumber("3219876543");
		person.setCreatedAt(LocalDateTime.now());
		personService.save(person);
		Optional<Person> hola = personService.getBySharedKey("jgutierrez1");
		assertEquals("jgutierrez1", hola.get().getSharedKey());

	}

	@Test
	public void delete() throws Exception {
		Optional<Person> delete = personRepository.findById(1);
		assertTrue(delete.isPresent());
		personService.delete(1);
		delete = personRepository.findById(1);
		assertFalse(delete.isPresent());
	}

	@Test
	public void existsById() throws Exception {
		boolean existe = personService.existsById(1);
		assertFalse(existe);
	}

	@Test
	public void getBySharedKey() throws Exception {
		Optional<Person> sharedKey = personRepository.findBySharedKey("jgutierrez");
		assertNotNull(sharedKey.get().getSharedKey());
	}
	
	
	@Test
	public void existsBySharedKey() throws Exception {
		boolean sharedKey = personService.existsBySharedKey("jgutierrez");
		assertTrue(sharedKey);
	}


	@Test
	public void getOne() throws Exception {
		Optional<Person> person = personService.getOne(1);
		assertNotNull(person);
	}
	
	@Test
	public void list() throws Exception {
		List<Person> list = personService.list();
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void findBySharedKeyContaining() throws Exception {
		List<Person> list = personService.findBySharedKeyContaining("jgutierrez");
		assertFalse(list.isEmpty());
	}

	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeletePerson);
	}
}
