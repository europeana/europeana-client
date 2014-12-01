package eu.europeana.api.client.dataset.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.*;

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
	
	
	//TODO : add real tests and counting number of images downloaded.
	@Test
	public void downloadThumbnails() throws FileNotFoundException, IOException {
		
		File datasetFile = getDatasetFile();
		
		assertTrue(datasetFile.exists());
		
		File downloadFolder = getDatasetImageFolder();
		
		LargeThumbnailsetProcessing datasetDownloader = new LargeThumbnailsetProcessing(datasetFile);
		datasetDownloader.addObserver(new ThumbnailDownloader(downloadFolder));
		datasetDownloader.processThumbnailset(0, -1, 1000);
		
		System.out.println("Skipped items: " + datasetDownloader.getFailureCount());
//		for (String itemId : skippedItems) {
//			System.out.println(itemId);
//		}
	}

	protected File getDatasetImageFolder() {
		return new File(getConfiguration().getImageFolder(getDataset()));		
	}


	protected File getDatasetFile() {
		return new File(getConfiguration().getDatasetsFolder() + "/test.csv");
	}
	
	
}
