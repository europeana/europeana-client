package eu.europeana.api.client.provider;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import eu.europeana.api.client.ProviderDatasetsClient;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.impl.ProviderDatasetsClientImpl;
import eu.europeana.api.client.model.provider.Provider;

public class ProviderDatasetsClientTest {

	Logger log = Logger.getLogger(getClass());
	
	@Test
	public void getProviders() throws IOException, EuropeanaApiProblem{
		ProviderDatasetsClient client = new ProviderDatasetsClientImpl();
		List<Provider> providers = client.getProvidersList();
		assertNotNull(providers);
		assertTrue(providers.size() > 5);
		
		int cnt = 0;
		for (Provider provider : providers) {
			cnt++;
			//System.out.println(provider.toString());
			if(cnt <= 5)
				log.info(provider);
			else
				break;
						
		}
	}
		
		
		@Test
		public void getProvidersWithLimitAndPageSize() throws IOException, EuropeanaApiProblem{
			ProviderDatasetsClient client = new ProviderDatasetsClientImpl();
			
			List<Provider> providers  = client.getProvidersList(-1, 3, null);
			assertNotNull(providers);
			assertTrue(providers.size() == 3);
			Provider firstProvider = providers.get(0);
			Provider thirdProvider = providers.get(2);
			for (Provider provider : providers)
				log.info(provider);
		
			providers = client.getProvidersList(2, 1, null);
			assertNotNull(providers);
			assertTrue(providers.size() == 1);
			Provider thirdProviderAbsolute = providers.get(0);
			
			assertEquals(thirdProvider, thirdProviderAbsolute);
			assertFalse(firstProvider.equals(thirdProviderAbsolute));
			log.debug("First absolute provider, no offset:" + firstProvider);
			log.debug("Third absolute provider, no offset:" + thirdProvider);
			log.debug("Third absolute result, offset 2:" + thirdProviderAbsolute);
			
			
			
			
			
			
			
			
			
//		String jsonFile = "/myeuropeana/mydata_tag.json";
//		String json = readJsonFile(jsonFile);
//		TagsApiResponse apiResponse = client.parseTagsApiResponse(json);
//		assertEquals(600, apiResponse.getItemsCount());
//		assertEquals(600, apiResponse.getTotalResults());
	}


}
