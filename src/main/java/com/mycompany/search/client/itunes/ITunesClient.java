package com.mycompany.search.client.itunes;

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
import com.mycompany.search.client.itunes.bean.ITunesFeedback;
import com.mycompany.search.client.itunes.bean.ITunesResult;
import com.mycompany.search.config.CommonConstants;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Object to make rest calls to search the keyword in iTunes
 * @author QQZhao
 *
 */
public class ITunesClient extends BaseTemplateClient {

	private static final Logger logger = Logger.getLogger(ITunesClient.class);

	@Override
	public ClientSearchResponse doSearchInTarget(String searchKeyword)  {
		
		ClientSearchResponse clientSearchResponse = new ClientSearchResponse();
		SearchStatusDetails statusDetails = new SearchStatusDetails();
		statusDetails.setSource("iTunes");
		clientSearchResponse.setStatusDetails(statusDetails);
		
		WebResource webResource = client.resource(config.getiTunesSearchURL())
				   .queryParam(CommonConstants.TERM, searchKeyword)
				   .queryParam(CommonConstants.LIMIT, String.valueOf(config.getiTunesSearchLimit()))
				   .queryParam(CommonConstants.ENTITY, config.getiTunesSearchEntity());
		
		logger.info("Start sending iTuness search request");
		ClientResponse response = null;
		long startTime = System.currentTimeMillis();
		try {
			response = webResource.accept("application/json").get(ClientResponse.class);
		}
		catch (ClientHandlerException ex){
			if (ex.getCause() instanceof SocketTimeoutException) {
		        // timedout
				logger.error("iTunes search call timed out: " + ex.getMessage());
				clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
				return clientSearchResponse;
		    }
		}
		
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.000");
		String timeTaken = formatter.format((endTime - startTime) / 1000d) + " seconds";
		
		logger.info("Received result from iTunes, taken " + String.valueOf(endTime - startTime) + " milliseconds.");
		
		if (response == null) {
			logger.error("iTunes search call return null");
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}
		
		if (response.getStatus() != 200) {
			logger.error("HTTP error code in iTunes search: " + response.getStatus());
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}

		String output = response.getEntity(String.class);
		if (output == null || output.equals("")) {
			logger.error("iTunes search call return emput string");
			clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.FAIL);
			return clientSearchResponse;
		}
		
		ITunesFeedback feedback = gson.fromJson(output.trim(), ITunesFeedback.class);
		List<SearchResult> searchResults = translateFeedbackToSearchResult(feedback);
		
		clientSearchResponse.getResults().addAll(searchResults);
		clientSearchResponse.getStatusDetails().setStatusCode(StatusCode.SUCCESS);
		clientSearchResponse.getStatusDetails().setTimeComsumed(timeTaken);
		clientSearchResponse.getStatusDetails().setRecord(searchResults.size());		
		logger.info("iTunes search: " + clientSearchResponse.getStatusDetails().getStatusCode().name());
		return clientSearchResponse;
	}
	
	private List<SearchResult> translateFeedbackToSearchResult(ITunesFeedback feedback) {
		List<SearchResult> results = new ArrayList<>();
		logger.info("iTunes feedback " + String.valueOf(feedback.getResults().size()) + " records");
		for (ITunesResult iTunesResult : feedback.getResults()) {
			SearchResult result = new SearchResult();
			result.setType("album");
			if (iTunesResult.getArtistName() != null)
				result.getCreaters().add(iTunesResult.getArtistName());
			result.setTitle(iTunesResult.getCollectionName());
			results.add(result);
		}
		return results;
	}
}
