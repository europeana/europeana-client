package eu.europeana.api.client.search.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import eu.europeana.api.client.util.StringUrlProcessor;

public class Api2QueryBuilder extends StringUrlProcessor{

	public Api2QueryInterface buildQuery(String collectionName, String generalTerms, String what) throws IOException {

		return buildQuery(collectionName, generalTerms, what, null);
	}

	public Api2QueryInterface buildQuery(String collectionName, String generalTerms, String what, String objectType)
			throws IOException {

		return buildQuery(collectionName, generalTerms, what, null, objectType);
	}

	public Api2QueryInterface buildQuery(String collectionName, String generalTerms, String what, String creator,
			String objectType) {

		return buildQuery(collectionName, generalTerms, what, creator, objectType, null);
	}

	public Api2QueryInterface buildQuery(String collectionName, String generalTerms, String what, String creator,
			String objectType, String provider) {

		return buildQuery(collectionName, generalTerms, what, creator, objectType, provider, null);
	}

	public Api2QueryInterface buildQuery(String collectionName, String generalTerms, String what, String creator,
			String objectType, String provider, String dataProvider) {

		return buildQuery(collectionName, generalTerms, what, creator, objectType, provider, dataProvider, null);
	}

	public Api2QueryInterface buildQuery(String collectionName,

	String generalTerms, String what, String creator, String objectType, String provider, String dataProvider,
			String[] refinements) {
		Api2QueryInterface query = new Api2Query(collectionName);

		query.setWhatTerms(what);
		query.setGeneralTerms(generalTerms);
		query.setCreator(creator);
		query.setType(objectType);
		query.setProvider(provider);
		query.setDataProvider(dataProvider);

		if (refinements != null && refinements.length > 0) {
			for (int i = 0; i < refinements.length; i++) {
				query.addQueryRefinement(refinements[i]);
			}
		}
		return query;
	}

	public Api2QueryInterface buildQuery(String portalSearchUrl)
			throws MalformedURLException, UnsupportedEncodingException {

		URL portalUrl = new URL(portalSearchUrl);
		Api2Query apiQuery = new Api2Query();

		String queryParams = portalUrl.getQuery();
		queryParams = removeStartRows(queryParams);
		queryParams = updateSearchQueryParameterName(queryParams);

		apiQuery.setQueryParams(queryParams);

		return apiQuery;
	}

	public Api2QueryInterface buildBaseQuery(String baseApiSearchUrl)
			throws MalformedURLException, UnsupportedEncodingException {

		URL apiSearchUrl = new URL(baseApiSearchUrl);
		Api2Query apiQuery = new Api2Query();

		String queryParams = apiSearchUrl.getQuery();
		queryParams = removeStartRows(queryParams);
		queryParams = removeParam("&wskey=", queryParams);
		//queryParams = updateSearchQueryParameterName(queryParams);

		apiQuery.setQueryParams(queryParams);

		return apiQuery;
	}

	private String updateSearchQueryParameterName(String queryParams) {
		if (queryParams.indexOf("&q=") > -1)
			return queryParams.replaceFirst("&q=", "&query=");
		else if (queryParams.indexOf("q=") > -1) ////if first query param in the URL
			return queryParams.replaceFirst("q=", "query=");
		return queryParams;
	}

	private String removeStartRows(String queryParams) {

		String ret = removeParam("&start=", queryParams);
		ret = removeParam("&rows=", ret);

		return ret;
	}
}
