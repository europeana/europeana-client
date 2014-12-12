package eu.europeana.api.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.result.EuropeanaApi2Results;

public class PortalAndApiSearchTest extends BaseSearchUtils{

	@Test
	public void testSearchByPortalUrl() throws IOException, EuropeanaApiProblem{
		
		EuropeanaApi2Client client = new EuropeanaApi2Client();
		final String portalSearchUrl = "http://www.europeana.eu/portal/search.html?query=DATA_PROVIDER%3A%22The+Wellcome+Library%22+Great+War+OR+First+World+War+OR+WW1+OR+1914-1918&start=13&rows=12";
		EuropeanaApi2Results results = client.searchApi2(portalSearchUrl, 5, 1);
		assertNotNull(results.getAllItems());
		assertEquals(5, results.getAllItems().size());
		assertEquals(5, results.getItemsCount());
		
		printSearchResults(results);
	}
	
}
