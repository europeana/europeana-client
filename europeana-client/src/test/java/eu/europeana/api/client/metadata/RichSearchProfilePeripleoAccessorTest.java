package eu.europeana.api.client.metadata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * This class is computing intensive it must be run manually when needed 
 * 
 * It is a data loader from Europeana API for Peripleo
 * 
 * e.g. Europeana portal link for sculptures for Rome:
 * http://www.europeana.eu/portal/en/search?view=grid&q=where%3Arome&qf%5B%5D=sculpture&f%5BMEDIA%5D%5B%5D=true&f%5BTYPE%5D%5B%5D=IMAGE&per_page=96
 * 
 * related Europeana API link for sculptures for Rome:
 * http://www.europeana.eu/api/v2/search.json?view=grid&query=where%3Arome&qf=sculpture&MEDIA=true&TYPE=IMAGE&per_page=96&wskey=xxx
 * 
 * @author Roman Graf
 *
 */
//@Ignore
public class RichSearchProfilePeripleoAccessorTest extends EuClientDatasetUtil {

	//@Test
	public void saveRomeSculptures() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=where%3Arome&qf=sculpture&media=true&type=IMAGE&TYPE=IMAGE&wskey=xxx
		// total 164
		String downloadUrl = "http://www.europeana.eu/portal/search.html?view=grid&query=where%3Arome&qf=sculpture&media=true&type=IMAGE&TYPE=IMAGE";
		String downloadDir = "rome-sculptures/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveRomeMonuments() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=what%3Amonument&qf=%22%2Fplace%2Fbase%2F143433%22&media=true&TYPE=IMAGE&per_page=96&wskey=xxx
		// total 969
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?view=grid&query=what%3Amonument&qf=%22%2Fplace%2Fbase%2F143433%22&media=true&TYPE=IMAGE&per_page=96";
		String downloadDir = "rome-monuments/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveRomeInscriptionMonuments() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?country=italy&country=united+kingdom&country=austria&country=greece&media=true&per_page=96&query=inscription+monument+rome&view=grid&wskey=xxx
		// total 237
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?country=italy&country=united+kingdom&country=austria&country=greece&media=true&per_page=96&query=inscription+monument+rome&view=grid";
		String downloadDir = "rome-inscription-monuments/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveNumismatics() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=numismatic&media=true&fTYPE=IMAGE&per_page=96
		// total 677
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?view=grid&query=numismatic&media=true&fTYPE=IMAGE&per_page=96";
		String downloadDir = "numismatics/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}


	//@Test
	public void saveNumismaticsRoman() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?media=true&TYPE=IMAGE&per_page=96&query=what%3Anumismatics&qf=roman&view=grid
		// total 857
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?media=true&TYPE=IMAGE&per_page=96&query=what%3Anumismatics&qf=roman&view=grid";
		String downloadDir = "numismatics-roman/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveNumismaticsGreece() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=what%3Anumismatics&qf=where%3Agreece&media=true&TYPE=IMAGE&per_page=96
		// total 31
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?view=grid&query=what%3Anumismatics&qf=where%3Agreece&media=true&TYPE=IMAGE&per_page=96";
		String downloadDir = "numismatics-greece/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveNumismaticsSmall() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?view=grid&query=what%3A+m%C3%BCnze&qf=TYPE:IMAGE&IMAGE_SIZE=small&media=true&per_page=100
		// total 5028
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?view=grid&query=what%3A+m%C3%BCnze&IMAGE_SIZE=small&media=true&qf=TYPE:IMAGE&per_page=96";
		String downloadDir = "numismatics-small/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveNumismaticsLocaleEn() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?media=true&TYPE=IMAGE&locale=en&per_page=96&query=what%3A"Numismàtica"&qf=roman&view=grid
		// total 685
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?media=true&TYPE=IMAGE&locale=en&per_page=96&query=what%3A%22Numism%C3%A0tica%22&qf=roman&view=grid";
		String downloadDir = "numismatics-locale-en/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}

	//@Test
	public void saveInscriptions() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?media=true&locale=en&query=what%3A"type+of+inscription%3Afasti%2C+leges%2C+acta"&view=grid
		// total 440
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?media=true&locale=en&query=what%3A%22type+of+inscription%3Afasti%2C+leges%2C+acta%22&view=grid";
		String downloadDir = "inscriptions/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}


	@Test
	public void saveFilteredPlaces() throws IOException, EuropeanaApiProblem {
		// http://www.europeana.eu/api/v2/search.json?per_page=96&query=TYPE:IMAGE&qf=%22%2Fagent%2Fbase%2F*%22&qf=%22%2Fplace%2Fbase%2F*%22&qf=ancient&view=grid&profile=rich
		// total 115
		String downloadUrl = 
				"http://www.europeana.eu/portal/en/search?per_page=96&query=TYPE:IMAGE&qf=%22%2Fagent%2Fbase%2F*%22&qf=%22%2Fplace%2Fbase%2F*%22&qf=ancient&view=grid&profile=rich";
		String downloadDir = "filtered-places/";
		
		downloadJsonResults(downloadUrl, downloadDir);	
	}


	private void downloadJsonResults(String portalUrl, String downloadDir)
			throws MalformedURLException, UnsupportedEncodingException, IOException, EuropeanaApiProblem {

		Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
		
		Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
		apiQuery.setProfile("rich");
		MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
		ma.setStoreItemsAsJson(true);
		ma.setMetadataFolder(downloadDir);
		Map<String, String> contentMap = ma.getContentMap(-1, 0, 200000, MetadataAccessor.ERROR_POLICY_CONTINUE);
		
		for (Map.Entry<String, String> pair : contentMap.entrySet()) {
			System.out.println(pair.getKey() + ";" + pair.getValue());
		}
	}
	
	
}
