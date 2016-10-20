package eu.europeana.api.client.dataset.download;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.metadata.MetadataAccessor;

public class EnrichDatasetObjectsTest extends
		EuClientDatasetUtil {

	String EUROPEANA_ID_LIST_CSV = "overview.csv"; 
	String EXTENDED_EUROPEANA_ID_LIST_CSV = "extended-overview.csv"; 
	
	String DBPEDIA_ID_BASE_URL = "http://dbpedia.org/resource/";
	String IA_ID_BASE_URL = "https://archive.org/details/";
	
	int EUROPEANA_ID_COL_POS = 0;
	int TITLE_COL_POS        = 1;
	int DESC_COL_POS         = 2;
	int CREATOR_ID_COL_POS   = 3;
	int DBPEDIA_ID_COL_POS   = 4;
	int IA_ID_COL_POS        = 5;
	
	String DBPEDIA_ID_STR    = "DBPedia ID";
	String IA_ID_STR         = "IA ID";
	
	
	//support running the test as stand alone class
	public static void main(String[] args) throws Exception {                    
		parseParams(args);      
		JUnitCore.main(EnrichDatasetObjectsTest.class.getCanonicalName());            
	}
	
	
	@Test
	public void enrichMetadataForDatasetRegardingDBPediaIdAndInternetArchiveId() throws IOException{

		ensureParamsInit();
		
		File datasetFile = (new MetadataAccessor()).getDatasetFile(EUROPEANA_ID_LIST_CSV);
		if(!datasetFile.exists())
			fail("required dataset file doesn't exist" + datasetFile);
		
		LineIterator iterator = FileUtils.lineIterator(datasetFile);
		String line;
		String europeanaId = "";
		String title = "";
		String description = "";
		String creator = "";
		String dbpediaId = "";
		String iaId = ""; // Internet Archive ID
		int cnt = 0;
		final String cellSeparator = ";"; 
		final String lineBreak = "\n"; 
		
		File recordFile = new File(datasetFile.getParentFile(), EXTENDED_EUROPEANA_ID_LIST_CSV);
		
		while (iterator.hasNext()) {
			
			line = (String) iterator.next();
			String[] items = line.split(cellSeparator);
			if (items != null && items.length >= CREATOR_ID_COL_POS && items[CREATOR_ID_COL_POS] != null) {
				String row = new StringBuilder().append(line)
						.append(DBPEDIA_ID_STR).append(cellSeparator)
						.append(IA_ID_STR).append(lineBreak)
						.toString();
				if (cnt > 0) {
					europeanaId = items[EUROPEANA_ID_COL_POS];
					//ignore comments
					if(europeanaId.isEmpty() || !europeanaId.startsWith("/"))
						continue;
					title = items[TITLE_COL_POS];
					description = items[DESC_COL_POS];
					creator = items[CREATOR_ID_COL_POS];
					iaId = IA_ID_BASE_URL + creator.replaceAll("\"", "").replaceAll(" ", "");
					if (creator.contains(DBPEDIA_ID_BASE_URL)) {
						String[] creatorArr = creator.split(DBPEDIA_ID_BASE_URL);
						creator = creatorArr[0].replaceAll("\"", "");
						dbpediaId = DBPEDIA_ID_BASE_URL + creatorArr[1].replaceAll("\"", "");
						iaId = IA_ID_BASE_URL + creatorArr[1].replaceAll("\"", "").replaceAll(" ", "").replaceAll("_", "");
					}
					row = new StringBuilder().append(europeanaId).append(cellSeparator)
							.append(title).append(cellSeparator)
							.append(description).append(cellSeparator)
							.append(creator).append(cellSeparator)
							.append(dbpediaId).append(cellSeparator)
							.append(iaId).append(lineBreak)
							.toString();
					FileUtils.writeStringToFile(recordFile, row, "UTF-8", true);
				} else {
					FileUtils.writeStringToFile(recordFile, row, "UTF-8");					
				}
				cnt++;
			}
		}		
		log.info("Successfully enriched items: " + cnt);		
	}

	
	protected void ensureParamsInit() {
		//if not sent through parameters set it to test.
		if(getDataset() == null)
			setDataset("allsound");
	}

	
}
