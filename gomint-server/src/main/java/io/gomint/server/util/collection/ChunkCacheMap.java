package io.gomint.server.util.collection;

import com.koloboke.compile.KolobokeMap;
import com.koloboke.compile.MethodForm;
import io.gomint.server.world.ChunkAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
public abstract class ChunkCacheMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static ChunkCacheMap withExpectedSize( int expectedSize ) {
        return new KolobokeChunkCacheMap( expectedSize );
    }

    @MethodForm( "keys" )
    public abstract long[] keys();

    @MethodForm( "get" )
    public abstract ChunkAdapter getChunk( long key );

    @MethodForm( "justPut" )
    public abstract void storeChunk( long key, ChunkAdapter value );

    @MethodForm( "justRemove" )
    public abstract boolean removeChunk( long key );

    @MethodForm( "size" )
    public abstract int size();

}
