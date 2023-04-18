package com.alianza.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.alianza.demo.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/alianza")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/list")
    public ResponseEntity<List<Person>> list(){
        List<Person> list = personService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Person>> getSearchBySharedKey(@PathVariable("keyword") String keyword){
        if(!personService.existsBySharedKey(keyword))
            return new ResponseEntity(new Mensaje("Person not exist"), HttpStatus.NOT_FOUND);        
        List<Person> list = personService.findBySharedKeyContaining(keyword);
        return new ResponseEntity(list, HttpStatus.OK);
        
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonDto personDto){
        if(StringUtils.isBlank(personDto.getSharedKey()))
            return new ResponseEntity(new Mensaje("el SharedKey es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(personDto.getBussinesId()))
            return new ResponseEntity(new Mensaje("el BussinesId es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(personDto.getEmail()))
            return new ResponseEntity(new Mensaje("el Email es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(personDto.getMobileNumber()))
            return new ResponseEntity(new Mensaje("el MobileNumber es obligatorio"), HttpStatus.BAD_REQUEST);
        if(personService.existsBySharedKey(personDto.getSharedKey()))
            return new ResponseEntity(new Mensaje("ese SharedKey ya existe"), HttpStatus.BAD_REQUEST);
        Person person = new Person();
        
        person.setSharedKey(personDto.getSharedKey());
        person.setBussinesId(personDto.getBussinesId());
        person.setEmail(personDto.getEmail());
        person.setMobileNumber(personDto.getMobileNumber());
        person.setCreatedAt(LocalDateTime.now());
        personService.save(person);
        return new ResponseEntity(new Mensaje("person create"), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!personService.existsById(id))
            return new ResponseEntity(new Mensaje("No exist"), HttpStatus.NOT_FOUND);
        personService.delete(id);
        return new ResponseEntity(new Mensaje("person delete"), HttpStatus.OK);
    }


}
