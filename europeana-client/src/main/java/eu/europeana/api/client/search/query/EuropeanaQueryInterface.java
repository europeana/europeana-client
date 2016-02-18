package eu.europeana.api.client.search.query;

import java.io.UnsupportedEncodingException;
import java.util.List;

import eu.europeana.api.client.connection.EuropeanaConnection;

/**
 * Standard interface for Europeana queries.
 *
 * @author Cosmin Coman
 * @version 1.0
 * 
 * This inferface provides the basic functionality for
 * general queries to the Europeana database. It defines
 * methods for querying URLs and retrieving search terms
 * of a query.
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
     * @param connection: defines the used Europeana connection object.
     * @return string representing the query URL of a search.
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection) throws UnsupportedEncodingException;

    /**
     * Returns the full Europeana query url
     * 
     * @param connection: defines the used Europeana connection object.
     * @param offset: The item in the search results to start with; first item is 1 [default = 1].
     * @return string representing the query URL of a search.
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection, long offset) throws UnsupportedEncodingException;
    
    /**
     * Returns the full Europeana query url
     * 
     * @param connection: defines the used Europeana connection object.
     * @param limit: The number of records to return; the maximum value is 100 [default = 12].
     * @param offset: The item in the search results to start with; first item is 1 [default = 1].
     * @return string representing the query URL of a search.
     * @throws UnsupportedEncodingException 
     */
    public String getQueryUrl(EuropeanaConnection connection, long limit, long offset) throws UnsupportedEncodingException;
    
    
    /**
     * Returns the full Europeana query url employing the cursor
     * 
     * @param connection
     * @param cursor
     * @param rows
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getQueryUrl(EuropeanaConnection connection, String cursor, int rows) throws UnsupportedEncodingException;
    
    
    /**
     * Retrieves the used query type.
     * 
     * @return used query type.
     */
    public String getType();

    /**
     * Sets the used query type.
     * 
     * @param type: used query type.
     */
    public void setType(String type);
    
    /**
     * Sets the terms for a query.
     * 
     * @param what: terms for the query.
     */
    public void setWhatTerms(String what);

    /**
     * Sets general query terms.
     * 
     * @param generalTerms: string containing general terms.
     */
	public void setGeneralTerms(String generalTerms);

	/**
	 * Sets the creator of the query.
	 * 
	 * @param creator: string containing query creator.
	 */
	public void setCreator(String creator);

	/**
	 * Sets the provider of a query.
	 * 
	 * @param provider: string containing the provider.
	 */
	public void setProvider(String provider);
	
	/**
	 * Sets the data provider of the query.
	 * 
	 * @param dataProvider: string containing the data provider.
	 */
	public void setDataProvider(String dataProvider);

	/**
	 * @deprecated use {@link #getSubQueries()}
	 * @return
	 */
	public abstract String getWholeSubQuery();

	/**
	 * @deprecated use {@link #addSubQuery(SubQuery)}
	 * @return
	 */
	public abstract void setWholeSubQuery(String wholeSubQuery);

	/**
	 * Subqueries allow usage of search terms in specific search fields
	 * @param subQuery
	 */
	public void addSubQuery(SubQuery subQuery);

	/**
	 * Subqueries allow usage of search terms in specific search fields
	 * @param subQuery
	 */
	public List<SubQuery> getSubQueries();
	
	public String getProfile();

	public void setProfile(String profile);
}
