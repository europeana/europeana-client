package eu.europeana.api.client.result;

import java.util.List;

public class EuropeanaObject extends EuropeanaApi2Item {
	private int index;
	private int europeanaCompleteness;
	private List<String> edmIsShownAt;
	private float score;
	
	public String toString() {
		String result = "------------------------\n"
				+ "id: " + this.id + "\n"
				+ "title: " + this.title.get(0) + "\n"
				+ "------------------------\n";
		return result;
	}
}
