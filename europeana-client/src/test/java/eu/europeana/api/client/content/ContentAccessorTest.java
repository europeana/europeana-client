package eu.europeana.api.client.content;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

public class ContentAccessorTest extends EuClientDatasetUtil {

	@Test
	public void saveSoundContentMap() throws IOException{
		Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
		String portalUrl = "http://www.europeana.eu/portal/search.html?query=provider_aggregation_edm_isShownBy%3Ahttp*&rows=24&qf=TYPE%3ASOUND&qt=false";
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("rich");
		
		File toFile = new File("/tmp/eusounds", "europeana_allsound.csv");
		if(!toFile.exists()){
			//File toFile = new File("/tmp/eusounds", "europeana_allsound_test.csv");
			
			ContentAccessor ca = new ContentAccessor(apiQuery, null);
			Map<String, String> contentMap = ca.getContentMap(CommonMetadata.EDM_FIELD_IS_SHOWN_BY, -1, -1, ContentAccessor.ERROR_POLICY_CONTINUE);
			//Map<String, String> contentMap = ca.getContentMap(CommonMetadata.EDM_FIELD_IS_SHOWN_BY, -1, 5, ContentAccessor.ERROR_POLICY_CONTINUE);
			//writeContentMapToFile(, contentMap, toFile);
			DatasetDescriptor descriptor = new DatasetDescriptor("allsound", "europeana");
			writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
		}else{
			fail("The provided content file already exists on the given location. Please delete the file and run the test again!" + toFile.getAbsolutePath());
			//fail("");
		}
	}
	
	
}
