/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.permission;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * The group manager is a global defined manager which manages permission groups. You can define groups which contain
 * certain permission settings. This will help permission plugins attach permission containers to players quickly
 * without the need of loading / creating duplicate data entries.
 */
public interface GroupManager {

    /**
     * Get or create a group named name
     *
     * @param name for the group
     * @return new or existing group for the name
     */
    Group group(String name );

    /**
     * Remove given group from the group manager. This does not remove references from {@link PermissionManager}
     * instances. To fully remove a group from the Java Heap you have to call {@link PermissionManager#removeGroup(Group)}
     * on any permission manager who has this group attached
     *
     * @param group which should be removed
     */
    GroupManager remove(Group group );

}
