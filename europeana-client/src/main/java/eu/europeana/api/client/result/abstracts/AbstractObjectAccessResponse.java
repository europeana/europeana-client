package eu.europeana.api.client.result.abstracts;

public class AbstractObjectAccessResponse<T> extends ResponseContainer{
	
	private int statsDuration;
	private T object;
	
	public int getStatsDuration() {
		return statsDuration;
	}
	public void setStatsDuration(int statsDuration) {
		this.statsDuration = statsDuration;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}

}
