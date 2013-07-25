/*
 * EuropeanaItem.java - europeana4j
 * (C) 2011 Digibis S.L.
 */
package eu.europeana.api.client;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import eu.europeana.api.client.adv.EuropeanaComplexQuery;

/**
 * A EuropeanaItem is the metadata of a work record provided by Europeana.
 *
 * @author Andres Viedma Pelaez
 * @author Sergiu Gordea
 */
public class EuropeanaApi2Item {

    private List<String> title;
    private String link;
    private String guid;
    //private String description;
    //private String enclosure;
    // dcterms:
    private List<String> isPartOf;
    // europeana:
    private String type;
    private List<String> year;
    private List<String> language;
    private List<String> provider;
    private List<String> dataProvider;
    private List<String> rights;
    // enrichment:
    //private List<String> period_begin;
    //private List<String> period_end;
    //private List<String> place_latitude;
    //private List<String> place_longitude;
    //private List<String> period_term;
    //private List<String> period_label;
    //private List<String> place_term;
    //private List<String> place_label;
    //private List<String> concept_term;
    //private List<String> concept_label;
    
    private ArrayList<HashMap<String, String>> edmTimespanLabel;
    
    private List<String> europeanaCollectionName;
    private List<String> dcCreator;
    
    private String id;
    private Integer index;
    private Integer europeanaCompleteness;
    
    private List<String> edmPreview;

    public EuropeanaApi2Item() {
    }

    /*
     ********* CONVENIENCE METHODS **********
     */
    /**
     * Returns true if the item is classified as textual material
     * @return 
     */
    public boolean isText() {
        return EuropeanaComplexQuery.TYPE.TEXT.equals(this.type);
    }

    /**
     * Returns true if the item is classified as image material
     * @return 
     */
    public boolean isImage() {
        return EuropeanaComplexQuery.TYPE.IMAGE.equals(this.type);
    }

    /**
     * Returns true if the item is classified as sound material
     * @return 
     */
    public boolean isSound() {
        return EuropeanaComplexQuery.TYPE.SOUND.equals(this.type);
    }

    /**
     * Returns true if the item is classified as video material
     * @return 
     */
    public boolean isVideo() {
        return EuropeanaComplexQuery.TYPE.VIDEO.equals(this.type);
    }
    
    /**
     * Returns true if the item is classified as 3D material
     * @return 
     */
    public boolean is3D() {
        return EuropeanaComplexQuery.TYPE._3D.equals(this.type);
    }

    
    /**
     * Returns the object unique identifier.
     * @return 
     */
    public String getObjectIdentifier() {
        String part0 = this.getObjectURL();
        if (part0 == null) {
            return null;
        }
        int iPart = part0.lastIndexOf('/');
        if (iPart < 0) {
            return null;
        } else {
            part0 = part0.substring(iPart + 1);
            iPart = part0.indexOf('.');
            if (iPart > 0) {
                part0 = part0.substring(0, iPart);
            }
            return part0;
        }
    }

    /**
     * Returns the URL of the digital object in Europeana.
     * @return 
     */
    public String getObjectURL() {
        return this.getGuid();
    }

    /**
     * Returns a JSON representation of this item. This JSON representation will
     * be slightly different to the one provided by the Europeana API, with no
     * namespaces information.
     * @return 
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Writes a JSON representation of this item. This JSON representation will
     * be slightly different to the one provided by the Europeana API, with no
     * namespaces information.
     * @param out 
     * @throws IOException 
     */
    public void toJSON(Writer out) throws IOException {
        Gson gson = new Gson();
        JsonWriter out2 = new JsonWriter(out);
        gson.toJson(this, EuropeanaApi2Item.class, out2);
        out2.flush();
    }

    /**
     * Creates an item from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json
     * @return  
     */
    public static EuropeanaApi2Item loadJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EuropeanaApi2Item.class);
    }

    /**
     * Creates an item from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json
     * @return  
     */
    public static EuropeanaApi2Item loadJSON(Reader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EuropeanaApi2Item.class);
    }

    /**
     * Creates a list of items from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json 
     * @return 
     */
    public static List<EuropeanaApi2Item> loadJSONList(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaApi2Item>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    /**
     * Creates a list of items from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json
     * @return  
     */
    public static List<EuropeanaApi2Item> loadJSONList(Reader json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaApi2Item>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    /**
     * Creates a list of items from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json 
     * @return 
     */
    public static Map<String, String> loadJSONMap(String json) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        return gson.fromJson(json, mapType);
    }

    /**
     * Creates a list of items from a JSON representation. The expected JSON
     * representation will be slightly different to the one provided by the
     * Europeana API, with no namespaces information.
     * @param json
     * @return  
     */
    public static Map<String, String> loadJSONMap(Reader json) {
    	 Gson gson = new Gson();
         Type mapType = new TypeToken<HashMap<String, String>>() {
         }.getType();
         return gson.fromJson(json, mapType);
    }
   
    /*
     ********* DIRECT PROPERTY GETTERS / SETTERS **********
     */
    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public List<String> getYear() {
        return year;
    }

    public void setYear(List<String> year) {
        this.year = year;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(List<String> provider) {
        this.provider = provider;
    }

    public List<String> getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(List<String> dataProvider) {
        this.dataProvider = dataProvider;
    }

    public List<String> getRights() {
        return rights;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }

   
    public List<String> getIsPartOf() {
        return isPartOf;
    }

    public void setIsPartOf(List<String> isPartOf) {
        this.isPartOf = isPartOf;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the europeanaCollectionName
     */
    public List<String> getEuropeanaCollectionName() {
        return europeanaCollectionName;
    }

    /**
     * @param europeanaCollectionName the europeanaCollectionName to set
     */
    public void setEuropeanaCollectionName(List<String> europeanaCollectionName) {
        this.europeanaCollectionName = europeanaCollectionName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the dcCreator
     */
    public List<String> getDcCreator() {
        return dcCreator;
    }

    /**
     * @param dcCreator the dcCreator to set
     */
    public void setDcCreator(List<String> dcCreator) {
        this.dcCreator = dcCreator;
    }

	public void setEdmTimespanLabel(ArrayList<HashMap<String, String>> edmTimespanLabel) {
		this.edmTimespanLabel = edmTimespanLabel;
	}

	public ArrayList<HashMap<String, String>> getEdmTimespanLabel() {
		return edmTimespanLabel;
	}

	public void setEuropeanaCompleteness(Integer europeanaCompleteness) {
		this.europeanaCompleteness = europeanaCompleteness;
	}

	public Integer getEuropeanaCompleteness() {
		return europeanaCompleteness;
	}

	public void setEdmPreview(List<String> edmPreview) {
		this.edmPreview = edmPreview;
	}

	public List<String> getEdmPreview() {
		return edmPreview;
	}
}
