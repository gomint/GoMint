/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.permission;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Group {

    /**
     * Get the name of this group
     *
     * @return name of this group
     */
    String getName();

    /**
     * Set a permission for this group. For further documentation on how permissions are checked
     * see {@link PermissionManager#hasPermission(String)}.
     *
     * @param permission which should be set
     * @param value      of this permission, true when granted, false when revoked
     */
    void setPermission( String permission, boolean value );

    /**
     * Remove a permission completely
     *
     * @param permission which should be removed
     */
    void removePermission( String permission );

    /**
     * Get a iterator for all configured permissions
     *
     * @return new iterator for this group
     */
    ObjectSet<Object2BooleanMap.Entry<String>> cursor();

}
