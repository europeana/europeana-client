package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public class TimeSpan {
	private String about;
	private Map<String, List<String>> prefLabel;
	private Map<String, List<String>> isPartOf;

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Map<String, List<String>> getPrefLabel() {
		return prefLabel;
	}

	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		this.prefLabel = prefLabel;
	}

	public Map<String, List<String>> getIsPartOf() {
		return isPartOf;
	}

	public void setIsPartOf(Map<String, List<String>> isPartOf) {
		this.isPartOf = isPartOf;
	}
}