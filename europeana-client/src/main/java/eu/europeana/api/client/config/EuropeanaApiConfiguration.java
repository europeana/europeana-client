package eu.europeana.api.client.config;

public interface EuropeanaApiConfiguration {

	public static final String REPRESENTATION_PREVIEW = "preview";

	public abstract String getRecordUri();

	public abstract String getSearchUri();

	public abstract String getApiKey();
	
	public abstract String getEuropeanaUri();

	public abstract String getSearchUrn();
	
	public String getMetadataFolder();
	
	public String getJsonMetadataFile(String id, String metadataFolder, String representation);

}
