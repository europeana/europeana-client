package eu.europeana.api.client.config;

import java.io.File;

public interface ThumbnailAccessConfiguration {

	public String getBaseFolder();

	public String getDatasetsFolder();
	
	public File getDatasetFile(String dataset);
	
	public abstract String getImageFolder(String dataset);

	public String getBaseImageFolder();

	public int getImageMinSize();
	
	/**
	 * Helper method to get an image file in a certain directory.
	 * 
	 * @param dir
	 *            : directory where to search the image file.
	 * @param id
	 *            : id of the image file.
	 * @return File object representing the found image.
	 */
	public File getImageFile(File dir, String id);

}
