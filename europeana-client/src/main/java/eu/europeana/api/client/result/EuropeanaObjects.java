package eu.europeana.api.client.result;

public class EuropeanaObjects {
	private String apikey;
	private String action;
	private boolean success;
	private int requestNumber;
	private int itemsCount;
	private int totalResults;
	private EuropeanaObject[] items;
	
	public EuropeanaObject getObject(String id) {
		for(EuropeanaObject item : this.items) {
			if(item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public int getItemsCount() {
		return itemsCount;
	}
	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public EuropeanaObject[] getItems() {
		return items;
	}
	public void setItems(EuropeanaObject[] items) {
		this.items = items;
	}
	
}
