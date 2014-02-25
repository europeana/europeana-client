package eu.europeana.api.client;

import java.io.IOException;

import org.junit.Test;

import eu.europeana.api.client.connection.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;
import eu.europeana.api.client.result.EuropeanaObject;

public class ObjectRetrievalTest {

	@Test
	public void test() throws IOException, EuropeanaApiProblem {
		EuropeanaApi2Client ec = new EuropeanaApi2Client();
		Api2Query query = new Api2Query();
		query.setCollectionName("08515*");
        EuropeanaApi2Results results = ec.searchApi2(query, 10, 0);
        for(EuropeanaApi2Item result : results.getAllItems()) {
        	EuropeanaObject eo = ec.getObject(result.getId());
        	System.out.println(eo.toString());
        }
	}

}
