package eu.europeana.api.client.model.search;

import java.util.List;

import com.google.gson.Gson;

import eu.europeana.api.client.util.StringUrlProcessor;


public abstract class CommonMetadata {

	public static final int EDM_FIELD_IGNORE = -1;
	public static final int EDM_FIELD_PREVIEW = 1;
	public static final int EDM_FIELD_IS_SHOWN_BY = 2;
	public static final int FIELD_TITLE = 3;
	public static final int FIELD_DC_DESCRIPTION = 4;
	public static final int FIELD_DC_CREATOR = 5;
	public static final int EDM_FIELD_THUMBNAIL_LARGE = 101;
	public static final int EDM_FIELD_THUMBNAIL_W400 = 102;
	public static final String PARAM_SIZE = "&size=";
	public static final String PARAM_SIZE_W400 = "&size=w400";
	public static final String PARAM_SIZE_LARGE = "&size=LARGE";
	
	public static final int EDM_OBJECT_URL = 1001;
	
	public static final int LIMIT_ALL = -1;
	public static final int START_BEGINING = -1;
	
	
	
	private String type;
	private List<String> edmIsShownAt;
	private List<String> edmIsShownBy;
//	private List<String> title;

	private StringUrlProcessor urlProcessor;
	
	protected StringUrlProcessor getUrlProcessor() {
		if(urlProcessor == null)
			urlProcessor = new StringUrlProcessor();
		return urlProcessor;
	}
	
	public abstract String getFieldContent(int edmField);
	public abstract String getThumbnailLarge();
	public abstract String getThumbnailOfSize(String sizeParam);
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getEdmIsShownAt() {
		return edmIsShownAt;
	}

	public void setEdmIsShownAt(List<String> edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
	}
	
	public List<String> getEdmIsShownBy() {
		return edmIsShownBy;
	}

	public void setEdmIsShownBy(List<String> edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}
	
	
//	public List<String> getTitle() {
//		return title;
//	}
//
//	public void setTitle(List<String> title) {
//		this.title = title;
//	}
	
	  /**
     * Returns a JSON representation of this item. This JSON representation will
     * be slightly different to the one provided by the Europeana API, with no
     * namespaces information.
     * @return 
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
