package eu.europeana.api.client;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Cosmin Coman
 */
public interface EuropeanaQueryInterface {

    /**
     * Returns the Europeana search terms query string
     * 
     * @return the searchTerms argument of the url
     */
    public String getSearchTerms();

    /**
     * Returns the full Europeana query url
     * 
     * @param connection
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection) throws UnsupportedEncodingException;

    /**
     * Returns the full Europeana query url
     * 
     * @param connection
     * @param offset
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection, long offset) throws UnsupportedEncodingException;
    
    /**
     * Returns the full Europeana query url
     * 
     * @param connection
     * @param limit The number of records to return; the maximum value is 100 [default = 12].
     * @param offset The item in the search results to start with; first item is 1 [default = 1].
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection, long limit, long offset) throws UnsupportedEncodingException;
    
    public String getType();

    public void setType(String type);
}
