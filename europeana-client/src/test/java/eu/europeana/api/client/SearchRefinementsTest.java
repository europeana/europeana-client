package eu.europeana.api.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import eu.europeana.api.client.connection.EuropeanaApi2Client;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

public class SearchRefinementsTest {

	@Test
	public void testQueryRefinements() throws IOException {

		long ms0 = System.currentTimeMillis();

		// create the query object
		Api2Query europeanaQuery = new Api2Query(
				"\"08511_Ag_EU_ATHENA_InstituteforCulturalMemory(CIMEC),Bucharest\"");
		europeanaQuery.setWhatTerms("building");
		europeanaQuery.addQueryRefinement("NOT gips");
		europeanaQuery.addQueryRefinement("NOT capitel");

		EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
		final int RESULTS_SIZE = 1;
		final int OFFSET = 1;
		String queryUrl = europeanaQuery.getQueryUrl(europeanaClient, RESULTS_SIZE, OFFSET);
		//System.out.println(queryUrl);
		String encodedUrl = "http://www.europeana.eu/api/v2/search.json?query=what%3A%28building%29+AND" +
				"+europeana_collectionName%3A%28%2208511_Ag_EU_ATHENA_InstituteforCulturalMemory%28CIMEC%29%2CBucharest%22%29" +
				"&qf=NOT+gips&qf=NOT+capitel&rows=1&start=1&wskey=" + europeanaClient.getApiKey();
		
		assertEquals(encodedUrl, queryUrl);
		
		
		
		// perform search
		EuropeanaApi2Results res = europeanaClient.searchApi2(europeanaQuery,
				RESULTS_SIZE, OFFSET);

		
		// print out response time
		long t = System.currentTimeMillis() - ms0;
		System.out.println("response time (client+server): " + (t / 1000d)
				+ " seconds");

		assertEquals(RESULTS_SIZE, res.getItemCount());
		//we expect the collection to remain stable
		int TOTAL_EXPECTED_RESULTS = 192;
		assertEquals(TOTAL_EXPECTED_RESULTS, res.getTotalResults());
		

		int count = 0;
		for (EuropeanaApi2Item item : res.getAllItems()) {
			System.out.println();
			System.out.println("**** " + (count++ + 1));
			System.out.println("Title: " + item.getTitle());
			System.out.println("Europeana URL: " + item.getObjectURL());
			System.out.println("Type: " + item.getType());
			System.out.println("Creator(s): " + item.getDcCreator());
			System.out.println("Thumbnail(s): " + item.getEdmPreview());
			System.out.println("Data provider: " + item.getDataProvider());
		}
	}
}
