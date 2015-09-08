package eu.europeana.api.client.dataset.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.thumbnails.download.ThumbnailDownloader;
import eu.europeana.api.client.thumbnails.processing.LargeThumbnailsetProcessing;

public class DownloadThumbnailsTest extends
		EuClientDatasetUtil {

	// public static String CLASS_WW1 = "ww1";
	
	//support running the test as stand alone class
	public static void main(String[] args) throws Exception {                    
		parseParams(args);      
		JUnitCore.main(DownloadThumbnailsTest.class.getCanonicalName());            
	}
	
	
	@Test
	public void downloadThumbnails() throws FileNotFoundException, IOException {

		ensureParamsInit();
		
		File datasetFile = getDatasetFile();
		File downloadFolder = getDatasetImageFolder();

		LargeThumbnailsetProcessing datasetDownloader = new LargeThumbnailsetProcessing(datasetFile);
		datasetDownloader.addObserver(new ThumbnailDownloader(downloadFolder));
		//datasetDownloader.processThumbnailset(0, -1, 1000);
		datasetDownloader.processThumbnailset(0, 21, 10);
		
		log.debug("Skipped items: " + datasetDownloader.getFailureCount());
//		for (String itemId : skippedItems) {
//			System.out.println(itemId);
//		}
	}


	protected void ensureParamsInit() {
		//if not sent through parameters set it to test.
		if(getDataset() == null)
			setDataset("test");
	}


	protected File getDatasetImageFolder() {
		return new File(getConfiguration().getImageFolder(getDataset()));		
	}


	protected File getDatasetFile() {
		return super.getDataSetFile(false);
	}
	
	
}
