package eu.europeana.api.client.dataset;

public class DatasetDescriptor {

	String imageSetName, collectionName;
	String[] classifications;
	int thumbnailWithSize = -1;
	boolean storeItemPreview;
	
	public boolean isStoreItemPreview() {
		return storeItemPreview;
	}

	public void setStoreItemPreview(boolean storeItemPreview) {
		this.storeItemPreview = storeItemPreview;
	}

	public int getThumbnailWithSize() {
		return thumbnailWithSize;
	}

	public void setThumbnailWithSize(int thumbnailWithSize) {
		this.thumbnailWithSize = thumbnailWithSize;
	}

	public DatasetDescriptor(String imageSetName, String collectionName){
		this(imageSetName, collectionName, null);
	}
	
	public DatasetDescriptor(String imageSetName, String collectionName, String[] classifications){
		this(imageSetName, collectionName, classifications, -1);
	}
	
	public DatasetDescriptor(String imageSetName, String collectionName, String[] classifications, int thumbnailSize){
		this.imageSetName = imageSetName;
		this.collectionName = collectionName;
		this.classifications = classifications;
		this.thumbnailWithSize = thumbnailSize;
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
