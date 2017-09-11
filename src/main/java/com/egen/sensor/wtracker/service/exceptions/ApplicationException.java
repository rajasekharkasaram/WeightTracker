package com.egen.sensor.wtracker.service.exceptions;

public class ApplicationException extends RuntimeException{
	
	private static final long serialVersionUID = -2142879932540384175L;
	
	private String errorCode;
	private String errorMessage;

	public ApplicationException(String errorCode, String errorMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}