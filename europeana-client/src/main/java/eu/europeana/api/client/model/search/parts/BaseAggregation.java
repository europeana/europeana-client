package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public abstract class BaseAggregation {

	final String RIGHTS_KEY_DEF = "def";
	
	
	private String about;
	private String aggregatedCHO;
	private Map<String, List<String>> edmRights;

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAggregatedCHO() {
		return aggregatedCHO;
	}

	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = aggregatedCHO;
	}

	public Map<String, List<String>> getEdmRights() {
		return edmRights;
	}

	public void setEdmRights(Map<String, List<String>> edmRights) {
		this.edmRights = edmRights;
	}

	public List<String> getDefaultEdmRights() {
		if(getEdmRights() != null)
			return getEdmRights().get(RIGHTS_KEY_DEF);
		
		return null;
	}

}
