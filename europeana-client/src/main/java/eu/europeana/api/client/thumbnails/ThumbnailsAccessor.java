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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.EuropeanaQueryInterface;
import eu.europeana.api.client.connection.EuropeanaApi2Client;
import eu.europeana.api.client.connection.HttpConnector;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of thumbnails of
 * the Europeana items.
 * 
 * @author Andres Viedma
 * @version 1.0
 * 
 * The class provides functionality to copy thumbnails and write a thumbnail to
 * a folder.
 */
public class ThumbnailsAccessor {
	private static final Log log = LogFactory.getLog(ThumbnailsAccessor.class);

	private HttpConnector http = new HttpConnector();
	EuropeanaApi2Client europeanaClient;
	
	public static int ERROR_POLICY_RETHROW = 1;
	public static int ERROR_POLICY_STOP = 5;
	public static int ERROR_POLICY_IGNORE = 9;
	public static int ERROR_POLICY_CONTINUE = 99;

	/**
	 * Default constructor.
	 */
	public ThumbnailsAccessor() {
	}

	/**
	 * Constructor with predefined europeana client.
	 * 
	 * @param europeanaClient: EuropeanaApi2Client object defining the used europeana client.
	 */
	public ThumbnailsAccessor(EuropeanaApi2Client europeanaClient) {
		this.europeanaClient = europeanaClient;
	}

	/**
	 * The copyThumbnails method extracts a thumbnail image from the 
	 * search results and stores it in the provided directory.
	 * 
	 * @param search: EuropeanaQueryInterface object representing the search.
	 * @param dir: A directory where the thumbnail will be saved.
	 * @param maxResults: The maximal amount of result retrieved by the search.
	 * @return a list of ids from items which provided valid thumbnails.
	 * @throws IOException
	 * @throws EuropeanaApiProblem
	 */
	public List<String> copyThumbnails(EuropeanaQueryInterface search,
			File dir, int maxResults) throws IOException, EuropeanaApiProblem {
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
	 * The method writeThumbnailToFolder creates an output stream
	 * and writes the binary content of the thumbnail to a new file
	 * in a specified folder.
	 * 
	 * @param id: ID string of the image file.
	 * @param thumbnailUrl: URL of the thumbnail.
	 * @param imageFolder: Folder where the resulting images are to be saved.
	 * @return boolean indicating wheter the operation has been successful.
	 */
	public boolean writeThumbnailToFolder(String id, String thumbnailUrl,
			File imageFolder){

		FileOutputStream out = null;
		try {
			// write thumbnail to output folder
			out = createOutputStream(imageFolder, id);
			
			String mime = "image";
			this.http.silentWriteURLContent(thumbnailUrl, out, mime);
		}catch(IOException e){
			log.trace("Cannot write file to disk!", e);
			
		} finally {
			if(out!= null){
				try{
					out.close();
				}catch(Exception e){
					log.debug("Cannot close outputStream after writing file to disk!", e);
				}
			}
				
		}

		// verify file on disk
		File imageFile = getImageFile(imageFolder, id);
		if (imageFile.length() < 100) {
			// if not correctly written to disk
			imageFile.delete();
			return false;
		} else if (imageFile.exists()) {
			return true;
		}

		return false;
	}

	/**
	 * Helper method for the creation of a file output stream.
	 *  
	 * @param imageFolder: folder where images are to be saved.
	 * @param id: id of spcefic image file.
	 * @return FileOutputStream object prepared to store images.
	 * @throws FileNotFoundException
	 */
	protected FileOutputStream createOutputStream(File imageFolder, String id)
			throws FileNotFoundException {

		File imageFile = getImageFile(imageFolder, id);
		imageFile.getParentFile().mkdirs();

		return new FileOutputStream(imageFile);
	}

	/**
	 * Helper method to get an image file in a certain directory.
	 * 
	 * @param dir: directory where to search the image file.
	 * @param id: id of the image file.
	 * @return File object representing the found image.
	 */
	File getImageFile(File dir, String id) {
		String fileName = id + ".jpg";
		File imageFile = new File(dir, fileName);
		return imageFile;
	}

	/**
	 * The copyThumbnails method iterates the provided thumbnails map and
	 * filters the items that are skipped due to failed write operations 
	 * in the specified folder.
	 * 
	 * @param thumbnailsMap: map of thumbnails to be searched.
	 * @param imageFolder: folder where to save found thumbnails.
	 * @return the list of items that were not saved on disk.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public List<String> copyThumbnails(Map<String, String> thumbnailsMap,
			File imageFolder) throws FileNotFoundException, IOException {

		List<String> skippedItems = new ArrayList<String>();
		
		int counter = 0;
		for (Map.Entry<String, String> thumbnail : thumbnailsMap.entrySet()) {
			if (!writeThumbnailToFolder(thumbnail.getKey(),
					thumbnail.getValue(), imageFolder))
				skippedItems.add(thumbnail.getKey());
			
			counter++;
			if(counter % 1000 == 0)
				System.out.println("Progress: items processed " + counter);
		}
		
		return skippedItems;
	}
}
