/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import io.gomint.entity.EntityPlayer;
import io.gomint.permission.Group;
import io.gomint.server.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PermissionManager implements io.gomint.permission.PermissionManager {

    private final EntityPlayer player;
    private final List<PermissionGroup> groups = new CopyOnWriteArrayList<>();
    private final Set<Group> dirtyGroups = new HashSet<>();
    private final Map<String, Boolean> permissions = new ConcurrentHashMap<>();
    private volatile boolean dirty = false;

    // Effective cache
    private Map<String, Boolean> cache = new ConcurrentHashMap<>();

    public PermissionManager(EntityPlayer player) {
        this.player = player;
        this.dirty = true; // Dirty by default so we only send commands once on join
    }

    /**
     * Update this permission manager
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last tick in 1/s
     */
    public void update(long currentTimeMS, float dT) {
        // Check if a group changed

        // Resend commands when something changed
        synchronized (this.dirtyGroups) {
            if (this.dirty || !this.dirtyGroups.isEmpty()) {
                // Resend commands
                this.player.sendCommands();

                this.dirty = false;
                this.dirtyGroups.clear();
            }
        }
    }

    @Override
    public boolean has(String permission) {
        return this.has(permission, false);
    }

    @Override
    public boolean has(String permission, boolean defaultValue) {
        // Check if player is op
        if (this.player != null && this.player.op()) {
            return true;
        }

        // Check if we have a cached copy
        Boolean val = this.cache.get(permission);
        if (val == null) {
            // Check player permissions first
            String wildCardFound = null;
            for (Map.Entry<String, Boolean> entry : this.permissions.entrySet()) {
                // Did we find a full permission match?
                String currentChecking = entry.getKey();
                if (permission.equals(currentChecking)) {
                    this.cache.put(permission, entry.getValue());
                    return entry.getValue();
                }

                // Check for wildcard
                if (StringUtil.endsWith(currentChecking, '*')) {
                    String wildCardChecking = currentChecking.substring(0, currentChecking.length() - 1);
                    if (permission.startsWith(wildCardChecking)) {
                        if (wildCardFound == null || currentChecking.length() > wildCardFound.length()) {
                            wildCardFound = currentChecking;
                        }
                    }
                }
            }

            // Check if we found a wildcard
            if (wildCardFound != null) {
                val = this.permissions.get(wildCardFound);
                this.cache.put(permission, val);
                return val;
            }

            // Expensive way of checking q.q (groups)
            List<PermissionGroup> reverted = new ArrayList<>(this.groups);
            Collections.reverse(reverted);

            // Iterate over all groups until we found one we can use
            for (PermissionGroup group : reverted) {
                Set<Map.Entry<String, Boolean>> playerCursor = group.cursor();
                wildCardFound = null;

                if (playerCursor != null) {
                    for (Map.Entry<String, Boolean> entry : playerCursor) {
                        // Did we find a full permission match?
                        String currentChecking = entry.getKey();
                        if (permission.equals(currentChecking)) {
                            this.cache.put(permission, entry.getValue());
                            return entry.getValue();
                        }

                        // Check for wildcard
                        if (StringUtil.endsWith(currentChecking, '*')) {
                            String wildCardChecking = currentChecking.substring(0, currentChecking.length() - 1);
                            if (permission.startsWith(wildCardChecking)) {
                                if (wildCardFound == null || currentChecking.length() > wildCardFound.length()) {
                                    wildCardFound = currentChecking;
                                }
                            }
                        }
                    }
                }

                if (wildCardFound != null) {
                    val = group.get(wildCardFound);
                    this.cache.put(permission, val);
                    return val;
                }
            }

            this.cache.put(permission, defaultValue);
            return defaultValue;
        }

        return val;
    }

    @Override
    public PermissionManager addGroup(Group group) {
        this.groups.add((PermissionGroup) group);
        ((PermissionGroup) group).addAttached(this);
        synchronized (this.dirtyGroups) {
            this.dirtyGroups.add(group);
        }
        this.cache.clear();
        return this;
    }

    @Override
    public PermissionManager removeGroup(Group group) {
        //noinspection RedundantCast
        this.groups.remove((PermissionGroup) group);
        ((PermissionGroup) group).removeAttached(this);
        synchronized (this.dirtyGroups) {
            this.dirtyGroups.remove(group);
        }
        this.cache.clear();
        return this;
    }

    @Override
    public PermissionManager permission(String permission, boolean value) {
        this.permissions.put(permission, value);
        this.cache.clear();
        this.dirty = true;
        return this;
    }

    @Override
    public PermissionManager remove(String permission) {
        this.permissions.remove(permission);
        this.cache.clear();
        this.dirty = true;
        return this;
    }

    /**
     * Notify about op toggle
     */
    @Override
    public PermissionManager toggleOp() {
        this.dirty = true;
        return this;
    }

    void markDirty(Group from) {
        synchronized (this.dirtyGroups) {
            this.dirtyGroups.add(from);
        }
    }

}
