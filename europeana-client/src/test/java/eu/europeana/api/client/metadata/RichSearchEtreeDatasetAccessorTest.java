package eu.europeana.api.client.metadata;

import org.junit.Test;

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
public class RichSearchEtreeDatasetAccessorTest extends BaseRichSearchAccessor {

	int MAX_ITEMS = 10491;

	

	@Test
	public void saveEtreeDatasetContentMap() throws Throwable {
		
		String queryStr = "what:\"etree\"";
		uploadAndSaveDatasetContentMap(queryStr, MAX_ITEMS);

	}


}
