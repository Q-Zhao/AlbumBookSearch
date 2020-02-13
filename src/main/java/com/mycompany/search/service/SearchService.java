package com.mycompany.search.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.mycompany.search.bean.SearchRequest;
import com.mycompany.search.bean.SearchResponse;
import com.mycompany.search.bean.SearchResult;
import com.mycompany.search.bean.SearchStatusDetails;
import com.mycompany.search.bean.StatusCode;
import com.mycompany.search.client.BaseTemplateClient;
import com.mycompany.search.client.ClientSearchResponse;
import com.mycompany.search.client.SearchTask;
import com.mycompany.search.resultprocessor.IResultSorter;

/**
 * Service layer
 * @author QQZhao
 *
 */
public class SearchService {

	private static final Logger logger = Logger.getLogger(SearchService.class);
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	/**
	 * Search Client Pool contains each client which make rest calls to the target domain
	 * The addition of this list is defined in applicationContext.xml
	 */
	private List<BaseTemplateClient> searchClientPool = new ArrayList<>();
	private IResultSorter resultSorter;

	/**
	 * Perform the search
	 * @param request
	 * @return SearchResponse
	 */
	public SearchResponse search(SearchRequest request) {
		
		SearchResponse response = new SearchResponse();
		
		// Validate input
		if (request == null || request.getKeyword() == null || request.getKeyword().trim().equals("")) {
			response.setStatusCode(StatusCode.INVALID_INPUT.name());
			return response;
		}
		
		// Start multi-threading rest-call tasks to target domains.
		// Create collections for search result
		List<Future<ClientSearchResponse>> futureResults = new ArrayList<>();
		List<SearchResult> results = new ArrayList<>();
		
		// Submit callable task to thread pool
		for (BaseTemplateClient searchClient : searchClientPool) {
			SearchTask searchTask = new SearchTask(searchClient, request.getKeyword());
			Future<ClientSearchResponse> searchResult = threadPool.submit(searchTask);
			futureResults.add(searchResult);
		}

		// Fetching real result from future object. 
		// This loop will be a time-consuming one since future.get() will be blocked if that task is not complete
		// Overall time should be slightly longer than the most time-consuming task
		
		for (Future<ClientSearchResponse> futureResult : futureResults) {
			try {
				ClientSearchResponse clientSearchResponse = futureResult.get();
				response.getStatusDetails().add(clientSearchResponse.getStatusDetails());
				results.addAll(clientSearchResponse.getResults());
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				SearchStatusDetails statusDetails = new SearchStatusDetails();
				statusDetails.setStatusCode(StatusCode.FAIL);
				response.getStatusDetails().add(statusDetails);
			} 
		}

		// Sort search result, sorter can be configured in spring xml file
		this.resultSorter.sort(results);
		
		response.getResults().addAll(results);
		StatusCode overallStatus = determineOveralStatusCode(response.getStatusDetails());
		response.setStatusCode(overallStatus.name());
		return response;
	}
	
	/**
	 * If status details of every call is success, the overall status code will be SUCCESS,
	 * If status details of every call is fail, the overall status code will be FAIL,
	 * Otherwise the overall status will be PARTIAL_FAIL, which mean the search result contains both success and failures.
	 * @param statusDetails
	 * @return a overall status code
	 */
	private StatusCode determineOveralStatusCode(List<SearchStatusDetails> statusDetails) {
		boolean isAllFail = true;
		boolean containsFailButNotAll = false;
		for(SearchStatusDetails detail : statusDetails) {
			StatusCode detailStatusCode = detail.getStatusCode();
			if (detailStatusCode == StatusCode.SUCCESS) {
				isAllFail = false;
			} else {
				containsFailButNotAll = true;
			}
		}
		
		if (isAllFail)
			return StatusCode.FAIL;
		if (containsFailButNotAll)
			return StatusCode.PARTIAL_SUCCESS;
		return StatusCode.SUCCESS;
	}

	
	/**********************Getter/Setter***********************/
	
	public void setResultSorter(IResultSorter resultSorter) {
		this.resultSorter = resultSorter;
	}
	
	public void setSearchClientPool(List<BaseTemplateClient> searchClientPool) {
		this.searchClientPool = searchClientPool;
	}

}