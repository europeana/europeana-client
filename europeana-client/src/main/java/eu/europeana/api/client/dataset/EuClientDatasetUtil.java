package eu.europeana.api.client.dataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.config.ThumbnailAccessConfiguration;

public class EuClientDatasetUtil extends BaseDatasetUtil {

	protected final int DEFAULT_BLOCK_SIZE = 1000;
	public final int POLICY_SKIP_EXISTING = 0;
	public final int POLICY_OVERWRITE_FILE = 1;
	public final int POLICY_APPEND_TO_FILE = 2;

	protected Logger log = Logger.getLogger(getClass());

	@Override
	public File getDataSetFile(boolean urls) {
		// TODO: add support for dataset.urls.cvs when needed
		return getConfiguration().getDatasetFile(getDataset());
	}

	public ThumbnailAccessConfiguration getConfiguration() {
		return (ThumbnailAccessConfiguration) ClientConfiguration.getInstance();
	}

	protected void writeMapToCsvFile(DatasetDescriptor dataset,
			Map<String, String> contentUrlMap, File file, int fileWritePolicy)
			throws IOException {
		// create parent dirs
		file.getParentFile().mkdirs();

		if (file.exists() && POLICY_SKIP_EXISTING == fileWritePolicy) {
			log.warn("Existing files will not be overwritten, change write policy! "
					+ file);
			return;
		}

		boolean append = (POLICY_APPEND_TO_FILE == fileWritePolicy);
		if (append && file.exists())
			log.warn("Appending data to existing files! " + file);

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));

		if (dataset != null) {
			writeCvsFileHeader(writer, dataset.getImageSetName(),
					contentUrlMap, dataset.getClassifications());
		}

		int count = 0;
		for (Entry<String, String> contentUrl : contentUrlMap.entrySet()) {

			writer.write(contentUrl.getKey());
			writer.write(";");
			writer.write(contentUrl.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	public void writeCvsFileHeader(BufferedWriter writer, String subset,
			Map<String, String> thumbnails, String[] classifications)
			throws IOException {

		writeCvsFileHeader(writer, subset, thumbnails.size(), classifications);

	}

	protected void writeCvsFileHeader(BufferedWriter writer, String subsetName,
			int thumbnailsSize, String[] classifications) throws IOException {

		writer.write("#");
		writer.write(subsetName);
		writer.write("; ");
		writer.write("" + thumbnailsSize);
		writer.write("; ");
		if (classifications != null) {
			for (int i = 0; i < classifications.length; i++) {
				writer.write(classifications[i]);
				writer.write("; ");
			}
		}
		writer.write("\n");

	}
	
	
	protected void writeContentMapToFile(Map<String, String> contentMap, File file) 
			throws IOException {
		Properties properties = new Properties();

		for (Map.Entry<String,String> entry : contentMap.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}
		
		FileOutputStream fos = new FileOutputStream(file);

		properties.store(fos, null);
	}

	
	protected String readStringFromFile(String filename) throws IOException {
		File file = new File(filename);
		String string = FileUtils.readFileToString(file);
		return string;
	}
	
	
	protected File getCollectionCsvFile(DatasetDescriptor dataset) {
		String fileName = getCollectionsCvsFolder() + dataset.getImageSetName()
				+ "_" + encode(dataset.getCollectionName()) + ".csv";
		return new File(fileName);
	}

	protected String getCollectionsCvsFolder() {
		return getConfiguration().getBaseFolder() + "/collections";
	}

	protected String encode(String collectionName) {

		if (collectionName == null)
			return null;

		return collectionName.replace('*', 'X');

	}

}
