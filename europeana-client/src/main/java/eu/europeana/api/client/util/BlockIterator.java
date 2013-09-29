package eu.europeana.api.client.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Generic class for iterators which load the data on demand, obtaining
 * the desired elements in blocks.
 *
 * @author Andres Viedma Pelaez
 */
public class BlockIterator<E>
        implements Iterator<E> {
    
    private BlockLoader<E> loader;
    private List<E> block = Collections.emptyList();
    private int blockOffset = 0;
    private long offset = 0;
    private long totalRecords = -1;
    
    
    public BlockIterator(BlockLoader<E> loader) {
        this.loader = loader;
    }

    @Override
    public boolean hasNext() {
        return (this.checkNext() != null);
    }

    @Override
    public E next() {
        E res = this.checkNext();
        if (res == null) {
            throw new NoSuchElementException();
        } else {
            this.moveForward();
            return res;
        }
    }

    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported in BlockIterator");
    }

    
    private E checkNext() {
        // Load Total records
        if (this.totalRecords < 0) {
            this.totalRecords = this.loader.getTotalRecords();
        }
        
        // Middle of a block
        if (this.blockOffset < this.block.size()) {
            return this.block.get (this.blockOffset);
            
        // End
        } else if (this.offset + this.blockOffset >= this.totalRecords) {
            return null;
        
        // End of a block; load the next
        } else {
            this.offset += this.block.size();
            this.blockOffset = 0;
            this.block = this.loader.loadBlock (this.offset);
            return (this.block.isEmpty()? null : this.block.get(0));
        }
    }
    
    
    private void moveForward() {
        this.blockOffset++;
    }
    
    
    public static interface BlockLoader<E> {
        public List<E> loadBlock (long offset);
        public long getTotalRecords();
    }
}
