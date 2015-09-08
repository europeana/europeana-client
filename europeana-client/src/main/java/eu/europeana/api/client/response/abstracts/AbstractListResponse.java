package eu.europeana.api.client.response.abstracts;

import java.util.List;

import eu.europeana.api.client.model.search.Facet;


public class AbstractListResponse<T> extends ResponseContainer{

	private int itemsCount;
	
	private List <T> items;
	
	private List<Facet> facets;

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	/**
	 * @return Returns the total number of results of the query
	 */
	public long getTotalResults() {
	    return totalResults;
	}

	public void setTotalResults(long totalResults) {
	    this.totalResults = totalResults;
	}
	
	public List <T> getItems() {
		return items;
	}

	public void setItems(List <T> items) {
		this.items = items;
	}

	/**
     * Adds an item to the results.
     * @param item 
     */
    public void addItem(T item) {
        this.items.add(item);
    }

	public List<Facet> getFacets() {
		return facets;
	}

	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}
	
}
