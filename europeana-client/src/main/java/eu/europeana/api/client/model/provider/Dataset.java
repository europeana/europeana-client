package eu.europeana.api.client.model.provider;

import java.util.Date;

import com.google.gson.Gson;

/**
 * Class representing the Dataset objects returned by Providers api
 * see http://labs.europeana.eu/api/provider/#response-1 
 * @author Sergiu Gordea 
 *
 */
public class Dataset {

	String identifier, provIdentifier, providerName, name, status;
	int publishedRecords, deleteRecords;
	Date creationDate;
	
	protected String getIdentifier() {
		return identifier;
	}
	protected void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	protected String getProvIdentifier() {
		return provIdentifier;
	}
	protected void setProvIdentifier(String provIdentifier) {
		this.provIdentifier = provIdentifier;
	}
	protected String getProviderName() {
		return providerName;
	}
	protected void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected String getStatus() {
		return status;
	}
	protected void setStatus(String status) {
		this.status = status;
	}
	protected int getPublishedRecords() {
		return publishedRecords;
	}
	protected void setPublishedRecords(int publishedRecords) {
		this.publishedRecords = publishedRecords;
	}
	protected int getDeleteRecords() {
		return deleteRecords;
	}
	protected void setDeleteRecords(int deleteRecords) {
		this.deleteRecords = deleteRecords;
	}
	protected Date getCreationDate() {
		return creationDate;
	}
	protected void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
