/*
 * EuropeanaResults.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.response.abstracts.AbstractListResponse;

/**
 * A EuropeanaResults is an object encapsulating the results of a query to
 * Europeana. It can be the result of multiple calls to the Europeana API with
 * the same query (using pagination of results).
 *
 * @author Andres Viedma Pelaez
 */
public class EuropeanaApi2Results extends AbstractListResponse<EuropeanaApi2Item> {

	private String nextCursor;
	
    public void setNextCursor(String nextCursor) {
		this.nextCursor = nextCursor;
	}

	public EuropeanaApi2Results() {
    	setItems(new ArrayList<EuropeanaApi2Item>());
    }

    /**
     * Adds an item to the results.
     * @param item 
     */
    public void addItem(EuropeanaApi2Item item) {
        this.getItems().add(item);
    }


    /**
     * Returns a list of EuropeanaItem objects with all the stored results
     * @return 
     */
    public List<EuropeanaApi2Item> getAllItems() {
        if (this.getItems() == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(this.getItems());
        }
    }

    /**
     * Returns the number of results provided in this results object
     * @return 
     * Used in Client API 1
     */
    @Deprecated 
    public long getItemCount() {
        return (this.getItems() == null ? 0 : this.getItems().size());
    }

    /**
     * @deprecated used only in Europeana V1
     * Acumulates the provided results in this object
     * @param res2 
     */
    @Deprecated
    public void acumulate(EuropeanaApi2Results res2) {
    	this.getItems().addAll(res2.getItems());
    	this.setItemsCount(getItemsCount() + res2.getItemsCount());
    }
    
    /**
     * @Deprecated Europeana V1 method 
     * @param limit
     */
    @Deprecated
    public void limitResults (int limit)
    {
        if (this.getItemCount() > limit) {
            this.setItems(this.getItems().subList (0, limit));
            this.setItemsCount(limit);
        }
    }

    /**
     * This doesn't seems to be used.  
     * @return
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void toJSON(Writer out) throws IOException {
        Gson gson = new Gson();
        JsonWriter out2 = new JsonWriter(out);
        gson.toJson(this, EuropeanaApi2Results.class, out2);
        out2.flush();
    }

    
    public String getNextCursor() {
    	return this.nextCursor;
    }
    
    
    //TODO: remove commented code
//    public static AbstractApiResponse loadJSON(String json) {
//        Gson gson = new Gson();
//        return gson.fromJson(json, EuropeanaApi2Results.class);
//    }

//    public static AbstractApiResponse loadJSON(Reader json) {
//        Gson gson = new Gson();
//        return gson.fromJson(json, EuropeanaApi2Results.class);
//    }

//    public List<EuropeanaApi2Results> loadJSONList(String json) {
//        Gson gson = new Gson();
//        Type collectionType = new TypeToken<List<EuropeanaApi2Results>>() {
//        }.getType();
//        return gson.fromJson(json, collectionType);
//    }

//    public List<EuropeanaApi2Results> loadJSONList(Reader json) {
//        Gson gson = new Gson();
//        Type collectionType = new TypeToken<List<EuropeanaApi2Results>>() {
//        }.getType();
//        return gson.fromJson(json, collectionType);
//    }

    
	
}
