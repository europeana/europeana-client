package eu.europeana.api.client.search.facet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.search.query.Api2Query;

public class FacetSearchTest {
	

	@Test
	public void testParseApiResponse() throws IOException, EuropeanaApiProblem{
		EuropeanaApi2Client client = new EuropeanaApi2Client();
		String jsonFile = "/europeanaclient/facets/pd_collections_facets.json";
		String json = readJsonFile(jsonFile);
		EuropeanaApi2Results apiResponse = client.parseApiResponse(json);
		
		assertEquals(1, apiResponse.getFacets().size());
		assertEquals("europeana_collectionName", apiResponse.getFacets().get(0).getName());
		assertTrue(apiResponse.getFacets().get(0).getFields().size() > 2 );
	}

	@Test
	public void testParseFacetLabelsApiResponse() throws IOException, EuropeanaApiProblem{
		EuropeanaApi2Client client = new EuropeanaApi2Client();
		
		// create the query object
//		Api2Query europeanaQuery = new Api2Query("\"05812_L_RO_CIMEC_ese\"");
		Api2Query europeanaQuery = new Api2Query();
//		europeanaQuery.set
//		europeanaQuery.setWhatTerms("building");
//		europeanaQuery.addQueryRefinement("NOT gips");
//		europeanaQuery.addQueryRefinement("NOT capitel");

		EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
		
		final int RESULTS_SIZE = 1;
		final int OFFSET = 1;
		String queryUrl = europeanaQuery.getQueryUrl(europeanaClient,
				RESULTS_SIZE, OFFSET);
		// System.out.println(queryUrl);
		String encodedUrl = "http://www.europeana.eu/api/v2/search.json?query=what%3A%28building%29+AND+" +
				"europeana_collectionName%3A%28%2205812_L_RO_CIMEC_ese%22%29&qf=NOT+gips&qf=NOT+capitel&rows=1&start=1&wskey="
				+ europeanaClient.getApiKey();

		assertEquals(encodedUrl, queryUrl);

		// perform search
		EuropeanaApi2Results res = europeanaClient.searchApi2(europeanaQuery,
				RESULTS_SIZE, OFFSET);

		
		String jsonFile = "/europeanaclient/facets/pd_collections_facets.json";
		String json = readJsonFile(jsonFile);
		EuropeanaApi2Results apiResponse = client.parseApiResponse(json);
		
		assertEquals(1, apiResponse.getFacets().size());
		assertEquals("europeana_collectionName", apiResponse.getFacets().get(0).getName());
		assertTrue(apiResponse.getFacets().get(0).getFields().size() > 2 );
	}

	private String readJsonFile(String testResource) throws IOException {
		BufferedReader reader = null;
		StringBuilder out = null;
		try {
			InputStream resourceAsStream = getClass().getResourceAsStream(
					testResource);
			reader = new BufferedReader(new InputStreamReader(
					resourceAsStream));
			out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
		} finally {
			if(reader!= null)
				reader.close();
		}
		return out.toString();

	}
}
