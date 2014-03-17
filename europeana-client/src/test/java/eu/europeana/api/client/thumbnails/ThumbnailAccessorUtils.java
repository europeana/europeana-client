package eu.europeana.api.client.thumbnails;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import eu.europeana.api.client.Api2QueryBuilder;
import eu.europeana.api.client.dataset.DatasetDescriptor;
import eu.europeana.api.client.dataset.EuClientDatasetUtil;

public class ThumbnailAccessorUtils  extends EuClientDatasetUtil{

	Api2QueryBuilder queryBuilder = new Api2QueryBuilder();

	public Api2QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	protected void writeThumbnailsToCsvFile(DatasetDescriptor dataset,
			Map<String, String> thumbnails, File file) throws IOException {

		// create parent dirs
		file.getParentFile().mkdirs();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writeCvsFileHeader(writer, dataset.getImageSetName(), thumbnails,
				dataset.getClassifications());

		int count = 0;
		for (Entry<String, String> thumbnail : thumbnails.entrySet()) {

			writer.write(thumbnail.getKey());
			writer.write(";");
			writer.write(thumbnail.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	/**
	 * use {@link #writeThumbnailsToCsvFile(DatasetDescriptor, Map, File)}
	 * instead
	 * 
	 * @param imageSet
	 * @param thumbnails
	 * @param classifications
	 * @param file
	 * @throws IOException
	 */
	@Deprecated
	private void writeThumbnailsToCsvFile(String imageSet,
			Map<String, String> thumbnails, String[] classifications, File file)
			throws IOException {

		// create parent dirs
		file.getParentFile().mkdirs();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writeCvsFileHeader(writer, imageSet, thumbnails, classifications);

		int count = 0;
		for (Entry<String, String> thumbnail : thumbnails.entrySet()) {

			writer.write(thumbnail.getKey());
			writer.write(";");
			writer.write(thumbnail.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	protected void writeCvsFileHeader(BufferedWriter writer, String imageSet,
			Map<String, String> thumbnails, String[] classifications)
			throws IOException {

		writer.write("#");
		writer.write(imageSet);
		writer.write("; ");
		writer.write("" + thumbnails.size());
		writer.write("; ");
		if (classifications != null) {
			for (int i = 0; i < classifications.length; i++) {
				writer.write(classifications[i]);
				writer.write("; ");
			}
		}
		writer.write("\n");

	}

	/**
	 * use {@link #getCollectionCsvFile(DatasetDescriptor)} instead
	 */
	@Deprecated
	private File getCollectionCsvFile(String imageSet, String collectionName) {
		String fileName = getCollectionsCvsFolder() + imageSet + "_"
				+ encode(collectionName) + ".csv";
		return new File(fileName);
	}

	protected File getCollectionCsvFile(DatasetDescriptor dataset) {
		String fileName = getCollectionsCvsFolder() + dataset.getImageSetName()
				+ "_" + encode(dataset.getCollectionName()) + ".csv";
		return new File(fileName);
	}

	protected String getCollectionsCvsFolder() {
		return "/";
	}

	private String encode(String collectionName) {

		if (collectionName == null)
			return null;

		return collectionName.replace('*', 'X');

	}

}
