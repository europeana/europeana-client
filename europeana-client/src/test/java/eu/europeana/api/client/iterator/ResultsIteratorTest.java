package eu.europeana.api.client.iterator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import eu.europeana.api.client.Api2Query;
import eu.europeana.api.client.connection.EuropeanaApi2Client;
import eu.europeana.api.client.result.EuropeanaApi2Item;
import eu.europeana.api.client.result.EuropeanaApi2Results;

/**
 * Test of EuropeanaConnection.iterateResults
 * 
 * @author Andres Viedma Pelaez
 *
 * @author Sergiu Gordea
 * This was used with the API 1 client. The API V2 client is implementing the retrieval of large datasets in one query. I did not notice any performce problems even when retrieving hundreds of thousend of objects.   
 */
@Deprecated
@Ignore //tests are desactivated as the underlying code don't apply to apiV2.
public class ResultsIteratorTest {
    
    @Test
    public void testIterator() throws IOException {
        final int maxResTest = 200;
        EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
        Api2Query europeanaQuery = new Api2Query();
        europeanaQuery.setCreator("picasso");
        
        // 200 Results by iteration
        System.out.println("* Running iterator");
        Iterator<EuropeanaApi2Item> it = europeanaClient.iterateResults (europeanaQuery, 1);
        List<EuropeanaApi2Item> l0 = new ArrayList<EuropeanaApi2Item> (maxResTest);
        int num = 0;
        while ((num < maxResTest) && it.hasNext()) {
            l0.add (it.next());
            num++;
        }
        System.out.println("OK");

        // 200 Results by direct query
        System.out.println("* Running direct queries");
        List<EuropeanaApi2Item> l1 = new ArrayList<EuropeanaApi2Item> (maxResTest);
        EuropeanaApi2Results euRes = europeanaClient.search (europeanaQuery, 100, 1);
        EuropeanaApi2Results euRes2 = europeanaClient.search (europeanaQuery, 100, 101);
        l1.addAll(euRes.getAllItems());
        l1.addAll(euRes2.getAllItems());
        System.out.println("OK");
        
        // Must be equal
        System.out.println("* Comparing results");
        assertEquals(l0.size(), l1.size());
        Gson gson = new Gson();
        String s0 = gson.toJson(l0);
        String s1 = gson.toJson(l1);
        assertEquals(s0, s1);
        System.out.println("OK");
    }

    @Test
    public void testBigLimit() throws IOException {
        final int maxResTest = 200;
        EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
        Api2Query europeanaQuery = new Api2Query();
        europeanaQuery.setCreator("picasso");
        
        // 200 Results by big limit
        System.out.println("* Running one query with big limit");
        EuropeanaApi2Results euRes0 = europeanaClient.search (europeanaQuery, 200, 1);
        List<EuropeanaApi2Item> l0 = new ArrayList<EuropeanaApi2Item> (maxResTest);
        l0.addAll(euRes0.getAllItems());
        System.out.println("OK");

        // 200 Results by direct query
        System.out.println("* Running two queries");
        List<EuropeanaApi2Item> l1 = new ArrayList<EuropeanaApi2Item> (maxResTest);
        EuropeanaApi2Results euRes = europeanaClient.search (europeanaQuery, 100, 1);
        EuropeanaApi2Results euRes2 = europeanaClient.search (europeanaQuery, 100, 101);
        l1.addAll(euRes.getAllItems());
        l1.addAll(euRes2.getAllItems());
        System.out.println("OK");
        
        // Must be equal
        System.out.println("* Comparing results");
        assertEquals(l0.size(), l1.size());
        Gson gson = new Gson();
        String s0 = gson.toJson(l0);
        String s1 = gson.toJson(l1);
        assertEquals(s0, s1);
        System.out.println("OK");
    }

}
