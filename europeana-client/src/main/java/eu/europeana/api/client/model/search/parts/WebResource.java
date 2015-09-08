package eu.europeana.api.client.model.search.parts;

import java.util.List;
import java.util.Map;

public class WebResource {
	private Map<String, List<String>> webResourceEdmRights;
	private String about;
	private Map<String, List<String>> dcDescription;
	private Map<String, List<String>> dcFormat;
	private Map<String, List<String>> dcSource;
	private Map<String, List<String>> dctermsExtent;
	private Map<String, List<String>> dctermsIssued;
	private Map<String, List<String>> dctermsConformsTo;
	private Map<String, List<String>> dctermsCreated;
	private Map<String, List<String>> dctermsIsFormatOf;
	private Map<String, List<String>> dctermsHasPart;
	private String isNextInSequence;
	private Map<String, List<String>> webResourceDcRights;

	public Map<String, List<String>> getWebResourceDcRights() {
		return webResourceDcRights;
	}

	public void setWebResourceDcRights(
			Map<String, List<String>> webResourceDcRights) {
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
