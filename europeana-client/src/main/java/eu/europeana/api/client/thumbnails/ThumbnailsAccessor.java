/*
 * ThumbnailsAccessor.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.europeana.api.client.thumbnails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.config.ThumbnailAccessConfiguration;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.exception.EuropeanaClientException;
import eu.europeana.api.client.metadata.MetadataAccessor;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.model.search.EuropeanaObject;
import eu.europeana.api.client.search.query.EuropeanaQueryInterface;

/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of thumbnails of
 * the Europeana items.
 * 
 * @author Andres Viedma
 * @version 1.0
 * 
 *          The class provides functionality to copy thumbnails and write a
 *          thumbnail to a folder.
 */
public class ThumbnailsAccessor extends MetadataAccessor{
	boolean filterThumbnails = false;
	final int PLACEHOLDER_SIZE = 3583;
	final int LOW_QUALITY_SIZE = 1000;
	
	/**
	 * Default constructor.
	 */
	public ThumbnailsAccessor() {
		this(null);
	}

	/**
	 * Constructor with predefined europeana client.
	 * 
	 * @param europeanaClient
	 *            : EuropeanaApi2Client object defining the used europeana
	 *            client.
	 */
	public ThumbnailsAccessor(EuropeanaApi2Client apiClient) {
		super(null, apiClient);
	}

	/**
	 * The copyThumbnails method extracts a thumbnail image from the search
	 * results and stores it in the provided directory.
	 * 
	 * @param search
	 *            : EuropeanaQueryInterface object representing the search.
	 * @param dir
	 *            : A directory where the thumbnail will be saved.
	 * @param maxResults
	 *            : The maximal amount of result retrieved by the search.
	 * @return a list of ids from items which provided valid thumbnails.
	 * @throws IOException
	 * @throws EuropeanaApiProblem
	 */
	public List<String> copyThumbnails(EuropeanaQueryInterface search,
			File dir, int maxResults) throws IOException, EuropeanaApiProblem {

		//TODO: add thumbnail size as parameter
		
		EuropeanaApi2Results res = europeanaClient.searchApi2(search,
				maxResults, 0);

		List<EuropeanaApi2Item> items = res.getAllItems();
		List<String> itemsOk = new ArrayList<String>(items.size());

		EuropeanaApi2Item item;
		String id;
		String thumbnailUrl;

		for (int i = 0; i < items.size(); i++) {

			if (i % 10 == 0)
				log.info("Copying thumbnail: " + (i + 1) + " / " + items.size());

			item = (EuropeanaApi2Item) items.get(i);
			id = item.getId();

			if (item.getEdmPreview() == null || item.getEdmPreview().isEmpty()) {
				log.info("Skip item without thumbnail: " + id);
				continue;
			}

			thumbnailUrl = item.getEdmPreview().get(0);

			boolean fileOk = writeThumbnailToFolder(id, thumbnailUrl, dir);

			if (fileOk)
				itemsOk.add(id);

		}

		log.info("Copying finished OK, thumbnails generated: " + itemsOk.size());
		return itemsOk;
	}

	/**
	 * The method writeThumbnailToFolder creates an output stream and writes the
	 * binary content of the thumbnail to a new file in a specified folder.
	 * 
	 * @param id
	 *            : ID string of the image file.
	 * @param thumbnailUrl
	 *            : URL of the thumbnail.
	 * @param imageFolder
	 *            : Folder where the resulting images are to be saved.
	 * @return boolean indicating wheter the operation has been successful.
	 */
	public boolean writeThumbnailToFolder(String id, String thumbnailUrl,
			File imageFolder) {

		FileOutputStream out = null;
		try {
			// write thumbnail to output folder
			File imageFile = getThumbnailAccessConfiguration()
					.getImageFile(imageFolder, id);
			if (isSkipExistingFiles() && imageFile.exists()) {
				// skip file
				incrementSkippedItems();
			} else {
				// download file
				out = createOutputStream(imageFile);

				//String mime = "image";
				String mime = null;
				this.http.silentWriteURLContent(thumbnailUrl, out, mime);
			}
		} catch (IOException e) {
			log.warn("Cannot write file to disk!", e);

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					log.debug(
							"Cannot close outputStream after writing file to disk!",
							e);
				}
			}

		}

		// verify file on disk
		File imageFile = getThumbnailAccessConfiguration().getImageFile(imageFolder, id);
		
		if(!imageFile.exists()){
			return false;
		}else	if (imageFile.length() < getMinThumbailSize()) {
			// if not correctly written to disk
			imageFile.delete();
			log.debug("Rejected image smaller than image.min.size. Image size smaller than "
			+getMinThumbailSize() + "kB! " + thumbnailUrl );
			return false;
		} else if(filterThumbnails && imageFile.length() < getImageLowQualitySize()){
			imageFile.delete();
			log.debug("rejected image too small < 1MB: " + thumbnailUrl);
			return false;
		} else if(filterThumbnails && imageFile.length() == PLACEHOLDER_SIZE){
			imageFile.delete();
			log.debug("rejected placeholder image: " + thumbnailUrl);
			return false;
		} 

		return true;
	}

	protected int getImageLowQualitySize() {
		return LOW_QUALITY_SIZE;
	}

	protected int getMinThumbailSize() {
		return getThumbnailAccessConfiguration().getImageMinSize();	
	}

	protected ThumbnailAccessConfiguration getThumbnailAccessConfiguration() {
		return (ThumbnailAccessConfiguration)getConfiguration();
	}

	

	/**
	 * The copyThumbnails method iterates the provided thumbnails map and
	 * filters the items that are skipped due to failed write operations in the
	 * specified folder.
	 * 
	 * @param thumbnailsMap
	 *            : map of thumbnails to be searched.
	 * @param imageFolder
	 *            : folder where to save found thumbnails.
	 * @return the list of items that were not saved on disk.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<String> copyThumbnails(Map<String, String> thumbnailsMap,
			File imageFolder) throws FileNotFoundException, IOException {

		List<String> skippedItems = new ArrayList<String>();

		int counter = 0;
		String thumbnailUrl = null;

		for (Map.Entry<String, String> thumbnail : thumbnailsMap.entrySet()) {

			thumbnailUrl = thumbnail.getValue();
			if (thumbnailUrl.indexOf(";") > 0)
				thumbnailUrl = thumbnailUrl.split(";")[0];

			if (!writeThumbnailToFolder(thumbnail.getKey(), thumbnailUrl,
					imageFolder))
				skippedItems.add(thumbnail.getKey());

			counter++;
			if (counter % 1000 == 0)
				System.out.println("Progress: items processed " + counter);
		}

		return skippedItems;
	}

	/**
	 * This method uses the Europeana API to retrieve the URL of the Thumbnail
	 * and copies it to the given folder see {@link #getImageFile(File, String)}
	 * 
	 * @param europeanaId
	 * @param imageFolder
	 * @return the URL used to access the Thumbnail from europeana
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws EuropeanaClientException
	 */
	public String copyThumbnail(String europeanaId, File imageFolder)
			throws FileNotFoundException, IOException, EuropeanaClientException {

		EuropeanaObject euObject = europeanaClient.getObject(europeanaId);
		String thumbnailUrl = euObject.getThumbnailLarge();

		if (!writeThumbnailToFolder(europeanaId, thumbnailUrl, imageFolder))
			throw new EuropeanaClientException(
					"Cannot write the thumbnail file for object: "
							+ europeanaId + " to folder: " + imageFolder);

		return thumbnailUrl;
	}

	
	public boolean isFilterThumbnails() {
		return filterThumbnails;
	}

	public void setFilterThumbnails(boolean filterThumbnails) {
		this.filterThumbnails = filterThumbnails;
	}

	
}
