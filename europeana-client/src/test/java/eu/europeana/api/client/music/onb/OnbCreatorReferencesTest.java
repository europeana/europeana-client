package eu.europeana.api.client.music.onb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * this class is computing intensive it must be run manually when needed 
 * 
 * @author Sergiu Gordea 
 *
 */
//@Ignore
public class OnbCreatorReferencesTest extends EuClientDatasetUtil {

	EuropeanaApi2Client client = new EuropeanaApi2Client();
	
	public EuropeanaApi2Client getClient() {
		return client;
	}

	public void setClient(EuropeanaApi2Client client) {
		this.client = client;
	}

	@Test
	public void getAuthorReferencesMap() throws IOException, EuropeanaApiProblem, URISyntaxException{
		
		
//		File authorsFile = new File("/tmp/europeana/collections/europeana_collections.csv");
		URL inputFileUrl = getClass().getResource("/europeanaclient/datasets/onb_authors.csv");
		
		File authorsFile = new File(inputFileUrl.toURI());
		List<String> authors= FileUtils.readLines(authorsFile);
		Map<String, String> histogram =  new LinkedHashMap<String, String>();
		String authorName = null;
		
		
		for (String author : authors) {
			if(!author.startsWith("#") && !author.contains("?")) {
				authorName = author.split(";", 2)[0];
				authorName = authorName.replaceAll("<","");
				authorName = authorName.replaceAll(">","");
				
				long refsCount = getRefsCount(authorName);
				System.out.println(authorName + ";" + refsCount);
				histogram.put(authorName , ""+refsCount);
				
				//saveMinimalResponseForCollection(collectionId);
			}
		}
		
		writeMapToCsvFile(new DatasetDescriptor("ONB", "authors"), histogram, new File("/tmp/eusounds/onb/onb_author_references.csv"), POLICY_APPEND_TO_FILE);
		
	}

	private long getRefsCount(String authorName) throws IOException, EuropeanaApiProblem {
		
		Api2QueryBuilder queryBuilder = getClient().getQueryBuilder();
		//String portalUrl = "http://www.europeana.eu/portal/search.html?query=who%3a(" + authorName + ")";
		String portalUrl = "http://www.europeana.eu/portal/search.html?query=who:(" +URLEncoder.encode(authorName, "UTF-8")  + ")";
//		String portalUrl = "http://www.europeana.eu/portal/search.html?query=provider_aggregation_edm_isShownBy%3Ahttp*&rows=24&qf=TYPE%3ASOUND&qt=false";
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("minimal");
		
		EuropeanaApi2Results results = getClient().searchApi2(apiQuery, 0, -1);
		return results.getTotalResults();
		
//		
//		// TODO Auto-generated method stub
//		return null;
	}
	
	
}
