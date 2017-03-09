package eu.europeana.api.client.metadata;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2Query;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * this class is computing intensive it must be run manually when needed
 * 
 * @author Sergiu Gordea
 *
 */
//@Ignore
public class RichProfileByCollectionTest extends EuClientDatasetUtil {

	@Test
	public void saveMedataForCollections() throws IOException, EuropeanaApiProblem {

		File collectionsFile = new File("/tmp/europeana/collections/europeana_collections.csv");
		List<String> collections = FileUtils.readLines(collectionsFile);
		String collectionId = null;
		for (String collection : collections) {
			if (!collection.startsWith("#")) {
				collectionId = collection.split(";", 2)[0];
				saveRichResponseForCollection(collectionId);
			}
		}
	}
	public void saveRichResponseForCollection(String collectionId) 
			throws IOException, EuropeanaApiProblem {
		
		Api2QueryInterface apiQuery = new Api2Query(collectionId + "_*");
		apiQuery.setProfile("rich");
		MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
		ma.setMetadataFolder("/tmp/europeana/collections/metadata/");
		ma.setStoreBlockwiseAsJson(true);
		// Map<String, String> contentMap =
		ma.getContentMap(CommonMetadata.EDM_FIELD_IGNORE, CommonMetadata.START_BEGINING, CommonMetadata.LIMIT_ALL,
				MetadataAccessor.ERROR_POLICY_CONTINUE);

	}
}
