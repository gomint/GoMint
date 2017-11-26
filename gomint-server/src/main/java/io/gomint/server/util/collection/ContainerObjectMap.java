package io.gomint.server.util.collection;

import com.koloboke.collect.map.ByteObjCursor;
import com.koloboke.compile.KolobokeMap;
import io.gomint.server.inventory.ContainerInventory;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
public abstract class ContainerObjectMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static ContainerObjectMap withExpectedSize( int expectedSize ) {
        return new KolobokeContainerObjectMap( expectedSize );
    }

    public abstract ContainerInventory remove( byte id );
    public abstract ContainerInventory get( byte id );
    public abstract boolean containsKey( byte id );
    public abstract void justPut( byte id, ContainerInventory containerInventory );
    public abstract ByteObjCursor<ContainerInventory> cursor();

}
