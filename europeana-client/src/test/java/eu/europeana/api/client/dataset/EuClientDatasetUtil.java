package eu.europeana.api.client.dataset;

import java.io.File;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.config.ThumbnailAccessConfiguration;

public class EuClientDatasetUtil extends BaseDatasetUtil{

	@Override
	public File getDataSetFile(boolean urls) {
		//TODO: add support for dataset.urls.cvs when needed
		return getConfiguration().getDatasetFile(getDataset());
	}

	public ThumbnailAccessConfiguration getConfiguration(){
		return (ThumbnailAccessConfiguration)ClientConfiguration.getInstance();
	} 
}
