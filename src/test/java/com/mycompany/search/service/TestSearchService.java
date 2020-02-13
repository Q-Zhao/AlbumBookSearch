package com.mycompany.search.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.search.bean.SearchRequest;
import com.mycompany.search.bean.SearchResponse;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSearchService {

	@Resource
	SearchService searchService;

    @Test
    public void testSearchService() {
    	SearchRequest request = new SearchRequest();
    	request.setKeyword("coca cola");
    	SearchResponse response = searchService.search(request);
    	
    	assertNotNull(response);
    	assertNotNull(response.getStatusCode());
    	
    	assertEquals(response.getStatusCode(), "SUCCESS");
    	
    	// TEST CASE TO BE IMPLEMENTED
    }
}
