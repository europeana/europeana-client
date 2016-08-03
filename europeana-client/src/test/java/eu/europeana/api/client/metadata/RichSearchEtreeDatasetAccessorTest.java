package eu.europeana.api.client.metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * This class is computing intensive it must be run manually when needed.
 * It aggregates ONB ETree dataset, stores its metadata in JSON files and generates
 * CSV file comprising europeana ID, creator, title, description and subject.
 * 
 * Sample query is:
 * 
 * http://www.europeana.eu/portal/en/search?q=what:"etree"
 * 
 * @author Roman Graf
 *
 */
// @Ignore
public class RichSearchEtreeDatasetAccessorTest extends EuClientDatasetUtil {

	String searchOnbDatasetFodler = "./src/test/resources/onb";
	String OUTPUT_CSV_FILE = "europeana_onb_etree_dataset_metadata.csv";
	int MAX_ITEMS = 10491;

	

	@Test
	public void saveOnbDatasetContentMap() throws Throwable {
		
		try {

			Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
			String collectionStr = "what:\"etree\"";
			String portalUrl = "http://www.europeana.eu/portal/search?query="
					+ URLEncoder.encode(collectionStr, "UTF-8");
			Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
			apiQuery.setProfile("rich");
			MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
			ma.setStoreItemsAsJson(true);
			ma.setBlockSize(100);
			ma.setStoreBlockwiseAsJson(true);
			
			File toFile = new File(searchOnbDatasetFodler, OUTPUT_CSV_FILE);
			// create parent dirs
			toFile.getParentFile().mkdirs();
//			BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(searchOnbDatasetFodler + "/" + OUTPUT_CSV_FILE), "UTF-8"));            
			writer.write("Europeana ID;Title;Description;Creator;\n"); // header
			
			Map<String, String> contentMap = ma.getContentMap(CommonMetadata.FIELD_TITLE, 1, MAX_ITEMS,
					MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapTitle = new HashMap<String,String>(contentMap);
			contentMap = ma.getContentMap(CommonMetadata.FIELD_DC_DESCRIPTION, 1, MAX_ITEMS, MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapDcDescription = new HashMap<String,String>(contentMap);
			contentMap = ma.getContentMap(CommonMetadata.FIELD_DC_CREATOR, 1, MAX_ITEMS, MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapDcCreator = new HashMap<String,String>(contentMap);

			for (Map.Entry<String, String> pair : contentMapTitle.entrySet()) {
		    	String europeanaId = pair.getKey();
		    	String title = pair.getValue();
		    	String description = contentMapDcDescription.get(europeanaId);
		    	String creator = contentMapDcCreator.get(europeanaId);
		    	
				writer.write(europeanaId);
				writer.write(";");
				writer.write(title);
				writer.write(";");
				writer.write(description);
				writer.write(";");
				writer.write(creator);
				writer.write(";");
				writer.write("\n");
	    	}		

			writer.flush();
			writer.close();

		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
	}


}
