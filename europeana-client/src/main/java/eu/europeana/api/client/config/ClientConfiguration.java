package eu.europeana.api.client.config;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import eu.europeana.api.client.exception.TechnicalRuntimeException;

public class ClientConfiguration implements EuropeanaApiConfiguration,
		ThumbnailAccessConfiguration {

	protected static final String EUROPEANA_CLIENT_PROPERTIES_FILE = "/europeana-client.properties";
	// API CONFIG KEYS
	public static final String PROP_EUROPEANA_API_KEY = "europeana.api.key";
	public static final String PROP_EUROPEANA_API_URI = "europeana.api.uri";
	public static final String PROP_EUROPEANA_SEARCH_URN = "europeana.search.urn";
	public static final String PROP_EUROPEANA_RECORD_URN = "europeana.record.urn";
	// Thumbnail Access KEYS
	public static final String PROP_BASE_FOLDER_KEY = "europeana.client.base.folder";
	public static final String PROP_DATASETS_FOLDER_KEY = "europeana.client.datasets.folder";

	// static configs
	public static final String DATASET_FILE_EXTENSION = ".csv";

	// local attributes
	private static Properties properties = null;
	private static ClientConfiguration singleton;

	/**
	 * Hide the default constructor
	 */
	private ClientConfiguration() {
	}

	/**
	 * Accessor method for the singleton
	 * 
	 * @return
	 */
	public static synchronized EuropeanaApiConfiguration getInstance() {
		if (singleton == null) {
			singleton = new ClientConfiguration();
			singleton.loadProperties();
		}
		return singleton;
	}

	/**
	 * Laizy loading of configuration properties
	 */
	public synchronized void loadProperties() {
		try {
			properties = new Properties();
			InputStream resourceAsStream = getClass().getResourceAsStream(
					EUROPEANA_CLIENT_PROPERTIES_FILE);
			if (resourceAsStream != null)
				getProperties().load(resourceAsStream);
			else
				throw new TechnicalRuntimeException(
						"No properties file found in classpath! "
								+ EUROPEANA_CLIENT_PROPERTIES_FILE);

		} catch (Exception e) {
			throw new TechnicalRuntimeException(
					"Cannot read configuration file: "
							+ EUROPEANA_CLIENT_PROPERTIES_FILE, e);
		}

	}

	/**
	 * provides access to the configuration properties. It is not recommended to
	 * use the properties directly, but the
	 * 
	 * @return
	 */
	Properties getProperties() {
		return properties;
	}

	/**
	 * 
	 * @return the name of the file storing the client configuration
	 */
	String getConfigurationFile() {
		return EUROPEANA_CLIENT_PROPERTIES_FILE;
	}

	/**
	 * This method provides access to the API key defined in the configuration
	 * file
	 * 
	 * @see PROP_EUROPEANA_API_KEY
	 * 
	 * @return
	 */
	@Override
	public String getApiKey() {
		return getProperties().getProperty(PROP_EUROPEANA_API_KEY);
	}

	/**
	 * This method provides access to the search uri value defined in the
	 * configuration file
	 * 
	 * @see PROP_EUROPEANA_SEARCH_URN
	 * 
	 * @return
	 */
	@Override
	public String getSearchUri() {
		return (getEuropeanaUri() + getSearchUrn());
	}

	/**
	 * This method provides access to the search urn value defined in the
	 * configuration file
	 * 
	 * @see PROP_EUROPEANA_SEARCH_URN
	 */
	@Override
	public String getSearchUrn() {
		return getProperties().getProperty(
				PROP_EUROPEANA_SEARCH_URN);
	}

	/**
	 * This method provides access to the record uri value defined in the
	 * configuration file
	 * 
	 * @see PROP_EUROPEANA_RECORD_URN
	 * 
	 * @return
	 */
	@Override
	public String getRecordUri() {
		return (getEuropeanaUri() + getProperties().getProperty(
				PROP_EUROPEANA_RECORD_URN));
	}

	@Override
	public String getDatasetsFolder() {
		return (getProperties().getProperty(PROP_DATASETS_FOLDER_KEY));
	}

	@Override
	public String getImageFolder(String dataset) {
		String ret = getBaseFolder();
		final String separator = "/";
		if (!ret.endsWith(separator))
			ret += separator;

		ret += ("image" + separator);
		ret += dataset;

		return ret;
	}

	@Override
	public String getBaseFolder() {
		return (getProperties().getProperty(PROP_BASE_FOLDER_KEY));
	}

	@Override
	public File getDatasetFile(String dataset) {
		return new File(getDatasetsFolder(), dataset + DATASET_FILE_EXTENSION);
	}

	@Override
	public String getEuropeanaUri() {
		return getProperties().getProperty(PROP_EUROPEANA_API_URI);
	}

}
