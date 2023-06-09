package com.alianza.demo.errorhandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SharedKeyAlreadyExistException.class)
	protected ResponseEntity<ExceptionResponse> handleSharedKeyAlreadyExistException(
			SharedKeyAlreadyExistException ex, 
			WebRequest request) {
				
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		    	
		exceptionResponse.setTimestamp(new Date());
		exceptionResponse.setStatus(HttpStatus.CONFLICT.value());
		exceptionResponse.setPath(request.getDescription(false));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setError(HttpStatus.CONFLICT);
    	
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(SharedKeyNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleSharedKeyNotFoundException(
			SharedKeyNotFoundException ex,
			WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
    	
		exceptionResponse.setTimestamp(new Date());
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
		exceptionResponse.setPath(request.getDescription(false));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setError(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(PersonIdNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlePersonIdNotFoundException(
			PersonIdNotFoundException ex,
			WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
    	
		exceptionResponse.setTimestamp(new Date());
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
		exceptionResponse.setPath(request.getDescription(false));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setError(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, 
    		HttpHeaders headers, 
    		HttpStatusCode status, 
    		WebRequest request) {
           
      List<Object> errors = ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList());

      ExceptionResponse exceptionResponse = new ExceptionResponse();
  	
	  exceptionResponse.setTimestamp(new Date());
	  exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	  exceptionResponse.setPath(request.getDescription(false));
      exceptionResponse.setMessage("Validation failed for object='"+ex.getObjectName()+"'. Error count: "+ ex.getErrorCount());
      exceptionResponse.setError(HttpStatus.BAD_REQUEST);
      exceptionResponse.setErrors(errors);
      
      return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    
    }
    
    
    
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
      Map<String, String> body = new HashMap<>();

      body.put("message", ex.getMessage());

      return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
      List<String> errors = new ArrayList<>();

      ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

      Map<String, List<String>> result = new HashMap<>();
      result.put("errors", errors);

      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    
    
    
    
    
    
    

}