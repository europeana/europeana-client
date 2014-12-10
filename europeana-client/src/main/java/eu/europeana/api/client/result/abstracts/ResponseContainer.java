package eu.europeana.api.client.result.abstracts;

public abstract class ResponseContainer {

	protected long totalResults;
	private String apikey;
	private String action;
	private Boolean success;
	private long requestNumber;
	private String error;
	
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
