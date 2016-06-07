package eu.europeana.api.client.model.search;

import java.util.List;

import com.google.gson.Gson;


public abstract class CommonMetadata {

	public static final int EDM_FIELD_IGNORE = -1;
	public static final int EDM_FIELD_PREVIEW = 1;
	public static final int EDM_FIELD_IS_SHOWN_BY = 2;
	public static final int FIELD_TITLE = 3;
	public static final int FIELD_DC_DESCRIPTION = 4;
	public static final int EDM_FIELD_LARGEST_THUMBNAIL = 101;
	public static final int EDM_OBJECT_URL = 1001;
	
	public static final int LIMIT_ALL = -1;
	public static final int START_BEGINING = -1;
	
	
	public final String SIZE_IS_LARGE = "size=LARGE";

	private String type;
	private List<String> edmIsShownAt;
	private List<String> edmIsShownBy;
//	private List<String> title;

	public abstract String getFieldContent(int edmField);
	public abstract String getLargestThumbnail();
	
	
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
