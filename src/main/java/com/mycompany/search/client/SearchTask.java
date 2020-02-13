package com.mycompany.search.client;

import java.util.concurrent.Callable;

/**
 * This callable object will be executed by executor to start a new thread for each call
 * @author QQZhao
 *
 */
public class SearchTask implements Callable<ClientSearchResponse> {

	BaseTemplateClient searchClient;
	String keyword;
	
	public SearchTask(BaseTemplateClient searchClient, String keyword) {
		this.searchClient = searchClient;
		this.keyword = keyword;
	}
	
	@Override
	public ClientSearchResponse call() throws Exception {
		return this.searchClient.doSearchInTarget(this.keyword);
	}

}
