package com.mycompany.search.client;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.search.bean.SearchResult;
import com.mycompany.search.bean.SearchStatusDetails;

/**
 * The search response from each domain client
 * @author QQZhao
 *
 */

public class ClientSearchResponse {

	private SearchStatusDetails statusDetails;
	private List<SearchResult> results;
	public SearchStatusDetails getStatusDetails() {
		return statusDetails;
	}
	public void setStatusDetails(SearchStatusDetails statusDetails) {
		this.statusDetails = statusDetails;
	}
	public List<SearchResult> getResults() {
		if (results == null) {
			results = new ArrayList<>();
		}
		return results;
	}
	
}
