package io.gomint.server.util.collection;

import com.koloboke.compile.ConcurrentModificationUnchecked;
import com.koloboke.compile.KolobokeMap;
import io.gomint.server.gui.Form;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
@ConcurrentModificationUnchecked
public abstract class FormIDMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static FormIDMap withExpectedSize( int expectedSize ) {
        return new KolobokeFormIDMap( expectedSize );
    }

    // CHECKSTYLE:OFF
    public abstract Form get(int key);
    public abstract void justPut(int key, Form form);
    public abstract boolean justRemove(int key);
    public abstract int size();
    public abstract void clear();
    // CHECKSTYLE:ON

}
