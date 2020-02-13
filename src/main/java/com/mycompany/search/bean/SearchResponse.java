package com.mycompany.search.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

	private String statusCode;
	private List<SearchStatusDetails> statusDetails;
	private List<SearchResult> results;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<SearchResult> getResults() {
		if (this.results == null) {
            this.results = new ArrayList<SearchResult>();
        }
        return this.results;
	}


	public List<SearchStatusDetails> getStatusDetails() {
		if (this.statusDetails == null) {
			this.statusDetails = new ArrayList<SearchStatusDetails>();
		}
		return this.statusDetails;
	}

	
}
