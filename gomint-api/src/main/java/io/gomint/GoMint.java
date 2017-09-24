/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import io.gomint.entity.Player;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.World;

import java.util.Collection;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public interface GoMint {

    /**
     * Get the server's message of the day (MOTD)
     *
     * @return The server's current MOTD
     */
    String getMotd();

    /**
     * Sets the server's message of the day (MOTD)
     *
     * @param motd The MOTD to be set
     */
    void setMotd( String motd );

    /**
     * Get a world by its name. When the world is not loaded it will be tried to load
     *
     * @param name The name of the world
     * @return the world or null if there was a error loading it
     */
    World getWorld( String name );

    /**
     * Create a new itemstack with the given item in it
     *
     * @param itemClass which should be used to create
     * @param amount    of items in the new created stack
     * @param <T>       generic type of the itemstack
     * @return fresh generated itemstack of given type with amount of items
     */
    <T extends ItemStack> T createItemStack( Class<T> itemClass, int amount );

    /**
     * Get a collection of all players on this server
     *
     * @return collection of online players
     */
    Collection<Player> getPlayers();

    /**
     * Get the GoMint server instance currently running
     *
     * @return the started GoMint server instance
     */
    static GoMint instance() {
        return GoMintInstanceHolder.instance;
    }


}
