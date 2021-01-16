/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test;

import io.gomint.entity.EntityPlayer;
import io.gomint.permission.Group;
import io.gomint.server.permission.PermissionGroupManager;
import io.gomint.server.permission.PermissionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TestPermissions {

    private EntityPlayer mockPlayer(boolean op) {
        final EntityPlayer player = mock(EntityPlayer.class);
        when(player.op()).thenReturn(op);
        return player;
    }

    @Test
    public void testNormal() {
        PermissionManager permissionManager = new PermissionManager(mockPlayer(false));
        permissionManager.permission("test.permission.shouldbetrue", true);
        permissionManager.permission("test.permission.shouldfalseforreal", false);

        Assertions.assertTrue(permissionManager.has("test.permission.shouldbetrue"), "Wildcard is false");
        Assertions.assertFalse(permissionManager.has("test.permission.shouldfalseforreal"), "Wildcard is true");
    }

    @Test
    public void testWildcard() {
        PermissionManager permissionManager = new PermissionManager(mockPlayer(false));
        permissionManager.permission("test.permission.*", true);
        permissionManager.permission("test.permission.shouldfalse*", false);
        permissionManager.permission("test.permission.shouldfalseforrealno", true);

        Assertions.assertTrue(permissionManager.has("test.permission.shouldbetrue"), "Wildcard is false");
        Assertions.assertFalse(permissionManager.has("test.permission.shouldfalseforreal"), "Wildcard is true");
        Assertions.assertTrue(permissionManager.has("test.permission.shouldfalseforrealno"), "Wildcard is false");
    }

    @Test
    public void testOneGroup() {
        PermissionGroupManager permissionGroupManager = new PermissionGroupManager();
        Group groupA = permissionGroupManager.group("A");
        groupA.permission("test.permission.*", true);
        groupA.permission("test.permission.shouldfalse*", false);
        groupA.permission("test.permission.shouldfalseforrealno", true);

        PermissionManager permissionManager = new PermissionManager(mockPlayer(false));
        permissionManager.addGroup(groupA);

        Assertions.assertTrue(permissionManager.has("test.permission.shouldbetrue"), "Wildcard is false");
        Assertions.assertFalse(permissionManager.has("test.permission.shouldfalseforreal"), "Wildcard is true");
        Assertions.assertTrue(permissionManager.has("test.permission.shouldfalseforrealno"), "Wildcard is false");
    }

    @Test
    public void testGroupOverride() {
        PermissionGroupManager permissionGroupManager = new PermissionGroupManager();

        Group groupA = permissionGroupManager.group("A");
        groupA.permission("test.permission.*", false);
        groupA.permission("test.permission.shouldfalse*", true);
        groupA.permission("test.permission.shouldfalseforrealno", false);

        Group groupB = permissionGroupManager.group("B");
        groupB.permission("test.permission.*", true);
        groupB.permission("test.permission.shouldfalse*", false);
        groupB.permission("test.permission.shouldfalseforrealno", true);

        PermissionManager permissionManager = new PermissionManager(mockPlayer(false));
        permissionManager.addGroup(groupA);
        permissionManager.addGroup(groupB);

        Assertions.assertTrue(permissionManager.has("test.permission.shouldbetrue"), "Wildcard is false");
        Assertions.assertFalse(permissionManager.has("test.permission.shouldfalseforreal"), "Wildcard is true");
        Assertions.assertTrue(permissionManager.has("test.permission.shouldfalseforrealno"), "Wildcard is false");
    }

    @Test
    public void testOp() {
        final PermissionManager permissionManager = new PermissionManager(mockPlayer(true));
        permissionManager.permission("test.permission.*", false);
        permissionManager.permission("test.permission.false*", false);
        permissionManager.permission("test.permission.true", true);
        permissionManager.permission("test.permission.falseforreal", false);
        permissionManager.permission("test.permission.alsofalse", false);

        Assertions.assertTrue(permissionManager.has("test.otherpermission"));
        Assertions.assertTrue(permissionManager.has("test.permission"));
        Assertions.assertTrue(permissionManager.has("test.permission.someotherperm"));
        Assertions.assertTrue(permissionManager.has("test.permission.shouldfalseforreal"));
        Assertions.assertTrue(permissionManager.has("test.permission.true"));
        Assertions.assertTrue(permissionManager.has("test.permission.falseforreal"));
        Assertions.assertTrue(permissionManager.has("test.permission.alsofalse"));
        Assertions.assertTrue(permissionManager.has("test.permission.false123"));
    }
}
