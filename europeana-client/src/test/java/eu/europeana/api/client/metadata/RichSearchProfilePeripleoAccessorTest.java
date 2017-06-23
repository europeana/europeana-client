package eu.europeana.api.client.metadata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * This class is computing intensive it must be run manually when needed 
 * 
 * It is a data loader from Europeana API for Peripleo
 * 
 * e.g. Europeana portal link for sculptures for Rome:
 * http://www.europeana.eu/portal/en/search?view=grid&q=where%3Arome&qf%5B%5D=sculpture&f%5BMEDIA%5D%5B%5D=true&f%5BTYPE%5D%5B%5D=IMAGE&per_page=96
 * 
 * related Europeana API link for sculptures for Rome:
 * http://www.europeana.eu/api/v2/search.json?view=grid&query=where%3Arome&qf=sculpture&MEDIA=true&TYPE=IMAGE&per_page=96&wskey=xxx
 * 
 * @author Roman Graf
 *
 */
//@Ignore
public class RichSearchProfilePeripleoAccessorTest extends EuClientDatasetUtil {

	//@Test
	public void saveRomeSculptures() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=where%3Arome&qf=sculpture&media=true&type=IMAGE&TYPE=IMAGE&wskey=api2demo
		// total 164
		String downloadUrl = "http://www.europeana.eu/portal/search.html?view=grid&query=where%3Arome&qf=sculpture&media=true&type=IMAGE&TYPE=IMAGE";
		String downloadDir = "rome-sculptures/";
		
		downloadJsonResults(downloadUrl, downloadDir);
	
	}

	@Test
	public void saveRomeMonuments() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=what%3Amonument&qf=%22%2Fplace%2Fbase%2F143433%22&media=true&TYPE=IMAGE&per_page=96&wskey=api2demo
		// total 969
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?view=grid&query=what%3Amonument&qf=%22%2Fplace%2Fbase%2F143433%22&media=true&TYPE=IMAGE&per_page=96";
		String downloadDir = "rome-monuments/";
		
		downloadJsonResults(downloadUrl, downloadDir);
	
	}

	private void downloadJsonResults(String portalUrl, String downloadDir)
			throws MalformedURLException, UnsupportedEncodingException, IOException, EuropeanaApiProblem {

		Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
		
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("rich");
		MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
		ma.setStoreItemsAsJson(true);
		ma.setMetadataFolder(downloadDir);
		Map<String, String> contentMap = ma.getContentMap(-1, 0, 200000, MetadataAccessor.ERROR_POLICY_CONTINUE);
		
		for (Map.Entry<String, String> pair : contentMap.entrySet()) {
			System.out.println(pair.getKey() + ";" + pair.getValue());
		}
	}
	
	
}
