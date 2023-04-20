package com.alianza.demo.dto;

import java.time.LocalDateTime;

import com.alianza.demo.entity.Person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PersonDto {

    @NotBlank(message="sharedKey must not be blank")
    @Size(min=5, message="sharedKey must be at least 5 characters long")
    private String sharedKey;
    
    @NotBlank(message="bussinesId must not be blank")
    @Size(min=5, message="bussinesId must be at least 5 characters long")
    private String bussinesId;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;
    

    public Person toPerson() {
    	
    	Person person = new Person();

        person.setSharedKey(sharedKey);
        person.setBussinesId(bussinesId);
        person.setEmail(email);
        person.setMobileNumber(mobileNumber);
        person.setCreatedAt(LocalDateTime.now());
    	
        /*return new Person()
               .setSharedKey(sharedKey)
               .setBussinesId(bussinesId)
               .setEmail(email)
               .setMobileNumber(mobileNumber);*/
        return person;
      }
    
    
   public Person toPerson(Person person) {
    	
        person.setSharedKey(sharedKey);
        person.setBussinesId(bussinesId);
        person.setEmail(email);
        person.setMobileNumber(mobileNumber);
        person.setCreatedAt(LocalDateTime.now());
    	
        /*return new Person()
               .setSharedKey(sharedKey)
               .setBussinesId(bussinesId)
               .setEmail(email)
               .setMobileNumber(mobileNumber);*/
        return person;
      }

    
}
