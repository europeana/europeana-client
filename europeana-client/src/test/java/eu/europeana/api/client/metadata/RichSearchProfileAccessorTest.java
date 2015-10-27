package eu.europeana.api.client.metadata;

import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * this class is computing intensive it must be run manually when needed 
 * 
 * @author Sergiu Gordea 
 *
 */
@Ignore
public class RichSearchProfileAccessorTest extends EuClientDatasetUtil {

	@Test
	public void saveSoundContentMap() throws IOException{
		Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
		String portalUrl = "http://www.europeana.eu/portal/search.html?query=provider_aggregation_edm_isShownBy%3Ahttp*&rows=24&qf=TYPE%3ASOUND&qt=false";
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("rich");
		MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
		ma.setStoreItemsAsJson(true);
		Map<String, String> contentMap = ma.getContentMap(-1, 223000, -1, MetadataAccessor.ERROR_POLICY_CONTINUE);
		
		for (Map.Entry<String, String> pair : contentMap.entrySet()) {
			System.out.println(pair.getKey() + ";" + pair.getValue());
		}
		
//		File toFile = new File("/tmp/eusounds", "europeana_allsound_metadata_test.csv");
//		writeContentMapToFile(contentMap, toFile);
//		DatasetDescriptor descriptor = new DatasetDescriptor("allsound", "europeana");
//		writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
	}
	
	
}
