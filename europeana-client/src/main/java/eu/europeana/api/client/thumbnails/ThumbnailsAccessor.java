/*
 * ThumbnailsAccessor.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.europeana.api.client.thumbnails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.EuropeanaApi2Item;
import eu.europeana.api.client.EuropeanaApi2Results;
import eu.europeana.api.client.EuropeanaQuery;
import eu.europeana.api.client.HttpConnector;


/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of
 * thumbnails of the Europeana items.
 * 
 * @author Andres Viedma
 */
public class ThumbnailsAccessor
{
    private static final Log log = LogFactory.getLog(ThumbnailsAccessor.class);
	
    private HttpConnector http = new HttpConnector();
    EuropeanaApi2Client europeanaClient;
	
    
    public ThumbnailsAccessor ()
    {
    }

    public ThumbnailsAccessor (EuropeanaApi2Client europeanaClient)
    {
        this.europeanaClient = europeanaClient;
    }

    
    public boolean writeItemThumbnail (EuropeanaApi2Item item, OutputStream out, boolean useTypeThumb)
            throws IOException
    {
        String mime = "image";
        if(item.getEdmPreview() == null || item.getEdmPreview().isEmpty())
        	return false;
        boolean bOk = this.http.silentWriteURLContent (item.getEdmPreview().get(1), out, mime);
//        boolean bOk = this.http.silentWriteURLContent (item.getThumbnailURL(), out, mime);
//        if (!bOk)  bOk = this.http.silentWriteURLContent (item.getOriginalThumbnailURL(), out, mime);
//        if (!bOk && useTypeThumb)
//            return this.http.writeURLContent (item.getThumbnailOrTypeURL(), out, mime);
//        else
//            return bOk;
        
        return bOk;
    }

    
    
    public List<EuropeanaApi2Item> copyThumbnails (EuropeanaQuery search, File dir, int maxResults)
            throws IOException
    {
        EuropeanaApi2Results res = europeanaClient.search (search, maxResults);
        
        List<EuropeanaApi2Item> items = res.getAllItems();
        List<EuropeanaApi2Item> itemsOk = new ArrayList<EuropeanaApi2Item> (items.size());
        for (int i=0; i < items.size(); i++) {
        	EuropeanaApi2Item item = (EuropeanaApi2Item) items.get(i);
            String id = item.getObjectIdentifier();
            if ((id != null) && (id.length() > 0)) {
                String nombreFich = id + ".jpg";
                File fich = new File (dir, nombreFich);
                FileOutputStream out = new FileOutputStream (fich);
                try {
                    if (i % 10 == 0)  log.info ("Copying thumbnail: " + (i + 1) + " / " + items.size());
                    this.writeItemThumbnail (item, out, false);
                    
                } finally {
                    out.close();
                }
                
                fich = new File (dir, nombreFich);
                if (fich.length () < 100) {
                    fich.delete();
                } else if (fich.exists()) {
                    itemsOk.add (item);
                }
            }
        }
        
        log.info ("Copying finished OK, thumbnails generated: " + itemsOk.size());
        return itemsOk;
    }
    
}
