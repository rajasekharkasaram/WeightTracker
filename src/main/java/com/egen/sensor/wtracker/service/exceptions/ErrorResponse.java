package com.egen.sensor.wtracker.service.exceptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorResponse {
	private String errorMessage;

	private String errorCode;

	private long timestamp = (new Date()).getTime();

	private String type;

	private String id;

	private HttpStatus status;

	private HttpHeaders headers = new HttpHeaders();

	private Throwable cause;

	private List<String> errors;

	public ErrorResponse(List<String> errors) {
		this();
		this.errors = errors;
	}

	public ErrorResponse(String... errors) {
		this(Arrays.asList(errors));
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ErrorResponse() {
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ErrorResponse(HttpStatus status) {
		this.status = status;
	}

	public ErrorResponse(String message) {
		this(Collections.singletonList(message));
		this.setErrorMessage(message);
	}

	public ErrorResponse(String message, HttpStatus status) {
		this.setErrorMessage(message);
		this.setStatus(status);
	}

	public ErrorResponse(Throwable thrown) {
		this();
		this.setErrorMessage(thrown.getMessage());
		this.setType(thrown.getClass().getName());
		// this.parseException(thrown);
		this.cause = thrown;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	@JsonIgnore
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	@JsonIgnore
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	@JsonIgnore
	public void addHeaders(HttpHeaders headers) {
		if (headers != null) {
			this.headers.putAll(headers);
		}
	}

	@Override
	public String toString() {
		String ls = System.getProperty("line.separator");
		StringBuilder str = new StringBuilder();
		str.append("message=" + this.getErrorMessage() + ls);
		if (StringUtils.isNotBlank(this.errorCode)) {
			str.append(ls + "code=" + this.errorCode);
		}
		if (StringUtils.isNotBlank(this.type)) {
			str.append(ls + "type=" + this.type);
		}
		if (StringUtils.isNotBlank(this.id)) {
			str.append(ls + "id=" + this.id);
		}
		if (this.cause != null) {
			str.append(ls + "stacktrace=" + ExceptionUtils.getStackTrace(this.cause));
		}
		return str.toString();
	}
}