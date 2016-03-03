package eu.europeana.api.client.metadata;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

import java.net.URLEncoder;


/**
 * this class is computing intensive it must be run manually when needed 
 * 
 * @author Roman Graf
 *
 */
//@Ignore
public class RichSearchMusicChannelsAccessorTest extends EuClientDatasetUtil {
	
	String MUSIC_CHANNEL_FILTER_FILE_NAME = "/music_channel_filter";

	@Test
	public void saveMusicChannelsContentMap() throws IOException, EuropeanaApiProblem {
		Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
		String pathToFile = getClass().getResource(
				MUSIC_CHANNEL_FILTER_FILE_NAME).getFile();
		String musicChannelFilterStr = URLEncoder.encode(readStringFromFile(pathToFile), "UTF-8");
		String portalUrl = 
			"http://www.europeana.eu/portal/collections/music?" 
			+ "q=(" + musicChannelFilterStr + ")" 
		    + "&qf=TYPE:SOUND&qf=provider_aggregation_edm_isShownBy%3Ahttp*"
			+ "&qt=false";
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("rich");
		MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
		ma.setStoreItemsAsJson(true);
//		Map<String, String> contentMap = ma.getContentMap(-1, 223000, -1, MetadataAccessor.ERROR_POLICY_CONTINUE);
//		Map<String, String> contentMap = ma.getContentMap(1, 1, 1002, MetadataAccessor.ERROR_POLICY_CONTINUE);
		Map<String, String> contentMap = ma.getContentMap(1, 1, 61732, MetadataAccessor.ERROR_POLICY_CONTINUE);
		
		for (Map.Entry<String, String> pair : contentMap.entrySet()) {
			System.out.println(pair.getKey() + ";" + pair.getValue());
		}
		
//		File toFile = new File("/tmp/eusounds", "europeana_music_channel_metadata_test.csv");
		File toFile = new File("/", "europeana_music_channel_metadata_test.csv");
		writeContentMapToFile(contentMap, toFile);
		DatasetDescriptor descriptor = new DatasetDescriptor("music_channel", "europeana");
		writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
	}
	
	
}
