package eu.europeana.api.client.metadata;

import org.junit.Test;

/**
 * This class is computing intensive it must be run manually when needed.
 * It aggregates ONB dataset, stores its metadata in JSON files and generates
 * CSV file comprising europeana ID, creator, title, description and subject.
 * 
 * Sample query is:
 * 
 * http://www.europeana.eu/portal/search?q=europeana_collectionName:(2059216_Ag_EU_eSOUNDS_1001_ONB)&view=grid
 * 
 * @author Roman Graf
 *
 */
// @Ignore
public class RichSearchOnbDatasetAccessorTest extends BaseRichSearchAccessor {

	int MAX_ITEMS = 1691;
	

	@Test
	public void saveOnbDatasetContentMap() throws Throwable {
		
		String queryStr = "europeana_collectionName:(2059216_Ag_EU_eSOUNDS_1001_ONB)";
		uploadAndSaveDatasetContentMap(queryStr, MAX_ITEMS);

	}


}
