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
public class ServerFailedError extends ApplicationException implements APIErrorResponse {


	@Override
	public APIResponseCodes ApiError() {
		return APIResponseCodes.INTERNAL_SERVER_ERROR;
	}


	@Override
	public HttpStatus httpStatus() {
		return HttpStatus.SERVICE_UNAVAILABLE;
	}

	public ServerFailedError(){
		super();
	}

	public ServerFailedError(String message){
		super(message);
	}
}

