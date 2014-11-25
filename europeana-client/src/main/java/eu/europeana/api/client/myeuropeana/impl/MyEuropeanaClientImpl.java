package eu.europeana.api.client.myeuropeana.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.config.EuropeanaApiConfiguration;
import eu.europeana.api.client.connection.BaseApiConnection;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.myeuropeana.MyEuropeanaClient;
import eu.europeana.api.client.myeuropeana.exception.MyEuropeanaApiException;
import eu.europeana.api.client.myeuropeana.result.TagsApiResponse;

public class MyEuropeanaClientImpl extends BaseApiConnection implements
		MyEuropeanaClient {

	public EuropeanaApiConfiguration getConfiguration() {
		return ClientConfiguration.getInstance();
	}

	public MyEuropeanaClientImpl(String myEuropeanaServiceUri, String apiKey) {
		super(myEuropeanaServiceUri, apiKey);
		// initialize configuration
		getConfiguration();
	}

	public MyEuropeanaClientImpl() {
		this(null, null);
		// initialize attributes
		setApiKey(getConfiguration().getApiKey());
		setServiceUri(getConfiguration().getEuropeanaUri());
	}

	@Override
	public TagsApiResponse parseTagsApiResponse(String json)
			throws MyEuropeanaApiException {

		// Load results object from JSON
		Gson gson = new GsonBuilder().create();
		TagsApiResponse res = gson.fromJson(json, TagsApiResponse.class);

		if (!res.getSuccess())
			throw new MyEuropeanaApiException(res.getError(),
					res.getRequestNumber());

		logger.trace("Number of retrieved results: " + res.getItemsCount());
		logger.debug("Total results: " + res.getTotalResults());

		return res;
	}

	/**
	 * Creates URL based on the URI passed in.
	 */
	protected String buildInvokationUrl(String action, String apiKey) {
		StringBuilder sb = new StringBuilder();
		sb.append(getConfiguration().getSearchUri());
		sb.append(action);
		sb.append("?wsKey=").append(apiKey);

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.myeuropeana.MyEuropeanaClient#parseTagsApiResponse(java.io.InputStream)
	 */
	@Override
	public TagsApiResponse parseTagsApiResponse(InputStream jsonStream)
			throws MyEuropeanaApiException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				jsonStream));
		StringBuilder out = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null){
				out.append(line);
			}	
		} catch (IOException e) {
			throw new TechnicalRuntimeException("Cannot read input stream!", e); 
		}

		return parseTagsApiResponse(out.toString());
	}

}
