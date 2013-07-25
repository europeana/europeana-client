package eu.europeana.api.client.adv;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import eu.europeana.api.client.EuropeanaConnection;
import eu.europeana.api.client.EuropeanaQueryInterface;

/**
 * This class is used to work with EuropeanaSearchTerm
 * 
 * @author Cosmin Coman
 */
public class EuropeanaComplexQuery implements EuropeanaQueryInterface {
    
    private EuropeanaSearchTerm sreachTerms = null;
    private String type = null;
    
    public static class TYPE {

        public static final String TEXT = "TEXT";
        public static final String VIDEO = "VIDEO";
        public static final String SOUND = "SOUND";
        public static final String IMAGE = "IMAGE";
        public static final String _3D = "3D";
    }
    
    public static final long DEFAULT_OFFSET = 1;
    
    /**
     * Returns the Europeana search terms query string
     * 
     * @param sreachTerms 
     */
    public EuropeanaComplexQuery(EuropeanaSearchTerm sreachTerms) {
        this.sreachTerms = sreachTerms;
    }
    
    /**
     * 
     * @param sreachTerms
     * @param queryType IMAGE, TEXT, etc. Defined in TYPE
     */
    public EuropeanaComplexQuery(EuropeanaSearchTerm sreachTerms, String queryType) {
        this.sreachTerms = sreachTerms;
        this.type = queryType;
    }
    
    public String getSearchTerms() {
        return sreachTerms.toString();
    }

    public String getQueryUrl(EuropeanaConnection connection) throws UnsupportedEncodingException {
        return getQueryUrl(connection, EuropeanaComplexQuery.DEFAULT_OFFSET);
    }

    public String getQueryUrl(EuropeanaConnection connection, long offset) throws UnsupportedEncodingException {
        return getQueryUrl(connection, 12, offset);
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueryUrl(EuropeanaConnection connection, long limit, long offset) throws UnsupportedEncodingException {
        String seachTerms = this.sreachTerms.toString();
        StringBuilder url = new StringBuilder();
        url.append(connection.getEuropeanaUri()).append("?query=").append(URLEncoder.encode(seachTerms, "UTF-8"));
        url.append("&rows=").append(limit);
        //given the fact that normal programmers use 0 as default offset, we make a check
        if (offset<=0) {
            offset = 1;
        }
        if (this.type!=null) {
            url.append("&qf=TYPE:").append(this.type);
        }
        url.append("&start=").append(offset);
        url.append("&wskey=").append(connection.getApiKey());
        //System.err.println(url);
        return url.toString();
    }

}
