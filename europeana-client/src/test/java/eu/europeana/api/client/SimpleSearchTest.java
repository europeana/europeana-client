package eu.europeana.api.client;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import eu.europeana.api.client.adv.EuropeanaComplexQuery;

/**
 * Test Class generated from the SimpleSearch example
 * @author GordeaS
 *
 */
public class SimpleSearchTest {

	@Test
	public void testsimpleSearch() throws IOException{
		long ms0 = System.currentTimeMillis();

        //create the query object
		Api2Query europeanaQuery = new Api2Query();
        europeanaQuery.setCreator("picasso");
        europeanaQuery.setType(EuropeanaComplexQuery.TYPE.IMAGE);
        europeanaQuery.setNotProvider("Hispana");
        
        //perform search
        EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
        EuropeanaApi2Results res = europeanaClient.searchApi2(europeanaQuery, -1, 1);
        
        //print out response time
        long t = System.currentTimeMillis() - ms0;
        System.out.println("response time (client+server): " + (t / 1000d) + " seconds");

        //check item count
        final int DEFAULT_PAGE_SIZE = 12;
        assertTrue(res.getItemCount() ==  DEFAULT_PAGE_SIZE);
        
        //check total items. Should be more that one page for sure
        assertTrue(res.getTotalResults() >  DEFAULT_PAGE_SIZE);
        
        
        //print out search results
        System.out.println("Query: " + europeanaQuery.getSearchTerms());
        System.out.println("Query url: " + europeanaQuery.getQueryUrl(europeanaClient));
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
