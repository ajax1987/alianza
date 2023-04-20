package com.alianza.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.alianza.demo.entity.Person;
import com.alianza.demo.repository.PersonRepository;
import com.alianza.demo.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerTest {

	@Mock
	private EntityManager entityManager;

	@Autowired
	PersonService personService;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	private JdbcTemplate jdbc;
	
	@Mock
	Person person;

	@Value("${sql.script.create.person}")
	private String sqlAddPerson;

	@Value("${sql.script.delete.persons}")
	private String sqlDeletePerson;

	@Value("${sql.script.select.all.persons}")
	private String sqlSelectAllPerson;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeEach
	public void setupDatabase() {
		jdbc.execute(sqlAddPerson);
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	@Test
	@Order(1)
	public void delete() throws Exception 
	{		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/alianza/delete/{id}", 1))
		             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		            .andDo(print())
	                .andExpect(status().isAccepted())
	                .andExpect(jsonPath("$.mensaje").value("Person Delete"));;
	}
	
	@Test
	public void list() throws Exception {
		
		Person person = new Person();
		person.setSharedKey("jgutierrez1");
		person.setBussinesId("juliana gutierrez");
		person.setEmail("jgutierrez@gmail.com");
		person.setMobileNumber("3219876543");
		person.setCreatedAt(LocalDateTime.now());
		
		personService.save(person);
		
		//entityManager.persist(person);
		//entityManager.flush();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alianza/list"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andDo(print())
					.andExpect(jsonPath("$", hasSize(2)));
		
		afterEach();
	}
	
	@Test
	public void getSearchBySharedKey() throws Exception 
	{			
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/alianza/search/{keyword}","jgutierrez"))
		            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
		            .andExpect(jsonPath("$", hasSize(1)));	
	}
	
	
	
	@Test
	public void create() throws Exception {
		
		Person person = new Person();
		person.setSharedKey("jgutierrez1");
		person.setBussinesId("juliana gutierrez");
		person.setEmail("jgutierrez@gmail.com");
		person.setMobileNumber("3219876543");
		
		ObjectMapper objectMapper = new ObjectMapper();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/alianza/create")
				    .contentType(MediaType.APPLICATION_JSON)
				    .accept(MediaType.APPLICATION_JSON)
				    .content(objectMapper.writeValueAsString(person)))
				    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				    .andExpect(status().isCreated())
				    .andExpect(jsonPath("$.mensaje").value("Person Create"));

	}
	
	@AfterEach
	public void afterEach() {
	  jdbc.execute(sqlDeletePerson);
	}


}
