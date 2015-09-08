package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.search.query.Api2Query;
import eu.europeana.api.client.search.query.EuropeanaQueryInterface;

public class ThumbnailsAccessorTest {

	@Test
	public void testCopyThumbnails() throws IOException, EuropeanaApiProblem {
	
		ThumbnailsAccessor ta = new ThumbnailsAccessor(new EuropeanaApi2Client());
		EuropeanaQueryInterface query = new Api2Query();
		query.setType("IMAGE");
		query.setGeneralTerms("da vinci");
		
		File tmpFolder = new File("/tmp/europeana/images");
		
		List<String> thumbnailsCopied = ta.copyThumbnails(query, tmpFolder, 2);
		assertEquals(2, thumbnailsCopied.size());
			
	}
}
