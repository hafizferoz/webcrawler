package com.mavericks.webcrawler.error;

/**
 * @author Feroz Hafiz
 *
 */
public class CrawlerException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3385877135228541335L;

	public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
