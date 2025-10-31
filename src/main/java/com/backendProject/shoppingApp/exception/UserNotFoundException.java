package com.backendProject.shoppingApp.exception;

public class UserNotFoundException  extends RuntimeException{
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
