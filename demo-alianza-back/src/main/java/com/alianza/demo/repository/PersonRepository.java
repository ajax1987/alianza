package com.alianza.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alianza.demo.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    
	Optional<Person> findBySharedKey(String sharedKey);
	
    boolean existsBySharedKey(String nombre);
    
    List<Person> findBySharedKeyContaining(String sharedKey);
    
    
}
