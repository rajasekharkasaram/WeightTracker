package com.egen.sensor.wtracker.service.exceptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Configurable
@RestControllerAdvice
public class WeightTrackerServiceExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<String> handleNoContent(NoContentException noContentEx) {
		String message = "";
			if (noContentEx.getMessage() != null) {
				message = noContentEx.getMessage();
			}
			
		return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);
	}	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
		ErrorResponse responseError = null;
		HttpStatus status = null;

		if (ex instanceof ApplicationException) {
			ApplicationException ae = (ApplicationException) ex;
			responseError = new ErrorResponse();
			if (ae != null && ae.getErrorMessage() != null) {
				responseError.setErrorCode(ae.getErrorCode());
				responseError.setErrorMessage(ae.getErrorMessage());
			}
			responseError.setStatus(HttpStatus.BAD_REQUEST);
			responseError.setTimestamp(Calendar.getInstance().getTimeInMillis());
			status = responseError.getStatus();
	    } else {
            responseError = new ErrorResponse(ex);
            status = responseError.getStatus();
        }

		Object response = null;
		if (responseError != null) {
			response = responseError;
			System.out.println("[" + req.getMethod() + "::" + req.getContextPath() + "]   responseError: HttpStatus["
					+ responseError.getStatus() + "]  code[" + responseError.getErrorCode() + "]  message[" + responseError.getErrorMessage() + "]   timestamp["
					+ responseError.getTimestamp() + "]");
			System.out.println(req.getParameterNames() + "  xxxx  " + req.getParameterMap());
		} 
		System.out.println("handleControllerException");
		return new ResponseEntity<Object>(response, status);
	}	
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String notAccepted = "UnAccepted content type : " + ex.getSupportedMediaTypes();
        String accepted = "Accepted content types : " + MediaType.toString(headers.getAccept());
        ErrorResponse responseError = new ErrorResponse(notAccepted, accepted);
        return new ResponseEntity<Object>(responseError, headers, status);
	} 
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String unsupported = "Unsupported content type : " + ex.getContentType();
        String supported = "Supported content types : " + MediaType.toString(ex.getSupportedMediaTypes());
        ErrorResponse responseError = new ErrorResponse(unsupported, supported);
        return new ResponseEntity<Object>(responseError, headers, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
		String error;
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
			errors.add(error);
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
			errors.add(error);
		}
		ErrorResponse errorMessage = new ErrorResponse(errors);
		return new ResponseEntity<Object>(errorMessage, headers, status);
	}
	
}