package io.gomint.server.util.collection;

import com.koloboke.compile.ConcurrentModificationUnchecked;
import com.koloboke.compile.KolobokeMap;
import io.gomint.server.gui.Form;
import io.gomint.server.gui.FormListener;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
@ConcurrentModificationUnchecked
public abstract class FormListenerIDMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static FormListenerIDMap withExpectedSize( int expectedSize ) {
        return new KolobokeFormListenerIDMap( expectedSize );
    }

    // CHECKSTYLE:OFF
    public abstract FormListener get( int key);
    public abstract void justPut(int key, FormListener formListener);
    public abstract boolean justRemove(int key);
    public abstract int size();
    public abstract void clear();
    // CHECKSTYLE:ON

}
