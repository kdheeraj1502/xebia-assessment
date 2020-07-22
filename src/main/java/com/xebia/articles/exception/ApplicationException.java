package com.xebia.articles.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	/**
	 * 
	 * @author dhekumar2
	 *
	 */
public class ApplicationException extends ArticlesException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationException.class);

	private String[] params;

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, String[] params) {
		super(message);
		this.params = params;
	}

	public ApplicationException(String message, String[] params, Throwable cause) {
		super(message, cause);
		this.params = params;
	}
}
