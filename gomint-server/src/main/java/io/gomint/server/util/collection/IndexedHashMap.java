package io.gomint.server.util.collection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class IndexedHashMap<K, V> extends LinkedHashMap<K, V> {

    private List<K> index = new ArrayList<>();

    @Override
    public V put( K key, V value ) {
        V ret = super.put( key, value );

        if ( this.index.contains( key ) ) {
            this.index.remove( key );
        }

        this.index.add( key );
        return ret;
    }

    @Override
    public V remove( Object key ) {
        V ret = super.remove( key );
        this.index.remove( key );
        return ret;
    }

    /**
     * Get the index of a key
     *
     * @param key which should be looked up
     * @return index of key or -1 if the key was not found
     */
    public int getIndex( K key ) {
        return this.index.indexOf( key );
    }

}
