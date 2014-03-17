package eu.europeana.api.client.dataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import eu.europeana.api.client.config.ThumbnailAccessConfiguration;

public abstract class BaseDatasetUtil {

	public static final String PREFIX_START="start=";
	public static final String PREFIX_LIMIT="limit=";
	public static final String PREFIX_BLOCKSIZE="blockSize=";
	public static final String PREFIX_DATASET="dataset=";
	public static final int DEFAULT_BLOCKSIZE = 1000;
	
	protected static int blockSize=-2;
	protected static int limit=-2;
	protected static int start=-2;
	protected static String paramDataset=null;
	
	private String dataset = "test";
	
	public abstract ThumbnailAccessConfiguration getConfiguration();
	
	
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getDataset() {
		return dataset;
	}
	
	
	BufferedWriter getDataSetFileWriter(boolean urls)
			throws IOException {
		File datasetFile = getDataSetFile(urls);
		datasetFile.getParentFile().mkdirs();

		return new BufferedWriter(new FileWriter(datasetFile));
	}
	
	public abstract File getDataSetFile(boolean urls);

	protected static int getValue(String argPrefix, final String arg) {
		return Integer.parseInt(getStringValue(argPrefix, arg));
	}

	protected static String getStringValue(String argPrefix, final String arg) {
		return arg.substring(argPrefix.length());
	}

	protected static void parseParams(String[] args) {
		for (int i = 0; i < args.length; i++) {
			
			if(args[i].startsWith(PREFIX_START)){
				start = getValue(PREFIX_START, args[i]);
				continue;
			}else if(args[i].startsWith(PREFIX_LIMIT)){
				limit = getValue(PREFIX_LIMIT, args[i]);
				continue;
			}else if(args[i].startsWith(PREFIX_BLOCKSIZE)){
				blockSize = getValue(PREFIX_BLOCKSIZE, args[i]);
				continue;
			}else if(args[i].startsWith(PREFIX_DATASET)){
				paramDataset = getStringValue(PREFIX_DATASET, args[i]);
				//continue;
			}
		}
		
	}
}
