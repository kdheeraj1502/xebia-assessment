package com.xebia.articles.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author dhekumar2
 *
 */

public class ArticlesException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5897132406605126262L;

	/**
     * The log.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesException.class);

    private String[] params;

    public ArticlesException() {
    }

    public ArticlesException(String message) {
        super(message);
    }

    public ArticlesException(String message, Throwable cause) {
        super(message, cause);
    }


    public ArticlesException(Throwable cause) {
        super(cause);
    }


    public ArticlesException(String message, String[] params) {
        super(message);
        this.params = params;
    }


    public ArticlesException(String message, String[] params, Throwable cause) {
        super(message, cause);
        this.params = params;
    }
}
