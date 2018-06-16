/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BossBar {

    /**
     * Add a player to this boss bar. This has to be done manually for
     * all non boss entities.
     *
     * @param player which should be added to this bossbar
     */
    void addPlayer( EntityPlayer player );

    /**
     * Remove a player from the boss bar. his has to be done manually for
     * all non boss entities.
     *
     * @param player which should be removed from this bossbar
     */
    void removePlayer( EntityPlayer player );

    /**
     * Set the title of this boss bar
     *
     * @param title of the boss bar
     */
    void setTitle( String title );

    /**
     * Get the title of this boss bar
     *
     * @return title of the boss bar
     */
    String getTitle();

}
