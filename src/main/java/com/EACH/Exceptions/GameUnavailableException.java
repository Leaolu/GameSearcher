package com.EACH.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameUnavailableException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public GameUnavailableException() {
		super("Game not available!");
	}
	public GameUnavailableException(String msg) {
		super("msg");
	}

}