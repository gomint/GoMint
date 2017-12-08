/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import com.koloboke.collect.ObjCursor;
import com.koloboke.collect.map.ObjObjCursor;
import com.koloboke.compile.KolobokeMap;
import io.gomint.permission.Group;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
public abstract class PermissionGroupMap {

    /**
     * Create a new map for permission groups
     *
     * @param expectedSize for the new map
     * @return a new map which uses string as key and group as value
     */
    public static PermissionGroupMap withExpectedSize( int expectedSize ) {
        return new KolobokePermissionGroupMap( expectedSize );
    }

    // CHECKSTYLE:OFF
    public abstract void justPut( String name, Group group );
    public abstract boolean justRemove( String name );
    public abstract Group get( String name );
    public abstract ObjObjCursor<String, Group> cursor();
    // CHECKSTYLE:ON

}
