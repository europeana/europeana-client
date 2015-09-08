package eu.europeana.api.client.search.query;

public class SubQuery {

	private String field, value;
	private boolean notQuery, forceQuotes, encodeValue;
	
	public SubQuery(String field, String value){
		this(field, value, false, false, true);
	}
	
	public SubQuery(String field, String value, boolean notQuery, boolean forceQuotes, boolean encodeValue){
		this.field = field;
		this.value = value;
		this.notQuery = notQuery;
		this.forceQuotes = forceQuotes;
		this.encodeValue = encodeValue;
		
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isNotQuery() {
		return notQuery;
	}
	public void setNotQuery(boolean notQuery) {
		this.notQuery = notQuery;
	}
	public boolean isForceQuotes() {
		return forceQuotes;
	}
	public void setForceQuotes(boolean forceQuotes) {
		this.forceQuotes = forceQuotes;
	}

	public boolean isEncodeValue() {
		return encodeValue;
	}

	public void setEncodeValue(boolean encodeValue) {
		this.encodeValue = encodeValue;
	}
	
}
