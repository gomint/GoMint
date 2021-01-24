/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test.permission;

import io.gomint.server.permission.PermissionGroup;
import io.gomint.server.permission.PermissionGroupManager;
import io.gomint.server.permission.PermissionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PermissionTest {

    @Test
    public void groupTest() {
        PermissionGroupManager manager = new PermissionGroupManager();
        PermissionGroup group = (PermissionGroup) manager.group("test");
        group.permission("test", true);

        PermissionManager permissionManager = new PermissionManager(null);
        permissionManager.addGroup(group);

        Assertions.assertTrue(permissionManager.has("test"));
        Assertions.assertFalse(permissionManager.has("test1"));
    }

    @Test
    public void wildcardTest() {
        PermissionGroupManager manager = new PermissionGroupManager();
        PermissionGroup group = (PermissionGroup) manager.group("test");
        group.permission("test*", true);

        PermissionManager permissionManager = new PermissionManager(null);
        permissionManager.addGroup(group);

        Assertions.assertTrue(permissionManager.has("test"));
        Assertions.assertTrue(permissionManager.has("test1"));
    }

    @Test
    public void wildcardNegateTest() {
        PermissionGroupManager manager = new PermissionGroupManager();
        PermissionGroup group = (PermissionGroup) manager.group("test");
        group.permission("test*", true);

        PermissionManager permissionManager = new PermissionManager(null);
        permissionManager.addGroup(group);
        permissionManager.permission("test2", false);

        Assertions.assertTrue(permissionManager.has("test"));
        Assertions.assertTrue(permissionManager.has("test1"));
        Assertions.assertFalse(permissionManager.has("test2"));
    }

}
