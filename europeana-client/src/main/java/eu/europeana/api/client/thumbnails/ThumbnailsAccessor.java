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
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of thumbnails of
 * the Europeana items.
 * 
 * @author Andres Viedma
 */
public class ThumbnailsAccessor {
	private static final Log log = LogFactory.getLog(ThumbnailsAccessor.class);

	private HttpConnector http = new HttpConnector();
	EuropeanaApi2Client europeanaClient;

	public ThumbnailsAccessor() {
	}

	public ThumbnailsAccessor(EuropeanaApi2Client europeanaClient) {
		this.europeanaClient = europeanaClient;
	}

	public List<String> copyThumbnails(EuropeanaQueryInterface search,
			File dir, int maxResults) throws IOException {
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

	protected FileOutputStream createOutputStream(File imageFolder, String id)
			throws FileNotFoundException {

		File imageFile = getImageFile(imageFolder, id);
		imageFile.getParentFile().mkdirs();

		return new FileOutputStream(imageFile);
	}

	File getImageFile(File dir, String id) {
		String fileName = id + ".jpg";
		File imageFile = new File(dir, fileName);
		return imageFile;
	}

	/**
	 * 
	 * @param thumbnailsMap
	 * @param imageFolder
	 * @return the list of items that were not saved on disk
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public List<String> copyThumbnails(Map<String, String> thumbnailsMap,
			File imageFolder) throws FileNotFoundException, IOException {

		List<String> skippedItems = new ArrayList<String>();

		for (Map.Entry<String, String> thumbnail : thumbnailsMap.entrySet()) {
			if (!writeThumbnailToFolder(thumbnail.getKey(),
					thumbnail.getValue(), imageFolder))
				skippedItems.add(thumbnail.getKey());
		}
		
		return skippedItems;
	}
}
