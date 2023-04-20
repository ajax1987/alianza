package com.alianza.demo.errorhandling;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionResponse {
	
	private Date timestamp;
    private String path;
	private int status;
    private HttpStatus error;
    private String message;
    private List<Object> errors;
    
    
}