package io.gomint.server.util.collection;

import com.koloboke.compile.ConcurrentModificationUnchecked;
import com.koloboke.compile.KolobokeMap;
import com.koloboke.compile.MethodForm;
import io.gomint.server.event.EventHandlerList;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
@ConcurrentModificationUnchecked
public abstract class EventHandlerMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static EventHandlerMap withExpectedSize( int expectedSize ) {
        return new KolobokeEventHandlerMap( expectedSize );
    }

    public final EventHandlerList getEventHandler( int key ) {
        return subGet( key );
    }

    public final void storeEventHandler( int key, EventHandlerList value ) {
        subJustPut( key, value );
    }

    public final boolean removeEventHandler( long key ) {
        return subRemove( key );
    }

    @MethodForm( "get" )
    abstract EventHandlerList subGet( long key );

    @MethodForm( "justPut" )
    abstract void subJustPut( long key, EventHandlerList value );

    @MethodForm( "justRemove" )
    abstract boolean subRemove( long key );

}
