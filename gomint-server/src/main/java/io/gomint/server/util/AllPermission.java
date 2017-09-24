package io.gomint.server.util;

import sun.security.util.SecurityConstants;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AllPermission extends PermissionCollection implements java.io.Serializable {

    /**
     * Adds a permission to the AllPermissions. The key for the hash is
     * permission.path.
     *
     * @param permission the Permission object to add.
     * @throws IllegalArgumentException - if the permission is not a
     *                                  AllPermission
     * @throws SecurityException        - if this AllPermissionCollection object
     *                                  has been marked readonly
     */
    public void add( Permission permission ) {
        if ( isReadOnly() ) {
            throw new SecurityException( "attempt to add a Permission to a readonly PermissionCollection" );
        }
    }

    /**
     * Check and see if this set of permissions implies the permissions
     * expressed in "permission".
     *
     * @param permission the Permission object to compare
     * @return always returns true.
     */
    public boolean implies( Permission permission ) {
        return true;
    }

    /**
     * Returns an enumeration of all the AllPermission objects in the
     * container.
     *
     * @return an enumeration of all the AllPermission objects.
     */
    public Enumeration<Permission> elements() {
        return new Enumeration<Permission>() {
            private boolean hasMore = true;

            public boolean hasMoreElements() {
                return hasMore;
            }

            public Permission nextElement() {
                hasMore = false;
                return SecurityConstants.ALL_PERMISSION;
            }
        };
    }

}
