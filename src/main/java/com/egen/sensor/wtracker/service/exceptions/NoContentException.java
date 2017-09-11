package com.egen.sensor.wtracker.service.exceptions;

public class NoContentException extends RuntimeException{
	private static final long serialVersionUID = -393779250788497783L;
	
	private String message;

	public NoContentException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
