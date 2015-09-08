package eu.europeana.api.client.model.search;

import java.util.List;

public class Facet {

	private String name;
	private List<FacetField> fields;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FacetField> getFields() {
		return fields;
	}
	public void setFields(List<FacetField> fields) {
		this.fields = fields;
	}
}
