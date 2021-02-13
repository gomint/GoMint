/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.permission;

import io.gomint.permission.Group;
import io.gomint.permission.GroupManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PermissionGroupManager implements GroupManager {

    private Map<String, Group> groupMap = null;

    @Override
    public Group group(String name) {
        // Check if this is the first group we get/create
        if (this.groupMap == null) {
            this.groupMap = new ConcurrentHashMap<>();

            PermissionGroup group = new PermissionGroup(this, name);
            this.groupMap.put(name, group);
            return group;
        }

        Group group = this.groupMap.get(name);
        if (group == null) {
            group = new PermissionGroup(this, name);
            this.groupMap.put(name, group);
        }

        return group;
    }

    @Override
    public GroupManager remove(Group group) {
        this.groupMap.remove(group.name());
        return this;
    }

}
