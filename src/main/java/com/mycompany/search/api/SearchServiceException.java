package com.mycompany.search.api;

/**
 * 
 * @author QQZhao
 *
 */
public class SearchServiceException extends Exception {

	private static final long serialVersionUID = 5311532741860313854L;

	public SearchServiceException(String errorMessage) {
        super(errorMessage);
    }
}
