package eu.europeana.api.client.dataset.download;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.metadata.MetadataAccessor;

public class DownloadFullObjectsTest extends
		EuClientDatasetUtil {

	String EUROPEANA_ID_LIST_CSV = "overview.csv"; 
	
	
	boolean overwrite = false;
	
	//support running the test as stand alone class
	public static void main(String[] args) throws Exception {                    
		parseParams(args);      
		JUnitCore.main(DownloadFullObjectsTest.class.getCanonicalName());            
	}
	
	
	@Test
	public void downloadMetadataForDataset() throws IOException{

		ensureParamsInit();
		
		File datasetFile = (new MetadataAccessor()).getDatasetFile(EUROPEANA_ID_LIST_CSV);
		if(!datasetFile.exists())
			fail("required dataset file doesn't exist" + datasetFile);
		
		EuropeanaApi2Client client = new EuropeanaApi2Client();
		
		LineIterator iterator = FileUtils.lineIterator(datasetFile);
		String line;
		String europeanaId;
		String response; 
		File recordFile;
		int cnt = 0;
		int failedCounter = 0; 
		int skipCounter = 0;
		final String cellSeparator = ";"; // "\t";
		
		while (iterator.hasNext()) {
			
			line = (String) iterator.next();
			europeanaId = line.split(cellSeparator, 2)[0];
			
			//ignore comments
			if(europeanaId.isEmpty() || !europeanaId.startsWith("/"))
				continue;
			
			try{
				recordFile = new File(datasetFile.getParentFile(), "full"+ europeanaId + ".json");
//				recordFile = new File(datasetFile.getParentFile(), "metadata/response/record"+ europeanaId + ".json");
				
				if(!recordFile.exists() || overwrite){
					//read file
					response = client.getEuropeanaRecordResponse(europeanaId);
					FileUtils.writeStringToFile(recordFile, response, "UTF-8");
					cnt++;
					
					if(cnt%100==0)
						log.debug("Downloaded metadata files: " + cnt);					
				}else{
					skipCounter++;
				}
				
			}catch(Exception e){
				//log failures
				failedCounter++;
				log.debug("Download Error: cannot retrieve object with id: " + europeanaId);
				log.trace("Stacktrace", e);
			}
		}
		
		log.info("Successfully downloaded files: " + cnt);
		log.info("Failed downloads: " + failedCounter);
		log.info("Skipped downloads: " + skipCounter);
		
	}


	protected void ensureParamsInit() {
		//if not sent through parameters set it to test.
		if(getDataset() == null)
			setDataset("allsound");
	}

	
}
