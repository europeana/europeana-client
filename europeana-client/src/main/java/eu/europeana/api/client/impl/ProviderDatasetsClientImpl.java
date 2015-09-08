package eu.europeana.api.client.impl;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import eu.europeana.api.client.ProviderDatasetsClient;
import eu.europeana.api.client.connection.BaseApiConnection;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.model.ProviderAccessResponse;
import eu.europeana.api.client.model.ProvidersResponse;
import eu.europeana.api.client.model.provider.Provider;

/**
 * Class supporting invokation of Providers API. See http://labs.europeana.eu/api/provider/ 
 * @author Sergiu Gordea 
 *
 */
public class ProviderDatasetsClientImpl extends BaseApiConnection implements
		ProviderDatasetsClient {

	/**
	 * Default constructor which calls the default constructor of the parent
	 * EuropeanaConnection class
	 */
	public ProviderDatasetsClientImpl() {
		super();
	}

	/**
	 * Constructor which provides new strings for the search URI and and the API
	 * key.
	 * 
	 * @param baseApiUri
	 *            : Base URI for the Europeana api request (see config file).
	 * @param apiKey
	 *            : API key for the Europeana api request (see config file).
	 */
	public ProviderDatasetsClientImpl(String baseApiUri, String apiKey) {
		super(baseApiUri, apiKey);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.ProviderDatasetsClient#getProvidersList(int, int, String)
	 */
	public List<Provider> getProvidersList(int offset, int pageSize, String countryCode) throws EuropeanaApiProblem {
		ProvidersResponse response = getProvidersResponse(offset, pageSize, countryCode);
		return response.getItems();
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.ProviderDatasetsClient#getProvidersList()
	 */
	public List<Provider> getProvidersList() throws EuropeanaApiProblem {
		return getProvidersList(-1, -1, null);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.ProviderDatasetsClient#getProvidersResponse(int, int, String)
	 */
	public ProvidersResponse getProvidersResponse(int offset, int pageSize, String countryCode) throws EuropeanaApiProblem {
		String url = buildProvidersUrl(offset, pageSize, countryCode);
			
	        // Load results object from JSON
	        Gson gson = new GsonBuilder().create();
	        ProvidersResponse res = null;
	        String jsonResult = null;
			try {
				jsonResult = getJSONResult(url);
				res = gson.fromJson(jsonResult, ProvidersResponse.class);
		    } catch (JsonSyntaxException e) {
				throw new TechnicalRuntimeException("Cannot parse Json Response" + jsonResult, e);
			} catch (IOException e) {
				throw new TechnicalRuntimeException("Cannot invoke providers API:" + url, e);
			}
			if(!res.getSuccess())
				throw new EuropeanaApiProblem("Cannot retrieve list of providers!", res.getRequestNumber());
				
	        //else
	        return res;
	}

	private String buildProvidersUrl(int offset, int pageSize,
			String countryCode) {
	
		StringBuilder builder = new StringBuilder(getServiceUri());
		builder.append("/providers.json");
		builder.append("?wskey=").append(getApiKey());
		
		if(offset > 0)
			builder.append("&offset=").append(offset);
		
		if(pageSize > 0)
			builder.append("&pagesize=").append(pageSize);
		
		if(countryCode != null)
			builder.append("&countryCode=").append(countryCode);
		
		return builder.toString();
	}

	@Override
	public Provider getProvider(String providerId) throws EuropeanaApiProblem{
		ProviderAccessResponse response = getProviderResponse(providerId);
		return response.getObject();
	}
	
	protected ProviderAccessResponse getProviderResponse(String providerId) throws EuropeanaApiProblem{
		String url = buildProviderByIdUrl(providerId);
		
		 // Load results object from JSON
        Gson gson = new GsonBuilder().create();
        ProviderAccessResponse res = null;
        String jsonResult = null;
		try {
			jsonResult = getJSONResult(url);
			res = gson.fromJson(jsonResult, ProviderAccessResponse.class);
	    } catch (JsonSyntaxException e) {
			throw new TechnicalRuntimeException("Cannot parse Json Response" + jsonResult, e);
		} catch (IOException e) {
			throw new TechnicalRuntimeException("Cannot invoke providers API:" + url, e);
		}
		if(!res.getSuccess())
			throw new EuropeanaApiProblem("Cannot retrieve provider by id: " + providerId, res.getRequestNumber());
			
        //else
        return res;
	}
	
	private String buildProviderByIdUrl(String providerId) {
	
		StringBuilder builder = new StringBuilder(getServiceUri());
		builder.append("/provider/").append(providerId).append(".json");
		builder.append("?wskey=").append(getApiKey());
		
		return builder.toString();
	}
	
}
