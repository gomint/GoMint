/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import io.gomint.permission.Group;
import io.gomint.server.entity.EntityPlayer;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PermissionManager implements io.gomint.permission.PermissionManager {

    private final EntityPlayer player;
    private final List<PermissionGroup> groups = new ArrayList<>();
    private final Object2BooleanMap<String> permissions = new Object2BooleanOpenHashMap<>();
    private boolean dirty = false;

    // Effective cache
    private Object2BooleanMap<String> cache = new Object2BooleanOpenHashMap<>();

    /**
     * Update this permission manager
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last tick in 1/s
     */
    public void update( long currentTimeMS, float dT ) {
        // Check if a group changed
        for ( PermissionGroup group : this.groups ) {
            if ( group.isDirty() ) {
                this.dirty = true;
                break;
            }
        }

        // Resend commands when something changed
        if ( this.dirty ) {
            // Resend commands
            this.player.sendCommands();

            this.dirty = false;
        }
    }

    @Override
    public boolean hasPermission( String permission ) {
        // Check if we have a cached copy
        String permissionIntern = permission.intern();
        Boolean val = this.cache.get( permissionIntern );
        if ( val == null ) {
            // Check player permissions first
            String wildCardFound = null;
            for ( Object2BooleanMap.Entry<String> entry : this.permissions.object2BooleanEntrySet() ) {
                // Did we find a full permission match?
                String currentChecking = entry.getKey();
                if ( permissionIntern == currentChecking ) {
                    this.cache.put( permissionIntern, entry.getBooleanValue() );
                    return entry.getBooleanValue();
                }

                // Check for wildcard
                if ( currentChecking.endsWith( "*" ) ) {
                    String wildCardChecking = currentChecking.substring( 0, currentChecking.length() - 1 );
                    if ( permissionIntern.startsWith( wildCardChecking ) ) {
                        if ( wildCardFound == null || currentChecking.length() > wildCardFound.length() ) {
                            wildCardFound = currentChecking;
                        }
                    }
                }
            }

            // Check if we found a wildcard
            if ( wildCardFound != null ) {
                val = this.permissions.getBoolean( wildCardFound );
                this.cache.put( permissionIntern, val );
                return val;
            }

            // Expensive way of checking q.q (groups)
            List<PermissionGroup> reverted = new ArrayList<>( this.groups );
            Collections.reverse( reverted );

            // Iterate over all groups until we found one we can use
            for ( PermissionGroup group : reverted ) {
                ObjectSet<Object2BooleanMap.Entry<String>> playerCursor = group.cursor();
                wildCardFound = null;

                if ( playerCursor != null ) {
                    for ( Object2BooleanMap.Entry<String> entry : playerCursor ) {
                        // Did we find a full permission match?
                        String currentChecking = entry.getKey();
                        if ( permissionIntern == currentChecking ) {
                            this.cache.put( permissionIntern, entry.getBooleanValue() );
                            return entry.getBooleanValue();
                        }

                        // Check for wildcard
                        if ( currentChecking.endsWith( "*" ) ) {
                            String wildCardChecking = currentChecking.substring( 0, currentChecking.length() - 1 );
                            if ( permissionIntern.startsWith( wildCardChecking ) ) {
                                if ( wildCardFound == null || currentChecking.length() > wildCardFound.length() ) {
                                    wildCardFound = currentChecking;
                                }
                            }
                        }
                    }
                }

                if ( wildCardFound != null ) {
                    val = group.get( wildCardFound );
                    this.cache.put( permissionIntern, val );
                    return val;
                }
            }

            this.cache.put( permissionIntern, false );
            return false;
        }

        return val;
    }

    @Override
    public void addGroup( Group group ) {
        this.groups.add( (PermissionGroup) group );
        this.cache.clear();
        this.dirty = true;
    }

    @Override
    public void removeGroup( Group group ) {
        this.groups.remove( group );
        this.cache.clear();
        this.dirty = true;
    }

    @Override
    public void setPermission( String permission, boolean value ) {
        this.permissions.put( permission.intern(), value );
        this.cache.clear();
        this.dirty = true;
    }

    @Override
    public void removePermission( String permission ) {
        this.permissions.removeBoolean( permission );
        this.cache.clear();
        this.dirty = true;
    }

}
