package com.mycompany.search.client.itunes.bean;

import java.util.List;

public class ITunesFeedback {

	private String resultCount;
	private List<ITunesResult> results;
	
	public String getResultCount() {
		return resultCount;
	}
	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}
	public List<ITunesResult> getResults() {
		return results;
	}
	public void setResults(List<ITunesResult> results) {
		this.results = results;
	}
	
	
	
}
