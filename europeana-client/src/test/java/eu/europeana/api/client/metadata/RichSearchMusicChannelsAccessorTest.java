package eu.europeana.api.client.metadata;

import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
	String JSON_FIELD_EDM_IS_SHOWN_BY = "edmIsShownBy";
	

	@Test
	public void saveMusicChannelsContentMap() throws Throwable {
		try {

			Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
			String pathToFile = getClass().getResource(MUSIC_CHANNEL_FILTER_FILE_NAME).getFile();
//			String musicChannelFilterStr = URLEncoder.encode(readStringFromFile(pathToFile), "UTF-8");
			String musicChannelFilterStr = readStringFromFile(pathToFile);
			String portalUrl = "http://www.europeana.eu/portal/collections/music?query="+
					URLEncoder.encode("TYPE:SOUND")
					+ "&qf="+ URLEncoder.encode(musicChannelFilterStr, "UTF-8")
					+ "&MIME_TYPE=" + URLEncoder.encode("audio/mpeg", "UTF-8")
					+ "&media=true"
//					+ "&qf[MIME_TYPE][]=" + URLEncoder.encode("audio/mpeg","UTF-8")
					// + "&qf=MIME_TYPE:audio%2Fx-flac"
//					+ "&qf=" + URLEncoder.encode("provider_aggregation_edm_isShownBy:http*", "utf-8")
//					+ "&media=true"
					
			;
			Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
			apiQuery.setProfile("rich");
			MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
			ma.setStoreItemsAsJson(true);
			ma.setBlockSize(100);
			ma.setStoreBlockwiseAsJson(true);
			// Map<String, String> contentMap = ma.getContentMap(-1, 223000, -1,
			// MetadataAccessor.ERROR_POLICY_CONTINUE);
			// Map<String, String> contentMap = ma.getContentMap(1, 1, 1002,
			// MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMap = ma.getContentMap(CommonMetadata.EDM_FIELD_IS_SHOWN_BY, 1, 45000,
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

			File toFile = new File(getConfiguration().getDatasetsFolder(), "europeana_music_channel_metadata_edmIsShownBy.csv");
		    File[] files = new File(getConfiguration().getDatasetsFolder()).listFiles();
		    traverseFiles(files, contentMap);
			DatasetDescriptor descriptor = new DatasetDescriptor("music_channel", "europeana");
			writeMapToCsvFile(descriptor, contentMap, toFile, POLICY_OVERWRITE_FILE);
		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
	}
	

	/**
	 * Traverse directories containing metadata JSON files storing required values in a map.
	 * @param files
	 * @param contentMap
	 * @throws Throwable 
	 */
	public void traverseFiles(File[] files, Map<String, String> contentMap) throws Throwable {
	    for (File file : files) {
	        String fileName = file.getPath();
			if (file.isDirectory()) {
	            System.out.println("Directory: " + fileName);
	            traverseFiles(file.listFiles(), contentMap); 
	        } else {
	            System.out.println("File: " + fileName);
				if (!fileName.contains(".csv")) {
					String isShownByValue = getIsShownByValue(fileName, JSON_FIELD_EDM_IS_SHOWN_BY);
					if (!isShownByValue.equals("")) {
						String[] path = fileName.replace("\\", "\\\\").split("\\\\");
						int len = path.length;
						String id = "\\" + path[len-3] + "\\" + path[len-1];
						contentMap.put(id, isShownByValue);
					}
				}
	        }
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
//			throw th;
		}
		return res;
	}

}
