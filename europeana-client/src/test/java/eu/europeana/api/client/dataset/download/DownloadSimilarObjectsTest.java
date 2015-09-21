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

public class DownloadSimilarObjectsTest extends
		EuClientDatasetUtil {

	boolean overwrite = false;
	// public static String CLASS_WW1 = "ww1";
	
	//support running the test as stand alone class
	public static void main(String[] args) throws Exception {                    
		parseParams(args);      
		JUnitCore.main(DownloadSimilarObjectsTest.class.getCanonicalName());            
	}
	
	
	@Test
	public void downloadMetadataAndSimilarForDataset() throws IOException{

		ensureParamsInit();
		
		File datasetFile = new File("/tmp/eusounds", "ids_for_extracted_features.csv");
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
		
		while (iterator.hasNext()) {
			
			line = (String) iterator.next();
			europeanaId = line.trim();
			
			//ignore comments
			if(europeanaId.isEmpty() || !europeanaId.startsWith("/"))
				continue;
			
			try{
				recordFile = new File(datasetFile.getParentFile(), "metadata/response/similar"+ europeanaId + ".json");
				
				if(!recordFile.exists() || overwrite){
					//read file
					response = client.getEuropeanaRecordResponse(europeanaId, "similar");
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
