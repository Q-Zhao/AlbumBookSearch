package com.mycompany.search.api;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.search.bean.SearchRequest;
import com.mycompany.search.bean.SearchResponse;
import com.mycompany.search.service.SearchService;

/**
 * 
 * @author QQZhao
 *
 */
@Controller
public class SearchAPIImpl implements ISearchAPI, ApplicationContextAware {

	private ApplicationContext context;
	private SearchService service;
	
	private void init() {
		if (service == null) {
			service = (SearchService) context.getBean("searchService");
		}
	}

	@RequestMapping(value="/status", method=RequestMethod.GET)
	@ResponseBody
	@Override
	public SearchResponse getStatus() {
		SearchResponse response = new SearchResponse();
		response.setStatusCode("SEARCH_SERVICE_AVAILABLE");
		return response;
	}

	@RequestMapping(value="/search", method=RequestMethod.POST, consumes="application/json", produces = "application/json")
	@ResponseBody
	@Override
	public SearchResponse search(@RequestBody SearchRequest request) throws SearchServiceException {
		init();
		return service.search(request);
	}


	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
