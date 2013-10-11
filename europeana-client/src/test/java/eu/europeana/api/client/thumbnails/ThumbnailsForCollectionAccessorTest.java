package eu.europeana.api.client.thumbnails;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class ThumbnailsForCollectionAccessorTest{

	protected static final String TEST_COLLECTION_NAME = "07501_*";
	protected static final int TEST_COLLECTION_SIZE = 40811;
	protected static final String DEVELOPMENT_COLLECTION_NAME = "00000_*";

	@Test
	public void testGetThumbnailsForCollectionLimit() throws IOException {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				TEST_COLLECTION_NAME);
		//201 = 2* 100 +1  
		int resultsSize = (2 * ThumbnailsForCollectionAccessor.DEFAULT_BLOCKSIZE) + 1;
		//start at second page
		int startPosition = 0; 
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(startPosition,
				resultsSize);
		
		
		// assert all image urls are correct
		assertTrue(thumbnails.size() == resultsSize);
		
		// check if the contnt of the collection was changed 
		assertTrue(TEST_COLLECTION_SIZE == tca.totalResults);
				
		
	}

	@Test
	public void testGetThumbnailsForCollectionAll() throws IOException {
		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				DEVELOPMENT_COLLECTION_NAME);
		int resultsSize = -1;
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(0,
				resultsSize);
		
		int objectsInDevCollection = 4;
		int objectsWithThumbnailInDevCollection = 2;
		
		// check total 
		assertTrue(tca.totalResults == objectsInDevCollection);
				
		// check correct thumbnail extraction
		assertTrue(thumbnails.size() == objectsWithThumbnailInDevCollection);
				
//		String fileName = "/collection_07501_thumbnails.csv";
//		writeThumbnailsToCsvFile(thumbnails, fileName);
	}

	private void writeThumbnailsToCsvFile(Map<String, String> thumbnails,
			String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		int count = 0;
		for (Entry<String, String> thumbnail : thumbnails.entrySet()) {

			writer.write(thumbnail.getKey());
			writer.write(";");
			writer.write(thumbnail.getValue());
			writer.write("\n");
			count++;
			if (count % 1000 == 0)
				writer.flush();
		}
		writer.flush();
		writer.close();
	}

	public int buildImageSet(String imageSet, String collectionName,
			String generalTerms, String what) throws IOException {

		return buildImageSet(imageSet, collectionName, generalTerms, what, null);
	}

	public int buildImageSet(String imageSet, String collectionName,
			String generalTerms, String what, String objectType) throws IOException {
		return buildImageSet(imageSet, collectionName, generalTerms, what, null, objectType); 
	}

		
	public int buildImageSet(String imageSet, String collectionName,
			String generalTerms, String what, String creator, String objectType) throws IOException {

		ThumbnailsForCollectionAccessor tca = new ThumbnailsForCollectionAccessor(
				collectionName);
		tca.getQuery().setWhatTerms(what);
		tca.getQuery().setGeneralTerms(generalTerms);
		tca.getQuery().setCreator(creator);
		tca.getQuery().setType(objectType);
		

		int resultsSize = -1;
		Map<String, String> thumbnails = tca.getThumbnailsForCollection(0,
				resultsSize);

		String fileName = "/" + imageSet + "_" + collectionName + ".csv";
		writeThumbnailsToCsvFile(thumbnails, fileName);

		// assert all image urls are correct
		assertTrue(thumbnails.size() == tca.totalResults);

		return thumbnails.size();
	}

	//@Test
	public void createEvaluationDataset() throws IOException {
		// int objects = buildImageSet("Rijksmuseum-portrets",
		// "90402_M_NL_Rijksmuseum", "portret", "schilderij");
		// assertEquals(objects, 1243) ;

		// int objects = buildImageSet("Rijksmuseum-miniatur",
		// "90402_M_NL_Rijksmuseum", "miniatuur beeld", null);
		// assertEquals(objects, 69) ;

//		int objects1 = buildImageSet("Rijksmuseum-landscape",
//				"90402_M_NL_Rijksmuseum", "landschap", "schilderij");
//		assertEquals(objects1, 425);
//
//		int objects2 = buildImageSet("Rijksmuseum-bottles",
//				"90402_M_NL_Rijksmuseum", null, "fles");
//		assertEquals(objects2, 144);
//
//		int objects3 = buildImageSet("Rijksmuseum-drawing-lanscape",
//				"90402_M_NL_Rijksmuseum", "landschap", "tekening");
//		assertEquals(objects3, 700);

//		int objects4 = buildImageSet("Rijksmuseum-porcelain",
//				"90402_M_NL_Rijksmuseum", null, "Hollands porselein");
//		assertEquals(objects4, 131);
//
//		int objects5 = buildImageSet("Teylers-parrots",
//				"10106_Ag_EU_STERNA_48", "parrot", null);
//		assertEquals(objects5, 105);
//
//		int objects6 = buildImageSet("Teylers-duck", "10106_Ag_EU_STERNA_48",
//				"duck", null);
//		assertEquals(objects6, 120);
//
//		int objects7 = buildImageSet("Teylers-woodpecker",
//				"10106_Ag_EU_STERNA_48", "woodpecker", null);
//		assertEquals(objects7, 210);
//
//		int objects8 = buildImageSet("Teylers-eagle", "10106_Ag_EU_STERNA_48",
//				"falco", null);
//		assertEquals(objects8, 146);

//		int objects9 = buildImageSet("Galileo-electric",
//				"02301_Ag_IT_MG_catalogue", "ingegneria elettrica", null);
//		assertEquals(objects9, 231);
//
//		int objects10 = buildImageSet("Galileo-optic", "02301_Ag_IT_MG_catalogue",
//				"optics", null, "IMAGE");
//		assertEquals(objects10, 195);
//
//		int objects11 = buildImageSet("MIMO-trompe", "09102_Ag_EU_MIMO_ESE",
//				"trompe", null);
//		assertEquals(objects11, 1092);
//		
//		int objects12 = buildImageSet("NHM-LISABON-butterfly", "2023901_Ag_EU_NaturalEurope_all",
//				"butterflies", null, "IMAGE");
//		assertEquals(objects12, 371);
		
	}

}
