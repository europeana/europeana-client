package eu.europeana.api.client;

import java.util.List;

/**
 * Interface definition for standardized Search API calls.
 * 
 * @author Sergiu Gordea
 * @version 1.0
 * 
 * The query interface should be used instead of concrete implementation in order 
 * to allow use case specific enhancements
 *
 */
public interface Api2QueryInterface extends EuropeanaQueryInterface {

	/**
	 * Registers a query refinement
	 * Similar to the "add keyword" functionality in the portal
	 *   
	 * @param qf: string to define the query refinement
	 */
	public void addQueryRefinement(String qf);

	/**
	 * retrieves the list of registered query refinements
	 * 
	 * @return list of registered query refinements
	 */
	public List<String> getQueryRefinements();

	

}
