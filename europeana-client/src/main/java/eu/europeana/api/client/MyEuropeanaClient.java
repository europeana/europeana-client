package eu.europeana.api.client;

import java.io.InputStream;

import eu.europeana.api.client.myeuropeana.exception.MyEuropeanaApiException;
import eu.europeana.api.client.myeuropeana.result.TagsApiResponse;

/**
 * see http://labs.europeana.eu/api/myeuropeana/#response-2 for the MyEuropeanaApi documentation
 * @author Sergiu Gordea 
 *
 */
public interface MyEuropeanaClient {

	public TagsApiResponse parseTagsApiResponse(String json)
			throws MyEuropeanaApiException;

	/**
	 * This method reads the content of the input stream which is expected to be an valid API response and parses it to the
	 * coresponding data object.
	 * 
	 * This method <b>does not</b> close the given input stream 
	 * @param jsonStream
	 * @return
	 * @throws MyEuropeanaApiException
	 */
	public TagsApiResponse parseTagsApiResponse(InputStream jsonStream)
			throws MyEuropeanaApiException;

}
