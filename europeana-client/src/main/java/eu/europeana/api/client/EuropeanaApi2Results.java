/*
 * EuropeanaResults.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

/**
 * A EuropeanaResults is an object encapsulating the results of a query to
 * Europeana. It can be the result of multiple calls to the Europeana API with
 * the same query (using pagination of results).
 *
 * @author Andres Viedma Pelaez
 */
public class EuropeanaApi2Results {

    //private String link;
    //private String description;
    private long totalResults;
    //private long startIndex;
    //private long itemsPerPage;
    private String apikey;
    private String action;
    private Boolean success;
    private long requestNumber;
    private int itemsCount;
    
    private List<EuropeanaApi2Item> items = new ArrayList<EuropeanaApi2Item>();

    public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public EuropeanaApi2Results() {
    }

    /**
     * @return Returns the total number of results of the query
     */
    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * Adds an item to the results.
     * @param item 
     */
    public void addItem(EuropeanaApi2Item item) {
        this.items.add(item);
    }

    public void setItems(List<EuropeanaApi2Item> _items) {
        this.items.clear();
        this.items.addAll(_items);
    }

    /**
     * Returns a list of EuropeanaItem objects with all the stored results
     * @return 
     */
    public List<EuropeanaApi2Item> getAllItems() {
        if (this.items == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(this.items);
        }
    }

    /**
     * Returns the number of results provided in this results object
     * @return 
     */
    public long getItemCount() {
        return (this.items == null ? 0 : this.items.size());
    }

    /**
     * Acumulates the provided results in this object
     * @param res2 
     */
    public void acumulate(EuropeanaApi2Results res2) {
    	this.items.addAll(res2.items);
    	this.itemsCount += res2.itemsCount;
    }
    
    public void limitResults (int limit)
    {
        if (this.getItemCount() > limit) {
            this.items = this.items.subList (0, limit);
            this.itemsCount = limit;
        }
    }

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

    public static EuropeanaApi2Results loadJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EuropeanaApi2Results.class);
    }

    public static EuropeanaApi2Results loadJSON(Reader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EuropeanaApi2Results.class);
    }

    public static List<EuropeanaApi2Results> loadJSONList(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaApi2Results>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    public static List<EuropeanaApi2Results> loadJSONList(Reader json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaApi2Results>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }
}
