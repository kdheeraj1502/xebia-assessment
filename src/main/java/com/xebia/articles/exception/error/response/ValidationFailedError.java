package com.xebia.articles.exception.error.response;

import org.springframework.http.HttpStatus;

import com.xebia.articles.api.response.APIResponseCodes;
import com.xebia.articles.exception.ApplicationException;

/**
 * Represents MissingRequiredFields type of  error class.
 *
 * extends : ApplicationException
 * implements APIErrorResponse
 *
 * response code mapped from enum  class where all API response defined
 *
 * @author dhekumar2
 */
public class ValidationFailedError extends ApplicationException implements APIErrorResponse {


	@Override
	public APIResponseCodes ApiError() {
		return APIResponseCodes.VALIDATION_FAILED;
	}


	@Override
	public HttpStatus httpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	public ValidationFailedError(){
		super();
	}

	public ValidationFailedError(String message){
		super(message);
	}
}

