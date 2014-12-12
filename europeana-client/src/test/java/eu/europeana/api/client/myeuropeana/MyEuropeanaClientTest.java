package eu.europeana.api.client.myeuropeana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import eu.europeana.api.client.MyEuropeanaClient;
import eu.europeana.api.client.myeuropeana.exception.MyEuropeanaApiException;
import eu.europeana.api.client.myeuropeana.impl.MyEuropeanaClientImpl;
import eu.europeana.api.client.myeuropeana.result.TagsApiResponse;

public class MyEuropeanaClientTest {

	@Test
	public void testParseApiResponse() throws MyEuropeanaApiException, IOException{
		MyEuropeanaClient client = new MyEuropeanaClientImpl();
		String jsonFile = "/myeuropeana/mydata_tag.json";
		String json = readJsonFile(jsonFile);
		TagsApiResponse apiResponse = client.parseTagsApiResponse(json);
		assertEquals(600, apiResponse.getItemsCount());
		assertEquals(600, apiResponse.getTotalResults());
	}

	private String readJsonFile(String testResource) throws IOException {
		BufferedReader reader = null;
		StringBuilder out = null;
		try {
			InputStream resourceAsStream = getClass().getResourceAsStream(
					testResource);
			reader = new BufferedReader(new InputStreamReader(
					resourceAsStream));
			out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
		} finally {
			if(reader!= null)
				reader.close();
		}
		return out.toString();

	}
}
