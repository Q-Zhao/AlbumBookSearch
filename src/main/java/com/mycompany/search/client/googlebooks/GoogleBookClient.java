package com.mycompany.search.client.googlebooks;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mycompany.search.bean.SearchResult;
import com.mycompany.search.bean.SearchStatusDetails;
import com.mycompany.search.bean.StatusCode;
import com.mycompany.search.client.BaseTemplateClient;
import com.mycompany.search.client.ClientSearchResponse;
import com.mycompany.search.client.googlebooks.bean.GoogleBookFeedback;
import com.mycompany.search.client.googlebooks.bean.GoogleBookResult;
import com.mycompany.search.client.googlebooks.bean.GoogleBookVolumeInfo;
import com.mycompany.search.config.CommonConstants;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Object to make rest calls to search the keyword in GoogleBook
 * @author QQZhao
 *
 */
public class GoogleBookClient extends BaseTemplateClient {

	private static final Logger logger = Logger.getLogger(GoogleBookClient.class);
	
	@Override
	public ClientSearchResponse doSearchInTarget(String keyword) {
		
		ClientSearchResponse clientSearchResponse = new ClientSearchResponse();
		SearchStatusDetails statusDetails = new SearchStatusDetails();
		statusDetails.setSource("Google Book");
		clientSearchResponse.setStatusDetails(statusDetails);
		
		WebResource webResource = client.resource(config.getGoogleBookSearchURL())
				   .queryParam(CommonConstants.QUERY, keyword)
				   .queryParam(CommonConstants.MAX_RESULTS, String.valueOf(config.getGoogleSearchLimit()));

		logger.info("Start sending google book search request");
		ClientResponse response = null;
		long startTime = System.currentTimeMillis();
		try {
			response = webResource.accept("application/json").get(ClientResponse.class);
		}
		catch (ClientHandlerException ex){
			if (ex.getCause() instanceof SocketTimeoutException) {
		        // timedout
				logger.error("Google book search call timed out: " + ex.getMessage());
				clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
				return clientSearchResponse;
		    }
		} 
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.000");
		String timeTaken = formatter.format((endTime - startTime) / 1000d) + " seconds";
		logger.info("Received result from google, taken " + String.valueOf(endTime - startTime) + " milliseconds.");
		
		if (response == null) {
			logger.error("Google book  search call return null");
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}
		
		if (response.getStatus() != 200) {
			logger.error("HTTP error code in Google book search: " + response.getStatus());
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}

		String output = response.getEntity(String.class);
		if (output == null || output.equals("")) {
			logger.error("Google book  search call return emput string");
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}

		GoogleBookFeedback feedback = gson.fromJson(output.trim(), GoogleBookFeedback.class);
		List<SearchResult> searchResults = translateFeedbackToSearchResult(feedback);
		
		clientSearchResponse.getResults().addAll(searchResults);
		clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.SUCCESS);
		clientSearchResponse.getStatusDetails().setTimeComsumed(timeTaken);
		clientSearchResponse.getStatusDetails().setRecord(searchResults.size());
		logger.info("Google book search: " + clientSearchResponse.getStatusDetails().getStatusCode().name());
		return clientSearchResponse;
	}
	
	private List<SearchResult> translateFeedbackToSearchResult(GoogleBookFeedback feedback) {
		List<SearchResult> results = new ArrayList<>();
		logger.info("Google book feedback " + String.valueOf(feedback.getItems().size()) + " items");
		for (GoogleBookResult googleBookResult : feedback.getItems()) {
			if (googleBookResult != null) {
				GoogleBookVolumeInfo volumeInfo = googleBookResult.getVolumeInfo();
				SearchResult result = new SearchResult();
				result.setType("book");
				if (volumeInfo.getAuthors() != null)
					result.getCreaters().addAll(volumeInfo.getAuthors());
				result.setTitle(volumeInfo.getTitle());
				results.add(result);
			}
		}
		return results;
	}

}
