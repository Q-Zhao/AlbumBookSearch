package com.mycompany.search.client;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mycompany.search.config.SearchConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * A template client is defined here for extending new client (e.g. twitter, facebook).
 * To add a new client for new domain should go with the following steps:
 * 1. create a concrete client class extending this, and implementing the doSearchInTarget function, which handles the real search call.
 * 2. define a spring bean for this concrete client class in applicationContext.xml
 * 3. add the reference of this bean to the searchClientPool list in "searchService" bean.
 * @author QQZhao
 *
 */
public abstract class BaseTemplateClient {

	protected SearchConfig config;
	protected Client client;
	protected Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(BaseTemplateClient.class);

	public void setConfig(SearchConfig config) {
		this.config = config;
	}

	public void initialize() {
		ClientConfig cc = new DefaultClientConfig();
        cc.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, config.getTimedOutInSeconds() * 1000);
		client = Client.create(cc);
		logger.info("Base Client Initialized");
	}
	
	/**
	 * Do the rest call keyword searching in target domain. 
	 * @param searchKeyword
	 * @return ClientSearchResponse
	 */
	public abstract ClientSearchResponse doSearchInTarget(String searchKeyword);
	
}
