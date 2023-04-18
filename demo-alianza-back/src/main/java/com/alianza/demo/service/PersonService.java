package com.alianza.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alianza.demo.entity.Person;
import com.alianza.demo.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> list(){
        return personRepository.findAll();
    }
    
    public List<Person> findBySharedKeyContaining(String sharedKey){
        return personRepository.findBySharedKeyContaining(sharedKey);
    }

    public Optional<Person> getOne(int id){
        return personRepository.findById(id);
    }

    public Optional<Person> getBySharedKey(String sharedKey){
        return personRepository.findBySharedKey(sharedKey);
    }

    public void  save(Person person){
    	personRepository.save(person);
    }

    public void delete(int id){
    	personRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return personRepository.existsById(id);
    }

    public boolean existsBySharedKey(String nombre){
        return personRepository.existsBySharedKey(nombre);
    }
}
