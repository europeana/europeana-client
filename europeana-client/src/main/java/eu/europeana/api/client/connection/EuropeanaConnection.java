/*
 * EuropeanaConnection.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

import eu.europeana.api.client.adv.EuropeanaComplexQuery;
import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.util.BlockIterator;
import eu.europeana.api.client.util.BlockIterator.BlockLoader;

/**
 * The EuropeanaConnection class is the main access point to the Europeana API,
 * allowing to make querys and obtain the results in different formats.
 *
 * @author Andres Viedma Pelaez
 * @author Sergiu Gordea
 */
public class EuropeanaConnection {

	private static final Log log = LogFactory.getLog(EuropeanaConnection.class);
	
	private static final int MAX_RESULTS_PAGE = 100;
	
    private String apiKey;
    //v1 uri
    //private String europeanaUri = "http://api.europeana.eu/api/opensearch.json";
    //v2 uri
    //private String europeanaUri = "http://www.europeana.eu/api/v2/search.json";
    private String europeanaSearchUri = "";
    private HttpConnector http = new HttpConnector();

    /**
     * Create a new connection to the Europeana API.
     *
     * @param apiKey API Key provided by Europeana to access the API
     */
    public EuropeanaConnection(String europeanaSearchUri, String apiKey) {
        this.apiKey = apiKey;
        this.europeanaSearchUri = europeanaSearchUri;
    }

    public EuropeanaConnection() {
    	this(ClientConfiguration.getInstance().getSearchUri(), ClientConfiguration.getInstance().getApiKey());
    	
    }
    
    /**
     * Execute a query to Europeana returning a limited set of results
     *
     * @param search
     * @param offset
     * @return The results as a EuropeanaResults object
     * @throws IOException
     */
    public EuropeanaApi2Results search(EuropeanaQueryInterface search, long offset) throws IOException {
        String json = this.searchJsonPage(search, 12, offset);

        // Load results object from JSON
        Gson gson = new Gson();
        EuropeanaApi2Results res = gson.fromJson(json, EuropeanaApi2Results.class);

        return res;
    }

    /**
     * Execute a query to Europeana returning a limited set of results. If the
     * limit is greater than the API allowed max results (100), it will do
     * several consecutive requests.
     *
     * @param searchQuery
     * @param limit
     * @param offset
     * @return
     * @throws IOException
     */
    public EuropeanaApi2Results search(EuropeanaQueryInterface searchQuery, long limit, long offset) throws IOException {
        if (limit < 0)  limit = MAX_RESULTS_PAGE;
        
        Gson gson = new Gson();
        EuropeanaApi2Results res = null;
        do {
            String json = this.searchJsonPage(searchQuery, limit, offset);
            EuropeanaApi2Results res2 = gson.fromJson(json, EuropeanaApi2Results.class);
            if (res == null) {
                res = res2;
            } else {
                res.acumulate(res2);
            }
            offset += res2.getItemCount();
            
        } while (res.getItemCount() < limit);
        
        res.limitResults((int) limit);
        return res;
    }

    /**
     * Execute a query to Europeana returning a limited set of results
     *
     * @param searchQuery
     * @return The results as a EuropeanaResults object
     * @throws IOException
     */
    public EuropeanaApi2Results search(EuropeanaQueryInterface searchQuery) throws IOException {
    	EuropeanaApi2Results results = search(searchQuery, -1, EuropeanaComplexQuery.DEFAULT_OFFSET);
        return results;
    }

    /**
     * Execute a query to Europeana and return one results page as JSON
     *
     * @param search
     * @param limit
     * @param offset
     * @return The results as a JSON string
     * @throws IOException
     */
    public String searchJsonPage(EuropeanaQueryInterface search, long limit, long offset) throws IOException {
        String url = search.getQueryUrl(this, limit, offset);
        String jSON = this.getJSONResult(url);
        return jSON;
    }

    /**
     * Returns an Iterator over the results of a query to Europeana.
     * There's no limit in the size of the Iterator, so it can be potentially
     * very large and need a big number of requests to the API, so this method
     * should be used carefully.
     * 
     * @throws TechnicalRuntimeException in case of an error in a call to Europeana
     */
    public Iterator<EuropeanaApi2Item> iterateResults (EuropeanaQueryInterface searchQuery, long offset) throws IOException {

        final EuropeanaQueryInterface qFinal = searchQuery;
        final long offFinal = offset;
        
        return new BlockIterator<EuropeanaApi2Item>(
                new BlockLoader<EuropeanaApi2Item>() {
                    private EuropeanaApi2Results firstResults = null;
                    
                    @Override
                    public List<EuropeanaApi2Item> loadBlock(long offset) {
                        if ((offset == offFinal - 1) && (firstResults != null)) {
                            return firstResults.getAllItems();
                        } else {
                            try {
                                EuropeanaApi2Results res = search (qFinal, 100, offset + 1);
                                if (offset == offFinal - 1)  firstResults = res;
                                return res.getAllItems();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public long getTotalRecords() {
                        loadBlock(offFinal - 1);
                        return firstResults.getTotalResults();
                    }
        });
    }

         

    String getJSONResult(String url) throws IOException {
        log.trace("Call to Europeana API: " + url);
        String res = http.getURLContent(url);
        return res;
    }

    /**
     * Returns the Europeana API URI for JSON calls
     *
     * @return
     */
    public String getEuropeanaUri() {
        return europeanaSearchUri;
    }

    /**
     * Modifies the Europeana API URI for JSON calls. The default value points
     * to the "http://api.europeana.eu/api/opensearch.json"
     *
     * @param europeanaUri
     */
    public void setEuropeanaUri(String europeanaUri) {
        this.europeanaSearchUri = europeanaUri;
    }

    /**
     * @return the Europeana apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the Europeana apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    //TODO: imrove the following code or remove it
//    public String getRecordGuid(String id) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("http://www.europeana.eu/portal/record").append(id).append(".html?utm_source=api&utm_medium=api&utm_campaign=").append(apiKey);
//        return sb.toString();
//    }
}
