package com.mycompany.search.api;

import com.mycompany.search.bean.SearchRequest;
import com.mycompany.search.bean.SearchResponse;
/**
 * 
 * @author QQZhao
 *
 */
public interface ISearchAPI {

	/**
	 * A get request to check if the service is available
	 * @return
	 */
	public SearchResponse getStatus();
	
	/**
	 * With a keyword in request object, this function will call multiple domain servers to fetch corresponding related information.
	 * @param request
	 * @return
	 * @throws SearchServiceException
	 */
	public SearchResponse search(SearchRequest request) throws SearchServiceException; 
	
}
