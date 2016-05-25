package eu.europeana.api.client.metadata;

import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * this class is computing intensive it must be run manually when needed
 * 
 * @author Roman Graf
 *
 */
// @Ignore
public class RichSearchMusicChannelsAccessorTest extends EuClientDatasetUtil {

	String MUSIC_CHANNEL_FILTER_FILE_NAME = "/music_channel_filter";
	String JSON_FIELD_EDM_IS_SHOWN_BY = "edmIsSnownBy";
	

	@Test
	public void saveMusicChannelsContentMap() throws Throwable {
		try {

			Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
			String pathToFile = getClass().getResource(MUSIC_CHANNEL_FILTER_FILE_NAME).getFile();
			String musicChannelFilterStr = URLEncoder.encode(readStringFromFile(pathToFile), "UTF-8");
			String portalUrl = "http://www.europeana.eu/portal/collections/music?q=TYPE:SOUND&" + musicChannelFilterStr
					+ "&qf=MIME_TYPE:audio%2Fmpeg"
					// + "&qf=MIME_TYPE:audio%2Fx-flac"
					// + "&f[TYPE][]=SOUND"
					// + "&qf=provider_aggregation_edm_isShownBy%3Ahttp*"
					// + "&qt=false"
			;
			Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
			apiQuery.setProfile("rich");
			MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
			ma.setStoreItemsAsJson(true);
			ma.setBlockSize(30);
			// Map<String, String> contentMap = ma.getContentMap(-1, 223000, -1,
			// MetadataAccessor.ERROR_POLICY_CONTINUE);
			// Map<String, String> contentMap = ma.getContentMap(1, 1, 1002,
			// MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMap = ma.getContentMap(CommonMetadata.EDM_FIELD_IS_SHOWN_BY, 1, 61732,
					MetadataAccessor.ERROR_POLICY_CONTINUE);

			for (Map.Entry<String, String> pair : contentMap.entrySet()) {
				System.out.println(pair.getKey() + ";" + pair.getValue());
			}

			// File toFile = new File("/tmp/eusounds",
			// "europeana_music_channel_metadata_test.csv");
			File toFile = new File(getConfiguration().getDatasetsFolder(), "europeana_music_channel_metadata_mpeg.csv");
			writeContentMapToFile(contentMap, toFile);
			DatasetDescriptor descriptor = new DatasetDescriptor("music_channel", "europeana");
			writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
	}


	@Test
	public void saveMusicChannelsEdmIsShownBy() throws Throwable {
		
		try {
			Map<String, String> contentMap = new HashMap<String, String>();

			for (Map.Entry<String, String> pair : contentMap.entrySet()) {
				System.out.println(pair.getKey() + ";" + pair.getValue());
			}
			File toFile = new File(getConfiguration().getDatasetsFolder(), "europeana_music_channel_metadata_edmIsShownBy.csv");
			Iterator<File> it = FileUtils.iterateFiles(new File(getConfiguration().getDatasetsFolder()), null, false);
	        while(it.hasNext()){
	            String jsonFileName = ((File) it.next()).getName();
				System.out.println(jsonFileName);
				contentMap.put(jsonFileName, getIsShownByValue(jsonFileName, JSON_FIELD_EDM_IS_SHOWN_BY));
	        }
//			writeContentMapToFile(contentMap, toFile);
			DatasetDescriptor descriptor = new DatasetDescriptor("music_channel", "europeana");
			writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
	}
	
	
	/**
	 * This method reads a given field from JSON file to String.
	 * @param fileName
	 * @param fieldName The name of a field in JSON object
	 * @return The value of the given field
	 * @throws Throwable
	 */
	public String getIsShownByValue(String fileName, String fieldName) throws Throwable {
		
		String res = "";
		
        JsonObject jsonObject = new JsonObject();
        
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
            res = jsonObject.get(fieldName).getAsString();
		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
		return res;
	}

}
