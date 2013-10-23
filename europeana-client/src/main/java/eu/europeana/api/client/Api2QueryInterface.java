package eu.europeana.api.client;

import java.util.List;

/**
 * The query interface should be used instead of concrete implementation in order to allow use case specific enhancements
 * @author Sergiu Gordea 
 *
 */
public interface Api2QueryInterface extends EuropeanaQueryInterface {

	/**
	 * Registers a query refinement
	 * Similar to the "add keyword" functionality in the portal  
	 * @param qf
	 */
	public void addQueryRefinement(String qf);

	/**
	 * retrieves the list of registered query refinements
	 * @return
	 */
	public List<String> getQueryRefinements();

	

}
