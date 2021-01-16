/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin;

/**
 * Set of possible priorities of plugin initialization order.
 * The default is {@link #LOAD}.
 *
 * @author BlackyPaw
 * @version 1.0
 * @stability 3
 */
public enum StartupPriority implements Comparable<StartupPriority> {

    /**
     * Load a plugin on startup
     */
    STARTUP( 0 ),

    /**
     * Load a plugin on load of the startup ones. The plugins with this StartupPriority get loaded after {@link #STARTUP}
     */
    LOAD( 1 );

    private final int order;

    /**
     * Just a enum Constructor
     *
     * @param order which defines the sort order (ASC)
     */
    StartupPriority( final int order ) {
        this.order = order;
    }

    public int order() {
        return order;
    }

}
