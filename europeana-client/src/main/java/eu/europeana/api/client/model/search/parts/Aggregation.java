package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public class Aggregation extends BaseAggregation{
	
//	private String edmPreview;

	private String[] europeanaCollectionName;
	private List<TimeSpan> timespans;
	private int europeanaCompleteness;
	private List<ProvidedCHO> providedCHOs;
	private List<String> edmDatasetName;
	private int timestamp_created_epoch;
	private int timestamp_update_epoch;
	private String timestamp_created;
	private String timestamp_update;

	private String edmIsShownBy;
	private String edmIsShownAt;
	private String edmObject;
	private Map<String, List<String>> edmProvider;
	private String edmUgc;
	private Map<String, List<String>> dcRights;
	private String[] hasView;
	private String[] aggregates;
	private String[] edmUnstored;
	private List<WebResource> webResources;
	private Boolean edmPreviewNoDistribute;
	private Map<String, List<String>> edmDataProvider;
	private EuropeanaAggregation europeanaAggregation;

	public Map<String, List<String>> getEdmDataProvider() {
		return edmDataProvider;
	}

	public void setEdmDataProvider(Map<String, List<String>> edmDataProvider) {
		this.edmDataProvider = edmDataProvider;
	}

	public String getEdmIsShownBy() {
		return edmIsShownBy;
	}

	public void setEdmIsShownBy(String edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}

	public String getEdmIsShownAt() {
		return edmIsShownAt;
	}

	public void setEdmIsShownAt(String edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
	}

	public String getEdmObject() {
		return edmObject;
	}

	public void setEdmObject(String edmObject) {
		this.edmObject = edmObject;
	}

	public Map<String, List<String>> getEdmProvider() {
		return edmProvider;
	}

	public void setEdmProvider(Map<String, List<String>> edmProvider) {
		this.edmProvider = edmProvider;
	}

	public String getEdmUgc() {
		return edmUgc;
	}

	public void setEdmUgc(String edmUgc) {
		this.edmUgc = edmUgc;
	}

	public Map<String, List<String>> getDcRights() {
		return dcRights;
	}

	public void setDcRights(Map<String, List<String>> dcRights) {
		this.dcRights = dcRights;
	}

	public String[] getHasView() {
		return hasView;
	}

	public void setHasView(String[] hasView) {
		this.hasView = hasView;
	}

	public String[] getAggregates() {
		return aggregates;
	}

	public void setAggregates(String[] aggregates) {
		this.aggregates = aggregates;
	}

	public String[] getEdmUnstored() {
		return edmUnstored;
	}

	public void setEdmUnstored(String[] edmUnstored) {
		this.edmUnstored = edmUnstored;
	}

	public List<WebResource> getWebResources() {
		return webResources;
	}

	public void setWebResources(List<WebResource> webResources) {
		this.webResources = webResources;
	}

	public Boolean getEdmPreviewNoDistribute() {
		return edmPreviewNoDistribute;
	}

	public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
		this.edmPreviewNoDistribute = edmPreviewNoDistribute;
	}

	public String[] getEuropeanaCollectionName() {
		return europeanaCollectionName;
	}

	public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
		this.europeanaCollectionName = europeanaCollectionName;
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

	public List<String> getEdmDatasetName() {
		return edmDatasetName;
	}

	public void setEdmDatasetName(List<String> edmDatasetName) {
		this.edmDatasetName = edmDatasetName;
	}

	public int getTimestamp_created_epoch() {
		return timestamp_created_epoch;
	}

	public void setTimestamp_created_epoch(int timestamp_created_epoch) {
		this.timestamp_created_epoch = timestamp_created_epoch;
	}

	public int getTimestamp_update_epoch() {
		return timestamp_update_epoch;
	}

	public void setTimestamp_update_epoch(int timestamp_update_epoch) {
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

	public EuropeanaAggregation getEuropeanaAggregation() {
		return europeanaAggregation;
	}

	public void setEuropeanaAggregation(
			EuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = europeanaAggregation;
	}
}
