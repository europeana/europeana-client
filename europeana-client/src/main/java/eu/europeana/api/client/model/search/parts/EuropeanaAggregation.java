package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public class EuropeanaAggregation extends BaseAggregation{
	
	private String edmLandingPage;
	private Map<String, List<String>> edmCountry;
	private Map<String, List<String>> edmLanguage;
	private String edmPreview;

	
	public String getEdmLandingPage() {
		return edmLandingPage;
	}

	public void setEdmLandingPage(String edmLandingPage) {
		this.edmLandingPage = edmLandingPage;
	}

	public Map<String, List<String>> getEdmCountry() {
		return edmCountry;
	}

	public void setEdmCountry(Map<String, List<String>> edmCountry) {
		this.edmCountry = edmCountry;
	}

	public Map<String, List<String>> getEdmLanguage() {
		return edmLanguage;
	}

	public void setEdmLanguage(Map<String, List<String>> edmLanguage) {
		this.edmLanguage = edmLanguage;
	}

	public String getEdmPreview() {
		return edmPreview;
	}

	public void setEdmPreview(String edmPreview) {
		this.edmPreview = edmPreview;
	}

	
}