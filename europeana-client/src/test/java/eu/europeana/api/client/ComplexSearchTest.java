package eu.europeana.api.client;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import eu.europeana.api.client.EuropeanaComplexQuery;
import eu.europeana.api.client.adv.EuropeanaOperand;
import eu.europeana.api.client.adv.EuropeanaSearchTerm;
import eu.europeana.api.client.connection.EuropeanaConnection;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;
import eu.europeana.api.common.EuropeanaFields;
import eu.europeana.api.common.EuropeanaOperators;

public class ComplexSearchTest {

	@Test
	public void testComplexSearch() throws IOException{
		
		 long ms0 = System.currentTimeMillis();

         //build a complex query
         EuropeanaOperand opA = new EuropeanaOperand("Shakespeare");
         EuropeanaOperand opB = new EuropeanaOperand("William");
         EuropeanaOperand opC = new EuropeanaOperand("poetry");
         EuropeanaOperand opSimple = new EuropeanaOperand("dune");
         EuropeanaOperand opComplex = new EuropeanaOperand(EuropeanaOperators.AND, opA, opB);
         opComplex.addOperand(EuropeanaOperators.AND, opC);
         EuropeanaOperand opComplex2 = new EuropeanaOperand(EuropeanaOperators.OR, opComplex, opSimple);
         EuropeanaSearchTerm stSimple = new EuropeanaSearchTerm(EuropeanaFields.TITLE, opComplex2);
         EuropeanaSearchTerm stComplex = new EuropeanaSearchTerm(EuropeanaFields.TITLE, opSimple);
         stComplex.addSearchTerm(EuropeanaOperators.OR, stSimple);
         
         //OR A QUICK AND SIMPLE SEARCH:
         //stComplex = new EuropeanaSearchTerm(EuropeanaFields.CREATOR, "eminescu");
         EuropeanaComplexQuery europeanaQuery = new EuropeanaComplexQuery(stComplex);
         //set query type
         europeanaQuery.setType(EuropeanaComplexQuery.TYPE.TEXT);
         
         //invoke the search api
         EuropeanaConnection europeanaConnection = new EuropeanaConnection();
         final int FECTHED_RESULTS_COUNT = 20;
		EuropeanaApi2Results res = europeanaConnection.search(europeanaQuery, FECTHED_RESULTS_COUNT, 0);
         
         long t = System.currentTimeMillis() - ms0;
         System.out.println("*** Response time (client + server processing): " + (t / 1000d) + " seconds");
         System.out.println("Results: " + res.getItemCount() + " / " + res.getTotalResults());
         
         //Check results
         //verify the size of fetched results
         assertTrue(FECTHED_RESULTS_COUNT == res.getItemCount());
         //verify results count
         assertTrue(FECTHED_RESULTS_COUNT < res.getTotalResults());
         
         //display results
         if (res.getItemCount() > 0) {
             List<EuropeanaApi2Item> items = res.getAllItems();
             for (int i = 0; i < items.size(); i++) {
                 EuropeanaApi2Item item = items.get(i);
                 System.out.println();
                 System.out.println("**** " + (i + 1));
                 System.out.println("Title: " + item.getTitle());
                 System.out.println("Europeana URL: " + item.getObjectURL());
                 System.out.println("Type: " + item.getType());
                 System.out.println("Creator: " + item.getDcCreator());
                 System.out.println("Thumbnail: " + item.getEdmPreview());
                 System.out.println("Data provider: "+ item.getDataProvider());
                 System.out.println("Id: "+ item.getId());
                 System.out.println("Guid: "+ item.getGuid());
                 
             }
         }
	}
}
