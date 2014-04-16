package eu.europeana.api.client.result;

import java.util.List;
import java.util.Map;

public class EuropeanaObject extends EuropeanaApi2Item {
	private int index;
	private List<String> edmIsShownAt;
	private float score;
	private String about;
	private List<ObjectProxy> proxies;
	private List<ObjectAggregation> aggregations;
	private List<ObjectTimeSpan> timespans;
	private int europeanaCompleteness;
	private List<ObjectProvidedCHO> providedCHOs;
	private ObjectEuropeanaAggregation europeanaAggregation;
	private int timestamp_created_epoch;
	private int timestamp_update_epoch;
	private String timestamp_created;
	private String timestamp_update;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<String> getEdmIsShownAt() {
		return edmIsShownAt;
	}

	public void setEdmIsShownAt(List<String> edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
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

	public List<ObjectProxy> getProxies() {
		return proxies;
	}

	public void setProxies(List<ObjectProxy> proxies) {
		this.proxies = proxies;
	}

	public List<ObjectAggregation> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<ObjectAggregation> aggregations) {
		this.aggregations = aggregations;
	}

	public List<ObjectTimeSpan> getTimespans() {
		return timespans;
	}

	public void setTimespans(List<ObjectTimeSpan> timespans) {
		this.timespans = timespans;
	}

	public int getEuropeanaCompleteness() {
		return europeanaCompleteness;
	}

	public void setEuropeanaCompleteness(int europeanaCompleteness) {
		this.europeanaCompleteness = europeanaCompleteness;
	}

	public List<ObjectProvidedCHO> getProvidedCHOs() {
		return providedCHOs;
	}

	public void setProvidedCHOs(List<ObjectProvidedCHO> providedCHOs) {
		this.providedCHOs = providedCHOs;
	}

	public ObjectEuropeanaAggregation getEuropeanaAggregation() {
		return europeanaAggregation;
	}

	public void setEuropeanaAggregation(
			ObjectEuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = europeanaAggregation;
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

	public String toString() {
		String result = "------------------------\n"
				+ "about: " + this.about + "\n"
				+ "title: " + this.title.get(0) + "\n"
				+ "------------------------\n";
		return result;
	}
	
	public class ObjectAggregation {
		private String about;
		private String[] europeanaCollectionName;
		private List<ObjectTimeSpan> timespans;
		private int europeanaCompleteness;
		private List<ObjectProvidedCHO> providedCHOs;
		private List<String> edmDatasetName;
		private int timestamp_created_epoch;
		private int timestamp_update_epoch;
		private String timestamp_created;
		private String timestamp_update;
		
		private String edmIsShownBy;
		private String edmIsShownAt;
		private String edmObject;
		private Map<String,List<String>> edmProvider;
		private Map<String,List<String>> edmRights;
		private String edmUgc;
		private Map<String,List<String>> dcRights;
		private String[] hasView;
		private String aggregatedCHO;
		private String[] aggregates;
		private String[] edmUnstored;
		private List<ObjectWebResource> webResources;
		private Boolean edmPreviewNoDistribute;
		private Map<String,List<String>> edmDataProvider;
		private ObjectEuropeanaAggregation europeanaAggregation;
		
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

		public Map<String, List<String>> getEdmRights() {
			return edmRights;
		}

		public void setEdmRights(Map<String, List<String>> edmRights) {
			this.edmRights = edmRights;
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

		public String getAggregatedCHO() {
			return aggregatedCHO;
		}

		public void setAggregatedCHO(String aggregatedCHO) {
			this.aggregatedCHO = aggregatedCHO;
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

		public List<ObjectWebResource> getWebResources() {
			return webResources;
		}

		public void setWebResources(List<ObjectWebResource> webResources) {
			this.webResources = webResources;
		}

		public Boolean getEdmPreviewNoDistribute() {
			return edmPreviewNoDistribute;
		}

		public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
			this.edmPreviewNoDistribute = edmPreviewNoDistribute;
		}
		
		public String getAbout() {
			return about;
		}

		public void setAbout(String about) {
			this.about = about;
		}

		public String[] getEuropeanaCollectionName() {
			return europeanaCollectionName;
		}

		public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
			this.europeanaCollectionName = europeanaCollectionName;
		}

		public List<ObjectTimeSpan> getTimespans() {
			return timespans;
		}

		public void setTimespans(List<ObjectTimeSpan> timespans) {
			this.timespans = timespans;
		}

		public int getEuropeanaCompleteness() {
			return europeanaCompleteness;
		}

		public void setEuropeanaCompleteness(int europeanaCompleteness) {
			this.europeanaCompleteness = europeanaCompleteness;
		}

		public List<ObjectProvidedCHO> getProvidedCHOs() {
			return providedCHOs;
		}

		public void setProvidedCHOs(List<ObjectProvidedCHO> providedCHOs) {
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

		public ObjectEuropeanaAggregation getEuropeanaAggregation() {
			return europeanaAggregation;
		}

		public void setEuropeanaAggregation(
				ObjectEuropeanaAggregation europeanaAggregation) {
			this.europeanaAggregation = europeanaAggregation;
		}

		
		public class ObjectWebResource {
			private Map<String,List<String>> webResourceEdmRights;
			private String about;
			private Map<String,List<String>> dcDescription;
			private Map<String,List<String>> dcFormat;
			private Map<String,List<String>> dcSource;
			private Map<String,List<String>> dctermsExtent;
			private Map<String,List<String>> dctermsIssued;
			private Map<String,List<String>> dctermsConformsTo;
			private Map<String,List<String>> dctermsCreated;
			private Map<String,List<String>> dctermsIsFormatOf;
			private Map<String,List<String>> dctermsHasPart;
			private String isNextInSequence;
			private Map<String,List<String>> webResourceDcRights;
			
			public Map<String, List<String>> getWebResourceDcRights() {
				return webResourceDcRights;
			}
			public void setWebResourceDcRights(Map<String, List<String>> webResourceDcRights) {
				this.webResourceDcRights = webResourceDcRights;
			}
			public Map<String, List<String>> getWebResourceEdmRights() {
				return webResourceEdmRights;
			}
			public void setWebResourceEdmRights(
					Map<String, List<String>> webResourceEdmRights) {
				this.webResourceEdmRights = webResourceEdmRights;
			}
			public String getAbout() {
				return about;
			}
			public void setAbout(String about) {
				this.about = about;
			}
			public Map<String, List<String>> getDcDescription() {
				return dcDescription;
			}
			public void setDcDescription(Map<String, List<String>> dcDescription) {
				this.dcDescription = dcDescription;
			}
			public Map<String, List<String>> getDcFormat() {
				return dcFormat;
			}
			public void setDcFormat(Map<String, List<String>> dcFormat) {
				this.dcFormat = dcFormat;
			}
			public Map<String, List<String>> getDcSource() {
				return dcSource;
			}
			public void setDcSource(Map<String, List<String>> dcSource) {
				this.dcSource = dcSource;
			}
			public Map<String, List<String>> getDctermsExtent() {
				return dctermsExtent;
			}
			public void setDctermsExtent(Map<String, List<String>> dctermsExtent) {
				this.dctermsExtent = dctermsExtent;
			}
			public Map<String, List<String>> getDctermsIssued() {
				return dctermsIssued;
			}
			public void setDctermsIssued(Map<String, List<String>> dctermsIssued) {
				this.dctermsIssued = dctermsIssued;
			}
			public Map<String, List<String>> getDctermsConformsTo() {
				return dctermsConformsTo;
			}
			public void setDctermsConformsTo(Map<String, List<String>> dctermsConformsTo) {
				this.dctermsConformsTo = dctermsConformsTo;
			}
			public Map<String, List<String>> getDctermsCreated() {
				return dctermsCreated;
			}
			public void setDctermsCreated(Map<String, List<String>> dctermsCreated) {
				this.dctermsCreated = dctermsCreated;
			}
			public Map<String, List<String>> getDctermsIsFormatOf() {
				return dctermsIsFormatOf;
			}
			public void setDctermsIsFormatOf(Map<String, List<String>> dctermsIsFormatOf) {
				this.dctermsIsFormatOf = dctermsIsFormatOf;
			}
			public Map<String, List<String>> getDctermsHasPart() {
				return dctermsHasPart;
			}
			public void setDctermsHasPart(Map<String, List<String>> dctermsHasPart) {
				this.dctermsHasPart = dctermsHasPart;
			}
			public String getIsNextInSequence() {
				return isNextInSequence;
			}
			public void setIsNextInSequence(String isNextInSequence) {
				this.isNextInSequence = isNextInSequence;
			}		
			
		}
	}
	
	public class ObjectProxy {
		private Map<String, List<String>> dcCreator;
		private Map<String, List<String>> dcDescription;
		private Map<String, List<String>> dcIdentifier;
		private Map<String, List<String>> dcRights;
		private Map<String, List<String>> dcSource;
		private Map<String, List<String>> dcSubject;
		private Map<String, List<String>> dcType;
		private Map<String, List<String>> dctermsAlternative;
		private Map<String, List<String>> dctermsCreated;
		private Map<String, List<String>> dctermsExtent;
		private Map<String, List<String>> dctermsMedium;
		private List<String> proxyIn;
		private String proxyFor;
		private String edmType;
		private boolean europeanaProxy;
		private Map<String, List<String>> year;
		private String about;
		
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
		public Map<String, List<String>> getDcCreator() {
			return dcCreator;
		}
		public void setDcCreator(Map<String, List<String>> dcCreator) {
			this.dcCreator = dcCreator;
		}
		public Map<String, List<String>> getDcDescription() {
			return dcDescription;
		}
		public void setDcDescription(Map<String, List<String>> dcDescription) {
			this.dcDescription = dcDescription;
		}
		public Map<String, List<String>> getDcIdentifier() {
			return dcIdentifier;
		}
		public void setDcIdentifier(Map<String, List<String>> dcIdentifier) {
			this.dcIdentifier = dcIdentifier;
		}
		public Map<String, List<String>> getDcRights() {
			return dcRights;
		}
		public void setDcRights(Map<String, List<String>> dcRights) {
			this.dcRights = dcRights;
		}
		public Map<String, List<String>> getDcSource() {
			return dcSource;
		}
		public void setDcSource(Map<String, List<String>> dcSource) {
			this.dcSource = dcSource;
		}
		public Map<String, List<String>> getDcSubject() {
			return dcSubject;
		}
		public void setDcSubject(Map<String, List<String>> dcSubject) {
			this.dcSubject = dcSubject;
		}
		public Map<String, List<String>> getDcType() {
			return dcType;
		}
		public void setDcType(Map<String, List<String>> dcType) {
			this.dcType = dcType;
		}
		public Map<String, List<String>> getDctermsAlternative() {
			return dctermsAlternative;
		}
		public void setDctermsAlternative(Map<String, List<String>> dctermsAlternative) {
			this.dctermsAlternative = dctermsAlternative;
		}
		public Map<String, List<String>> getDctermsCreated() {
			return dctermsCreated;
		}
		public void setDctermsCreated(Map<String, List<String>> dctermsCreated) {
			this.dctermsCreated = dctermsCreated;
		}
		public Map<String, List<String>> getDctermsExtent() {
			return dctermsExtent;
		}
		public void setDctermsExtent(Map<String, List<String>> dctermsExtent) {
			this.dctermsExtent = dctermsExtent;
		}
		public Map<String, List<String>> getDctermsMedium() {
			return dctermsMedium;
		}
		public void setDctermsMedium(Map<String, List<String>> dctermsMedium) {
			this.dctermsMedium = dctermsMedium;
		}
		public List<String> getProxyIn() {
			return proxyIn;
		}
		public void setProxyIn(List<String> proxyIn) {
			this.proxyIn = proxyIn;
		}
		public String getProxyFor() {
			return proxyFor;
		}
		public void setProxyFor(String proxyFor) {
			this.proxyFor = proxyFor;
		}
		public String getEdmType() {
			return edmType;
		}
		public void setEdmType(String edmType) {
			this.edmType = edmType;
		}
		public boolean isEuropeanaProxy() {
			return europeanaProxy;
		}
		public void setEuropeanaProxy(boolean europeanaProxy) {
			this.europeanaProxy = europeanaProxy;
		}
		public Map<String, List<String>> getYear() {
			return year;
		}
		public void setYear(Map<String, List<String>> year) {
			this.year = year;
		}
	}
	
	public class ObjectTimeSpan {
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
	
	public class ObjectProvidedCHO {
		private String about;

		public String getAbout() {
			return about;
		}

		public void setAbout(String about) {
			this.about = about;
		}
	}
	
	public class ObjectEuropeanaAggregation {
		private String about;
		private String edmLandingPage;
		private Map<String, List<String>> edmCountry;
		private Map<String, List<String>> edmLanguage;
		private String edmPreview;		
		
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
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
}
