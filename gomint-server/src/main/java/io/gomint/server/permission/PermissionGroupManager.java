/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import io.gomint.permission.Group;
import io.gomint.permission.GroupManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Setter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PermissionGroupManager implements GroupManager {

    @Setter
    private boolean dirty;
    private Object2ObjectMap<String, Group> groupMap = null;

    /**
     * Update this permission group manager
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last tick in 1/s
     */
    public void update( long currentTimeMS, float dT ) {
        if ( this.groupMap != null && this.dirty ) {
            for ( Object2ObjectMap.Entry<String, Group> entry : this.groupMap.object2ObjectEntrySet() ) {
                if ( entry.getValue() instanceof PermissionGroup ) {
                    ( (PermissionGroup) entry.getValue() ).resetDirty();
                }
            }

            this.dirty = false;
        }
    }

    @Override
    public Group getOrCreateGroup( String name ) {
        // Check if this is the first group we get/create
        if ( this.groupMap == null ) {
            this.groupMap = new Object2ObjectOpenHashMap<>();

            PermissionGroup group = new PermissionGroup( this, name );
            this.groupMap.put( name, group );
            return group;
        }

        Group group = this.groupMap.get( name );
        if ( group == null ) {
            group = new PermissionGroup( this, name );
            this.groupMap.put( name, group );
        }

        return group;
    }

    @Override
    public void removeGroup( Group group ) {
        this.groupMap.remove( group.getName() );
    }

}
