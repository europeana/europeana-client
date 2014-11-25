package eu.europeana.api.client.result;

import java.util.List;

public class AbstractApiResponse<T> {

	private long totalResults;
	private String apikey;
	private String action;
	private Boolean success;
	private long requestNumber;
	//TODO : change to private after support for Europeana V1 will be removed
	int itemsCount;
	private String error;
	
	private List <T> items;

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

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
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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
