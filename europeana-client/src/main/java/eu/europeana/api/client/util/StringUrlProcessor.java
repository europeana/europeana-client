package eu.europeana.api.client.util;

public class StringUrlProcessor {

	/**
	 * remove the given parameter and its value from the URL
	 * 
	 * @param queryParam
	 *            - depending on the urlPart (i.e. full URL or query params part
	 *            only), the queryParam should include ?, & or no prefix
	 * @param urlPart
	 * @return
	 */
	public String removeParam(final String queryParam, String urlPart) {

		int startPos = urlPart.indexOf(queryParam);
		int endPos = urlPart.indexOf('&', startPos + 1);

		if (startPos < 0)
			return urlPart; // queryParam not found
		else {
			if (endPos < 0)
				return urlPart.substring(0, startPos); // last query param
			else
				return urlPart.substring(0, startPos) + urlPart.substring(endPos);
		}
	}

	public String replaceParam(final String queryParam, String value, String urlPart) {

		StringBuilder ret = new StringBuilder();
		ret.append(removeParam(queryParam, urlPart));
		ret.append(queryParam);
		if(!queryParam.endsWith("="))
			ret.append('=');
		ret.append(value);
		
		return ret.toString();
	}
}
