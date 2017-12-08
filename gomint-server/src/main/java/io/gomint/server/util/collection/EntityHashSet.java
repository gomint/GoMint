package io.gomint.server.util.collection;

import com.koloboke.collect.ObjCursor;
import com.koloboke.compile.ConcurrentModificationUnchecked;
import com.koloboke.compile.KolobokeSet;
import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeSet
@ConcurrentModificationUnchecked
public abstract class EntityHashSet {

    /**
     * Create a new set for invalid GUIDs
     *
     * @param expectedSize for the new set
     * @return a new set which holds GUIDs for connection removing
     */
    public static EntityHashSet withExpectedSize( int expectedSize ) {
        return new KolobokeEntityHashSet( expectedSize );
    }

    public abstract ObjCursor<Entity> cursor();
    public abstract void clear();
    public abstract boolean add(Entity entity);
    public abstract boolean remove(Entity entity);
    public abstract boolean contains(Entity entity);

}
