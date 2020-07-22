package com.xebia.articles.api.response;

/**
 * 
 * @author dhekumar2
 *
 */
public enum APIResponseCodes {

	SUCCESS(200, "OK"), 
	CREATED(201, "CREATED"), 
	VALIDATION_FAILED(400, "VALIDATION FAILED"),
	FORBIDDEN_ERROR(404, "FORBIDDEN.THIS METHOD IS NOT ALLOWED"), 
	NOT_FOUND(300, "RESOURCE NOT FOUND"),
	INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

	private final int apiResponseCode;
	private final String apiResponseCodeMessage;

	public int getApiResponseCode() {
		return apiResponseCode;

	}

	public String getApiResponseMessage() {
		return apiResponseCodeMessage;
	}

	APIResponseCodes(int apiResponseCode, String apiResponseCodeMessage) {
		this.apiResponseCode = apiResponseCode;
		this.apiResponseCodeMessage = apiResponseCodeMessage;
	}
}
