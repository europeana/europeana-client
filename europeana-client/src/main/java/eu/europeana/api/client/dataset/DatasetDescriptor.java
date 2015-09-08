package eu.europeana.api.client.dataset;

public class DatasetDescriptor {

	String imageSetName, collectionName;
	String[] classifications;
	
	public DatasetDescriptor(String imageSetName, String collectionName){
		this.imageSetName = imageSetName;
		this.collectionName = collectionName;
	}
	
	public DatasetDescriptor(String imageSetName, String collectionName, String[] classifications){
		this(imageSetName, collectionName);
		this.classifications = classifications;
	}
	
	
	public String getImageSetName() {
		return imageSetName;
	}
	public void setImageSetName(String imageSetName) {
		this.imageSetName = imageSetName;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String[] getClassifications() {
		return classifications;
	}
	public void setClassifications(String[] classifications) {
		this.classifications = classifications;
	}
	
	@Override
	public String toString() {
		return getCollectionName() + " - " + getImageSetName();
	}
	
	public String getStringId() {
		return getCollectionName() + " - " + getImageSetName();
	}
}
