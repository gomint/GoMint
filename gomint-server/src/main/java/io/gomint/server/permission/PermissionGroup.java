/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import com.koloboke.collect.map.ObjObjCursor;
import io.gomint.permission.Group;
import io.gomint.server.util.collection.PermissionMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString(of = {"name", "permissions"})
@EqualsAndHashCode(of = "name")
public class PermissionGroup implements Group {

    private final PermissionGroupManager manager;
    private final String name;

    @Getter
    private boolean dirty;
    private PermissionMap permissions;

    /**
     * Create a new permission group. This needs to be configured via
     *
     * @param manager which created this group
     * @param name    of the group
     */
    PermissionGroup( PermissionGroupManager manager, String name ) {
        this.name = name;
        this.manager = manager;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPermission( String permission, boolean value ) {
        if ( this.permissions == null ) {
            this.permissions = PermissionMap.withExpectedSize( 50 );
        }

        this.permissions.justPut( permission.intern(), value );
        this.dirty = true;
        this.manager.setDirty( true );
    }

    @Override
    public void removePermission( String permission ) {
        if ( this.permissions != null ) {
            this.permissions.justRemove( permission.intern() );
        }

        this.dirty = true;
        this.manager.setDirty( true );
    }

    @Override
    public ObjObjCursor<String, Boolean> cursor() {
        if ( this.permissions == null ) {
            return null;
        }

        return this.permissions.cursor();
    }

    /**
     * Reset dirty state
     */
    void resetDirty() {
        this.dirty = false;
    }

    /**
     * Get a permission setting of this group
     *
     * @param permission which we need the setting for
     * @return true or false
     */
    public Boolean get( String permission ) {
        return this.permissions.get( permission );
    }

}
