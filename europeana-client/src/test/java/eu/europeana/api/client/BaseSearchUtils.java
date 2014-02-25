package eu.europeana.api.client;

import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

public class BaseSearchUtils {

	protected void printSearchResults(EuropeanaApi2Results res) {
		System.out.println("Results: " + res.getItemCount() + " / " + res.getTotalResults());
	    
	    int count = 0;
	    for (EuropeanaApi2Item item : res.getAllItems()) {
	    	 System.out.println();
	         System.out.println("**** " + (count++ + 1));
	         System.out.println("Title: " + item.getTitle());
	         System.out.println("Europeana URL: " + item.getObjectURL());
	         System.out.println("Type: " + item.getType());
	         System.out.println("Creator(s): " + item.getDcCreator());
	         System.out.println("Thumbnail(s): " + item.getEdmPreview());
	         System.out.println("Data provider: "
	                 + item.getDataProvider());
		}
	}

}
