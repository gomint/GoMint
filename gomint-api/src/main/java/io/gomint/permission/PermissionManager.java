package io.gomint.permission;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface PermissionManager {

    /**
     * Check if this permission manager has the permission. Permissions are sorted this way:
     * <p>
     * - EntityPlayer permission
     * - Group permission (reversed insert order)
     * <p>
     * For example you have a player with group A and group B, group A has set permission "testpermission" to true
     * group B has set "testpermission" to false. You inserted it with {@link PermissionManager#addGroup(Group)}
     * A first, second B you will get false as result.
     * <p>
     * When you insert A second you will get true as result, same when you use {@link PermissionManager#permission(String, boolean)}
     * which adds a player based override.
     * <p>
     * This manager also supports basic usage of the astrix wildcard (*). In the case of wildcard checking, all full
     * written permissions will be checked first when there is a result wildcards will be skipped. When colliding wildcard
     * permissions has been found (like "test.*": true, "test.command.*": false, testing for "test.command.test") the
     * wildcard with the greater length will be used.
     *
     * @param permission asked for
     * @return true if this manager has the permission, false if not
     */
    boolean has(String permission );

    /**
     * Same behaviour as {@link #has(String)} but with another return behaviour
     *
     * @param permission asked for
     * @param defaultValue which will be returned when no permission setting has been found
     * @return true if this manager has the permission, defaultValue if not
     */
    boolean has(String permission, boolean defaultValue );

    /**
     * Add a new permission group to this manager
     *
     * @param group which should be added
     */
    PermissionManager addGroup( Group group );

    /**
     * Remove a group from the manager
     *
     * @param group which should be removed
     */
    PermissionManager removeGroup( Group group );

    /**
     * Add a manager permission. This is the highest override level you can get
     *
     * @param permission which should be set
     * @param value      for the permission, true for grant, false for revoke
     */
    PermissionManager permission(String permission, boolean value );

    /**
     * Remove permission from this manager
     *
     * @param permission which should be removed
     */
    PermissionManager remove(String permission );

    /**
     * Toggle the op state of this permission manager
     */
    PermissionManager toggleOp();

}
