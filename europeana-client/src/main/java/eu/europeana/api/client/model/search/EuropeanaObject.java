package eu.europeana.api.client.model.search;

import java.util.List;

import eu.europeana.api.client.model.search.parts.Aggregation;
import eu.europeana.api.client.model.search.parts.EuropeanaAggregation;
import eu.europeana.api.client.model.search.parts.ProvidedCHO;
import eu.europeana.api.client.model.search.parts.Proxy;
import eu.europeana.api.client.model.search.parts.TimeSpan;


public class EuropeanaObject extends CommonMetadata{
	
	private List<String> edmDatasetName;
	private String about;
	private List<Proxy> proxies;
	private List<String> title;
	private List<String> language;
	private int europeanaCompleteness;
	private List<TimeSpan> timespans;
	private List<Aggregation> aggregations;
	private List<ProvidedCHO> providedCHOs;
	private EuropeanaAggregation europeanaAggregation;
	private List<String> europeanaCollectionName;
	
	private long index;
	private float score;
	
	private long timestamp_created_epoch;
	private long timestamp_update_epoch;
	private String timestamp_created;
	private String timestamp_update;

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Proxy> getProxies() {
		return proxies;
	}

	public void setProxies(List<Proxy> proxies) {
		this.proxies = proxies;
	}

	public List<Aggregation> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<Aggregation> aggregations) {
		this.aggregations = aggregations;
	}

	public List<TimeSpan> getTimespans() {
		return timespans;
	}

	public void setTimespans(List<TimeSpan> timespans) {
		this.timespans = timespans;
	}

	public int getEuropeanaCompleteness() {
		return europeanaCompleteness;
	}

	public void setEuropeanaCompleteness(int europeanaCompleteness) {
		this.europeanaCompleteness = europeanaCompleteness;
	}

	public List<ProvidedCHO> getProvidedCHOs() {
		return providedCHOs;
	}

	public void setProvidedCHOs(List<ProvidedCHO> providedCHOs) {
		this.providedCHOs = providedCHOs;
	}

	public EuropeanaAggregation getEuropeanaAggregation() {
		return europeanaAggregation;
	}

	public void setEuropeanaAggregation(
			EuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = europeanaAggregation;
	}

	public long getTimestamp_created_epoch() {
		return timestamp_created_epoch;
	}

	public void setTimestamp_created_epoch(long timestamp_created_epoch) {
		this.timestamp_created_epoch = timestamp_created_epoch;
	}

	public long getTimestamp_update_epoch() {
		return timestamp_update_epoch;
	}

	public void setTimestamp_update_epoch(long timestamp_update_epoch) {
		this.timestamp_update_epoch = timestamp_update_epoch;
	}

	public String getTimestamp_created() {
		return timestamp_created;
	}

	public void setTimestamp_created(String timestamp_created) {
		this.timestamp_created = timestamp_created;
	}

	public String getTimestamp_update() {
		return timestamp_update;
	}

	public void setTimestamp_update(String timestamp_update) {
		this.timestamp_update = timestamp_update;
	}

	public String toString() {
		String result = "------------------------\n"
				+ "Title: " + this.getTitle().get(0) + "\n"
				+ "About: " + this.getAbout() + "\n"
				+ "------------------------\n";
		return result;
	}

	public List<String> getEdmDatasetName() {
		return edmDatasetName;
	}

	public void setEdmDatasetName(List<String> edmDatasetName) {
		this.edmDatasetName = edmDatasetName;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	public List<String> getEuropeanaCollectionName() {
		return europeanaCollectionName;
	}

	public void setEuropeanaCollectionName(List<String> europeanaCollectionName) {
		this.europeanaCollectionName = europeanaCollectionName;
	}

	
	public String getFieldContent(int edmField) {
		switch (edmField) {
		case EDM_FIELD_PREVIEW:
			if (getEuropeanaAggregation() != null)
				return getEuropeanaAggregation().getEdmPreview();
			break;

		case EDM_FIELD_LARGEST_THUMBNAIL:
			if (getEuropeanaAggregation() != null)
				return getLargestThumbnail();
			break;
		case EDM_FIELD_IS_SHOWN_BY:
			if (getEdmIsShownBy() != null && !getEdmIsShownBy().isEmpty())
				return getEdmIsShownBy().get(0);
			break;

		case EDM_OBJECT_URL:
			if (getEuropeanaAggregation().getEdmLandingPage() != null && !getEuropeanaAggregation().getEdmLandingPage().isEmpty())
				return getEuropeanaAggregation().getEdmLandingPage();
			break;
		default:
			throw new IllegalArgumentException(
					"edmField not supported for content URL extraction: "
							+ edmField);
		}

		return null;
		// System.out.println("No thumbnail found! for item: " + item.getId());

	}

	public String getLargestThumbnail() {
		return getEdmPreviewFromAggregation();
	}

	private String getEdmPreviewFromAggregation() {
		if (getEuropeanaAggregation() != null)
			return getEuropeanaAggregation().getEdmPreview();
		else
			// cannot find edmPreview
			return null;

		// System.out.println("No thumbnail found! for item: " + item.getId());
	}

	
}
