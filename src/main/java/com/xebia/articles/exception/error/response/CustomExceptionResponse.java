package com.xebia.articles.exception.error.response;

import java.util.Date;

/**
 * Represents API customized error response class. Each API error response will
 * in below format
 *
 * { "statusCode" : 503, "reasonPhrase" : "Service Unavailable", "date" :
 * "2020-05-10T08:28:52.106+0000", "error_code" : 50003, "error_message" :
 * "Service Unavailable" }
 *
 * @author dhekumar2
 */

public class CustomExceptionResponse {
	private int statusCode;
	private String reasonPhrase;
	private Date date;
	private int error_code;
	private String error_message;

	public CustomExceptionResponse() {
		super();

	}

	public CustomExceptionResponse(int statusCode, String reasonPhrase, Date date, int error_code,
			String error_message) {
		super();
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
		this.date = date;
		this.error_code = error_code;
		this.error_message = error_message;

	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
}