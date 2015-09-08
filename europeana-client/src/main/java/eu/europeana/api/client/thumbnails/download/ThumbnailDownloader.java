package eu.europeana.api.client.thumbnails.download;

import java.io.File;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.thumbnails.ThumbnailsAccessor;
import eu.europeana.api.client.thumbnails.processing.LargeThumbnailsetProcessing;

public class ThumbnailDownloader extends ThumbnailsAccessor implements Observer {

	File downloadFolder;
	
	
	public File getDownloadFolder() {
		return downloadFolder;
	}

	public ThumbnailDownloader(File downloadFolder){
		this.downloadFolder = downloadFolder;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(! (arg instanceof Map))
			throw new TechnicalRuntimeException("Wrong argument type. Expected map but invoked with " + arg.getClass());
		
		@SuppressWarnings("unchecked")
		Map<String, String> thumbnailMap = (Map<String, String>) arg; 
		
		int failureCount = 0;
		boolean successful;
		
		for (Map.Entry<String, String> thumbnail : thumbnailMap.entrySet()) {
			successful = writeThumbnailToFolder(thumbnail.getKey(), thumbnail.getValue(), getDownloadFolder());
			
			if(!successful){
				
				log.warn("Cannot download: " + thumbnail.getKey() + ";" + thumbnail.getValue());
				failureCount++;
			}
		}
		
		((LargeThumbnailsetProcessing) o).increaseFailureCount(failureCount);
		((LargeThumbnailsetProcessing) o).increaseSkippedItemsCount(getSkippedItems());
		this.resetSkippedItems();
	}

}
