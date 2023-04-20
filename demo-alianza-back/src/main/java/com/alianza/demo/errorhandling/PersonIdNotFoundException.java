package com.alianza.demo.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonIdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 389949206272875015L;

	public PersonIdNotFoundException(String msg) {
		super(msg);
	}
	
	
}