package eu.europeana.api.client;

import java.util.List;

import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.ProvidersResponse;
import eu.europeana.api.client.model.provider.Provider;

public interface ProviderDatasetsClient {

	public abstract Provider getProvider(String providerId) throws EuropeanaApiProblem;

	public abstract List<Provider> getProvidersList() throws EuropeanaApiProblem;

	public abstract List<Provider> getProvidersList(int offset, int pageSize,
			String countryCode) throws EuropeanaApiProblem;

	public abstract ProvidersResponse getProvidersResponse(int offset, int pageSize,
			String countryCode) throws EuropeanaApiProblem;

}
