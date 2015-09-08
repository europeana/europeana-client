package eu.europeana.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;

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
