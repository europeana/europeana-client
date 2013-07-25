/*
 * EuropeanaQuery.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import eu.europeana.api.client.adv.EuropeanaComplexQuery;

/**
 * The EuropeanaQuery is an encapsulated query to a EuropeanaConnection object.
 *
 * @author Andres Viedma Pelaez
 */
public class EuropeanaQuery implements EuropeanaQueryInterface {

    private String wholeSubQuery;
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
    public void setWholeSubQuery(String wholeSubQuery) {
        this.wholeSubQuery = wholeSubQuery;
    }

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

        if (this.wholeSubQuery != null && this.wholeSubQuery.trim().length() > 0) {
            buf.append(this.wholeSubQuery);
        }
        if(this.generalTerms != null)
        	this.addSearchField(buf, "text", this.generalTerms);
        
        if(this.creator != null)
        	this.addSearchField(buf, "who", this.creator);
        
        if(this.title != null)
            this.addSearchField(buf, "title", this.title);
        
        if(this.date != null)
            this.addSearchField(buf, "date", this.date);
        
        if(this.subject != null)
        	this.addSearchField(buf, "subject", this.subject);
        
        if(this.type != null)
        	this.addSearchField(buf, "TYPE", this.type);
        
        if(this.dataProvider != null)
        	this.addSearchField(buf, "DATA_PROVIDER", this.dataProvider, false, true);
        
        if(this.provider != null)
        	this.addSearchField(buf, "PROVIDER", this.provider, false, true);
        
        if(this.notDataProvider != null)
        	this.addSearchField(buf, "DATA_PROVIDER", this.notDataProvider, true, true);
        
        if(this.notProvider != null)
        	this.addSearchField(buf, "PROVIDER", this.notProvider, true, true);
        
        if(this.country != null)
        	this.addSearchField(buf, "COUNTRY", this.country, false, true);
        
        if(this.language != null)
        	this.addSearchField(buf, "LANGUAGE", this.language, false, true);
        
        if(this.whatTerms != null)
        	this.addSearchField(buf, "what", this.whatTerms, false, false);
        
        return buf.toString();
    }

    public String getQueryUrl(EuropeanaConnection connection) throws UnsupportedEncodingException {
        return getQueryUrl(connection, EuropeanaComplexQuery.DEFAULT_OFFSET);
    }

    public String getQueryUrl(EuropeanaConnection connection, long offset) throws UnsupportedEncodingException {
        return getQueryUrl(connection, 12, offset);
    }
    
    public String getQueryUrl(EuropeanaConnection connection, long limit, long offset) throws UnsupportedEncodingException {
        String searchTerms = getSearchTerms();
        connection.setEuropeanaUri("http://api.europeana.eu/api/opensearch.json");
        StringBuilder url = new StringBuilder();
        url.append(connection.getEuropeanaUri()).append("?searchTerms=").append(URLEncoder.encode(searchTerms, "UTF-8"));
        url.append("&rows=").append(limit);
        url.append("&startPage=").append(offset);
        url.append("&wskey=").append(connection.getApiKey());
        return url.toString();
    }

    private void addSearchField(StringBuffer buf, String field, String value) {
        this.addSearchField(buf, field, value, false, false);
    }

    private void addSearchField(StringBuffer buf, String field, String value, boolean not, boolean forceQuotes) {
        if (value == null) {
            return;
        }

        value = value.trim();
        if (value.length() == 0) {
            return;
        }

        if (not) {
            buf.append(" NOT ");
        } else if (buf.length() > 0) {
            buf.append(" AND ");
        }

        if (field != null) {
            buf.append(field);
            buf.append(": ");
        }

        buf.append("(");

        if (forceQuotes && !value.startsWith("\"")) {
            buf.append("\"");
            buf.append(value);
            buf.append("\"");
        } else {
            buf.append(value);
        }
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
}
