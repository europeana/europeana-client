package eu.europeana.api.client.config;

import java.io.File;

public interface ThumbnailAccessConfiguration {

	public String getBaseFolder();

	public String getDatasetsFolder();
	
	public File getDatasetFile(String dataset);
	
	public abstract String getImageFolder(String dataset);

	public String getBaseImageFolder();

	public int getImageMinSize();

}
