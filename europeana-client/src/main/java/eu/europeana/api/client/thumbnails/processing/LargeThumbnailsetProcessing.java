package eu.europeana.api.client.thumbnails.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

import org.apache.log4j.Logger;

public class LargeThumbnailsetProcessing extends Observable{

	
	Logger log = Logger.getLogger(getClass());
	
	File datasetfile;
	int lastReadPosition;
	Map<String, String> thumbnailBlock; 
	int failureCount;
	int skippedItemsCount;
	int itemsProcessed;
	
	
	public int getFailureCount() {
		return failureCount;
	}

	
	public LargeThumbnailsetProcessing(File datasetFile) {
		this.datasetfile = datasetFile;
	}
	
	public void increaseFailureCount(int amount) {
		this.failureCount += amount;
	}


	public File getDatasetfile() {
		return datasetfile;
	}


	public void setDatasetfile(File datasetfile) {
		this.datasetfile = datasetfile;
	}


	public int getLastReadPosition() {
		return lastReadPosition;
	}


	public Map<String, String> getThumbnailBlock() {
		return thumbnailBlock;
	}
	
	public void processThumbnailset(int start, int limit, int blockSize)
			throws FileNotFoundException, IOException {
		
		thumbnailBlock = new LinkedHashMap<String, String>(blockSize);
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(getDatasetfile()));
			// BufferedReader reader = new BufferedReader(new
			// FileReader("/collection_07501_thumbnails.csv"));

			String europeanaUriAndObject = null;
			
			
			while ((europeanaUriAndObject = reader.readLine()) != null) {
				//ignore comments
				if(europeanaUriAndObject.startsWith("#"))
					continue;
				
				//increase line counter
				lastReadPosition++;
				
				//skip first <start> positions
				if(start > 0 && start >= lastReadPosition){
					if(start == lastReadPosition)
						log.info("Items skiped from processing: " + lastReadPosition);
					continue;
				}
				
				extractValues(europeanaUriAndObject);
				
				//increase processed items counter
				itemsProcessed++;
				
				//process objects blockwise
				if(thumbnailBlock.size() == blockSize || isLimitReached(itemsProcessed, limit) ){
					processThumbnailBlock();
				}
				
				if(isLimitReached(itemsProcessed, limit))
					break;
				
			}
			
			//process last incomplete block
			if(!thumbnailBlock.isEmpty()){
				processThumbnailBlock();
			}
			
		} finally {
				closeReader(reader); 
		}
		
	}

	protected void processThumbnailBlock() {
		setChanged();
		notifyObservers(thumbnailBlock);
		thumbnailBlock.clear();
		
		log.info("items processed: " + lastReadPosition);
	}

	protected String[] extractValues(String europeanaUriAndObject) {
		String[] values;
		//parse file entries, first one should be the object id
		values = europeanaUriAndObject.split(";", 3);
		thumbnailBlock.put(values[0], values[1]);
		return values;
	}

	protected boolean isLimitReached(int itemsProcessed, int limit) {
		return limit > 0 && itemsProcessed >= limit;
	}


	protected void closeReader(BufferedReader reader) {
		try{
			if(reader != null)
				reader.close();
		} catch (Exception e){
			log.warn("cannot close reader" + e); 
		}
	}


	public int getSkippedItemsCount() {
		return skippedItemsCount;
	}


	public void increaseSkippedItemsCount(int amount) {
		this.skippedItemsCount += amount;
	}


	public int getItemsProcessed() {
		return itemsProcessed;
	}

	
}
