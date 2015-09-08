/*
 * EuropeanaQuery.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client.search.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.api.client.connection.EuropeanaConnection;
import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.search.common.EuropeanaOperators;

/**
 * The EuropeanaQuery is an encapsulated query to a EuropeanaConnection object.
 *
 * @author Andres Viedma Pelaez
 * @author Sergiu Gordea
 */
public class EuropeanaQuery implements EuropeanaQueryInterface, EuropeanaOperators {

    private String wholeSubQuery;
    private List<SubQuery> subQueries;
    private String generalTerms;
    private String provider;
    private String dataProvider;
    private String notProvider;
    private String notDataProvider;
    private String creator;
    private String title;
    private String date;
    private String subject;
    private String type;
    private String country;
    private String language;
    private String whatTerms;
    private String profile; 
    
    /**
     * Creates an empry query
     */
    public EuropeanaQuery() {
    }

    /**
     * Creates a query with a query string
     *
     * @param query
     */
    public EuropeanaQuery(String query) {
        this.wholeSubQuery = query;
    }

    /**
     * Restricts by a subquery (a whole subquery with operators and fields is
     * allowed)
     *
     * @param wholeSubQuery
     */
    @Override
	public void setWholeSubQuery(String wholeSubQuery) {
        this.wholeSubQuery = wholeSubQuery;
    }

    @Override
	public String getWholeSubQuery() {
        return wholeSubQuery;
    }

    /**
     * Restricts by general terms, to be searched in every field
     *
     * @param generalTerms
     */
    public void setGeneralTerms(String generalTerms) {
        this.generalTerms = generalTerms;
    }

    public String getGeneralTerms() {
        return generalTerms;
    }

    /**
     * Restricts by provider
     *
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    /**
     * Restricts by data provider
     *
     * @param dataProvider
     */
    public void setDataProvider(String dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getDataProvider() {
        return dataProvider;
    }

    /**
     * Restricts by creator
     *
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * Restricts by title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Restricts by date
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    /**
     * Restricts by subject
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * Restricts by type
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Restricts by provider country
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    /**
     * Restricts by provider language
     *
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Returns the Europeana query string. Deprecated and renamed to
     * getSearchTerms() to maintain naming conventions with Europeana API
     *
     * @return the Europeana query string
     */
    @Deprecated
    public String getQueryString() {
        return getSearchTerms();
    }

    
    public String getSearchTerms() {

    	StringBuffer buf = new StringBuffer();
        buildSearchQueryString(buf);
        return buf.toString();
    }

	protected void buildSearchQueryString(StringBuffer buf) {
		
		if (this.wholeSubQuery != null && this.wholeSubQuery.trim().length() > 0) {
            buf.append(this.wholeSubQuery);
        }
		
		if(getSubQueries() != null){
			for (SubQuery subquery : getSubQueries()) {
				this.addSearchField(buf, subquery.getField(), subquery.getValue(), subquery.isNotQuery(), subquery.isForceQuotes(), subquery.isEncodeValue());
			}
		}
		
        if(this.generalTerms != null)
        	this.addSearchField(buf, "text", this.generalTerms);
        
        //TODO: creator <> who ... needs to be corrected ...
        if(this.creator != null)
        	this.addSearchField(buf, "who", this.creator);
        
        if(this.title != null)
            this.addSearchField(buf, "title", this.title);
        
        if(this.date != null)
            this.addSearchField(buf, "date", this.date, false, false, false);//TODO: check if data must be encoded?!
        
        if(this.subject != null)
        	this.addSearchField(buf, "subject", this.subject);
        
        if(this.type != null)
        	this.addSearchField(buf, "TYPE", this.type, false, false, false);
        
        if(this.dataProvider != null)
        	this.addSearchField(buf, "DATA_PROVIDER", this.dataProvider, false, true, true);
        
        if(this.provider != null)
        	this.addSearchField(buf, "PROVIDER", this.provider, false, true, true);
        
        //TODO: update to subqueries
        if(this.notDataProvider != null)
        	this.addSearchField(buf, "DATA_PROVIDER", this.notDataProvider, true, true, true);
        
        if(this.notProvider != null)
        	this.addSearchField(buf, "PROVIDER", this.notProvider, true, true, true);
        
        if(this.country != null)
        	this.addSearchField(buf, "COUNTRY", this.country, false, false, false);
        
        if(this.language != null)
        	this.addSearchField(buf, "LANGUAGE", this.language, false, true, false);
        
        if(this.whatTerms != null)
        	this.addSearchField(buf, "what", this.whatTerms, false, false, true);
	}

    public String getQueryUrl(EuropeanaConnection connection) throws UnsupportedEncodingException {
        return getQueryUrl(connection, EuropeanaComplexQuery.DEFAULT_OFFSET);
    }

    public String getQueryUrl(EuropeanaConnection connection, long offset) throws UnsupportedEncodingException {
        return getQueryUrl(connection, 12, offset);
    }
    
    public String getQueryUrl(EuropeanaConnection connection, long limit, long offset) throws UnsupportedEncodingException {
        connection.setEuropeanaUri("http://api.europeana.eu/api/opensearch.json");
        StringBuilder url = new StringBuilder();
        url.append(connection).append("?searchTerms=");
        String searchTerms = getSearchTerms();
        url.append(searchTerms);
        url.append("&rows=").append(limit);
        url.append("&startPage=").append(offset);
        url.append("&wskey=").append(connection.getApiKey());
        return url.toString();
    }

    protected void addSearchField(StringBuffer buf, String field, String value) {
        this.addSearchField(buf, field, value, false, false, true);
    }

    protected void addSearchField(StringBuffer buf, String field, String value, boolean not, boolean forceQuotes, boolean encodeValue) {
        if (value == null) {
            return;
        }

        value = value.trim();
        if (value.length() == 0) {
            return;
        }

        if (not) {
            buf.append(NOT).append(encodeSearchValue(EMPTY_SPACE));
        } else if (buf.length() > 0) {
            buf.append(encodeSearchValue(EMPTY_SPACE)).append(AND).append(encodeSearchValue(EMPTY_SPACE));
        }

        if (field != null) {
            buf.append(field);
            buf.append(encodeSearchValue(IS));
        }

        //TODO: check if the brackets are really needed. In worst case add the boolean forceBrackets parameter
        //TODO: correct this hack 
        if(!"europeana_id".equals(field))
        	buf.append("(");

        if (forceQuotes && !value.startsWith("\"")) {
            buf.append(encodeSearchValue("\""));
            if(encodeValue)
            	buf.append(encodeSearchValue(value));
            else
             buf.append(value);
            buf.append(encodeSearchValue("\""));
        } else {
        	if(encodeValue)
            	buf.append(encodeSearchValue(value));
            else
             buf.append(value);
        }
        if(!"europeana_id".equals(field))
            buf.append(")");
    }

	/**
     * Restricts by provider negatively (not this provider)
     *
     * @param notProvider
     */
    public void setNotProvider(String notProvider) {
        this.notProvider = notProvider;
    }

    public String getNotProvider() {
        return notProvider;
    }

    /**
     * Restricts by data provider negatively (not this data provider)
     *
     * @param notDataProvider
     */
    public void setNotDataProvider(String notDataProvider) {
        this.notDataProvider = notDataProvider;
    }

    public String getNotDataProvider() {
        return notDataProvider;
    }

	public void setWhatTerms(String whatTerms) {
		this.whatTerms = whatTerms;
	}

	public String getWhatTerms() {
		return whatTerms;
	}
	
	protected String encodeSearchValue(String searchValue){
		try {
			return URLEncoder.encode(searchValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//actualy should never happen as "UTF-8" is a valid encoding
			throw new TechnicalRuntimeException("Cannot encode using UTF-8 encoding!", e);
		}
	}

	public List<SubQuery> getSubQueries() {
		return subQueries;
	}

	public void addSubQuery(SubQuery subQuery) {
		//this.subQueries = subQueries;
		if(subQueries == null)
			subQueries = new ArrayList<SubQuery>(3);
		
		subQueries.add(subQuery);
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}
