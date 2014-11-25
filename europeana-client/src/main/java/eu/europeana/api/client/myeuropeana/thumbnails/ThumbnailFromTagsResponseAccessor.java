package eu.europeana.api.client.myeuropeana.thumbnails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.europeana.api.client.exception.TechnicalRuntimeException;
import eu.europeana.api.client.myeuropeana.result.TagItem;
import eu.europeana.api.client.myeuropeana.result.TagsApiResponse;
import eu.europeana.api.client.thumbnails.ThumbnailsAccessor;

public class ThumbnailFromTagsResponseAccessor extends ThumbnailsAccessor {

	
	public Map<String, Map<String, String>> getThumbnailsFromTagsApiResponse(TagsApiResponse response) throws TechnicalRuntimeException{
		
		Map<String, Map<String, String>> thumbnailsByTag = new HashMap<String, Map<String, String>>();
		
		List<TagItem> tags = response.getItems();
		for (TagItem tagItem : tags) {
			Map<String, String> thumbnailMap = thumbnailsByTag.get(tagItem.getTag()); 
			if(thumbnailMap == null){
				thumbnailMap = new HashMap<String, String>();
				thumbnailsByTag.put(tagItem.getTag(), thumbnailMap);
			}
			thumbnailMap.put(tagItem.getEuropeanaId(), tagItem.getEdmPreview());
		}
		
		// fetchBlock(blockStartPosition, limit, errorHandlingPolicy); 
			
		return thumbnailsByTag;
	}

	

	
}
