package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import eu.europeana.api.client.Api2Query;
import eu.europeana.api.client.EuropeanaQueryInterface;
import eu.europeana.api.client.connection.EuropeanaApi2Client;

public class ThumbnailsAccessorTest {

	@Test
	public void testCopyThumbnails() throws IOException {
	
		ThumbnailsAccessor ta = new ThumbnailsAccessor(new EuropeanaApi2Client());
		EuropeanaQueryInterface query = new Api2Query();
		query.setType("IMAGE");
		query.setGeneralTerms("test");
		
		File tmpFolder = new File("/tmp/europeana/images");
		
		List<String> thumbnailsCopied = ta.copyThumbnails(query, tmpFolder, 2);
		assertEquals(2, thumbnailsCopied.size());
			
	}
}
