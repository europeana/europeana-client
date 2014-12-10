package eu.europeana.api.client.result.abstracts;

import java.util.List;


public class AbstractSearchResponse<T> extends ResponseContainer{

	//TODO : change to private after support for Europeana V1 will be removed
	int itemsCount;
	
	private List <T> items;

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
	
}
