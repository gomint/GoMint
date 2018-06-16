/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import io.gomint.permission.Group;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString( of = { "name", "permissions" } )
@EqualsAndHashCode( of = "name" )
public class PermissionGroup implements Group {

    private final PermissionGroupManager manager;
    private final String name;

    @Getter
    private boolean dirty;
    private Object2BooleanMap<String> permissions;

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
            this.permissions = new Object2BooleanOpenHashMap<>();
        }

        this.permissions.put( permission.intern(), value );
        this.dirty = true;
        this.manager.setDirty( true );
    }

    @Override
    public void removePermission( String permission ) {
        if ( this.permissions != null ) {
            this.permissions.remove( permission.intern() );
        }

        this.dirty = true;
        this.manager.setDirty( true );
    }

    @Override
    public ObjectSet<Object2BooleanMap.Entry<String>> cursor() {
        if ( this.permissions == null ) {
            return null;
        }

        return this.permissions.object2BooleanEntrySet();
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
