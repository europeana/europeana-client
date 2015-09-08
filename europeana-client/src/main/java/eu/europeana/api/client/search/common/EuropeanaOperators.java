package eu.europeana.api.client.search.common;

/**
 * This class defines the logical operators supported by the Europeana API
 * so the programmer will not have to hardcode them
 * 
 * @author Cosmin Coman
 * @author Sergiu Gordea
 *  
 */
//TODO: check if the change to enumaration makes sense
public interface EuropeanaOperators {
    
    public static final String AND = "AND";
    public static final String NOT = "NOT";
    public static final String OR = "OR";
    public static final String EQUALS = "=";
    public static final String IS = ":";
    public static final String EMPTY_SPACE = " ";
}
