/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import com.koloboke.collect.map.ObjObjCursor;
import io.gomint.permission.Group;
import io.gomint.permission.GroupManager;
import io.gomint.server.util.collection.PermissionGroupMap;
import lombok.Setter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PermissionGroupManager implements GroupManager {

    @Setter
    private boolean dirty;
    private PermissionGroupMap groupMap = null;

    /**
     * Update this permission group manager
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last tick in 1/s
     */
    public void update( long currentTimeMS, float dT ) {
        if ( this.groupMap != null && this.dirty) {
            ObjObjCursor<String, Group> groups = this.groupMap.cursor();
            while ( groups.moveNext() ) {
                Group group = groups.value();
                if ( group instanceof PermissionGroup ) {
                    ( (PermissionGroup) group ).resetDirty();
                }
            }

            this.dirty = false;
        }
    }

    @Override
    public Group getOrCreateGroup( String name ) {
        // Check if this is the first group we get/create
        if ( this.groupMap == null ) {
            this.groupMap = PermissionGroupMap.withExpectedSize( 10 );

            PermissionGroup group = new PermissionGroup( this, name );
            this.groupMap.justPut( name, group );
            return group;
        }

        Group group = this.groupMap.get( name );
        if ( group == null ) {
            group = new PermissionGroup( this, name );
            this.groupMap.justPut( name, group );
        }

        return group;
    }

    @Override
    public void removeGroup( Group group ) {
        this.groupMap.justRemove( group.getName() );
    }

}
