package com.xebia.articles.exception.error.response;

import org.springframework.http.HttpStatus;

import com.xebia.articles.api.response.APIResponseCodes;

/**
 * Represents interface for API error response. Each API error class should implement this interface
 *
 * @author dhekumar2
 */

public interface APIErrorResponse {

    APIResponseCodes ApiError();
    HttpStatus httpStatus();
}
