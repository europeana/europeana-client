package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public class Proxy {
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
	private Map<String, List<String>> dcRelation;
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

	public void setDctermsAlternative(
			Map<String, List<String>> dctermsAlternative) {
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

	public Map<String, List<String>> getDcRelation() {
		return dcRelation;
	}

	public void setDcRelation(Map<String, List<String>> dcRelation) {
		this.dcRelation = dcRelation;
	}
}
