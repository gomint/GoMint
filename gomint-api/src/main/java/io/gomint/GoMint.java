/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.gui.ButtonList;
import io.gomint.gui.CustomForm;
import io.gomint.gui.Modal;
import io.gomint.inventory.item.ItemStack;
import io.gomint.permission.GroupManager;
import io.gomint.player.PlayerSkin;
import io.gomint.plugin.PluginManager;
import io.gomint.scoreboard.Scoreboard;
import io.gomint.world.World;
import io.gomint.world.Chunk;
import io.gomint.world.block.Block;
import io.gomint.world.generator.ChunkGeneratorRegistry;
import io.gomint.world.generator.CreateOptions;

import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 * @stability 3
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
     * Create a new entity
     *
     * @param entityClass which should be created
     * @param <T>         generic type of the entity
     * @return fresh generated entity
     */
    <T extends Entity> T createEntity( Class<T> entityClass );

    /**
     * Get a collection of all players on this server
     *
     * @return collection of online players
     */
    Collection<EntityPlayer> getPlayers();

    /**
     * Get the manager which manages permission groups
     *
     * @return permission group manager
     */
    GroupManager getGroupManager();

    /**
     * Get the GoMint server instance currently running
     *
     * @return the started GoMint server instance
     */
    static GoMint instance() {
        return GoMintInstanceHolder.instance;
    }

    /**
     * Find a player by its name
     *
     * @param target which we want to search
     * @return the player or null if not found
     */
    EntityPlayer findPlayerByName( String target );

    /**
     * Find a player by its uuid
     *
     * @param target which we want to search
     * @return the player or null if not found
     */
    EntityPlayer findPlayerByUUID( UUID target );

    /**
     * Get the plugin manager
     *
     * @return the plugin manager
     */
    PluginManager getPluginManager();

    /**
     * Get the port this server has bound to
     *
     * @return port of this server
     */
    int getPort();

    /**
     * Get the amount of player which will fit on this server before it start declining logins
     *
     * @return amount of maximum players
     */
    int getMaxPlayers();

    /**
     * Get current tickrate
     *
     * @return tickrate of this server
     */
    double getTPS();

    /**
     * Shutdown this server
     */
    void shutdown();

    /**
     * Create new button list for form display
     *
     * @param title of the button list
     * @return button list implementation
     */
    ButtonList createButtonList( String title );

    /**
     * Create a new modal for form display
     *
     * @param title    of the modal
     * @param question for the client
     * @return modal implementation
     */
    Modal createModal( String title, String question );

    /**
     * Create new custom form for form display
     *
     * @param title of the custom form
     * @return custom form implementation
     */
    CustomForm createCustomForm( String title );

    /**
     * Check if current thread is GoMints main thread
     *
     * @return true if main thread, false if not
     */
    boolean isMainThread();

    /**
     * Create a player skin from the given input stream
     *
     * @param inputStream which should be read
     * @return skin or null on error
     */
    PlayerSkin createPlayerSkin( InputStream inputStream );

    /**
     * Get the empty player skin
     *
     * @return empty player skin
     */
    PlayerSkin getEmptyPlayerSkin();

    /**
     * Get the default world of this server
     *
     * @return default world
     */
    World getDefaultWorld();

    /**
     * Set a new default world for this server
     *
     * @param world which should be used as default one
     */
    void setDefaultWorld( World world );

    /**
     * Create a empty block to be placed into the world with {@link Block#copyFromBlock(Block)} or
     * {@link Chunk#setBlock(int, int, int, Block)}
     *
     * @param blockClass class of the block we want to create
     * @param <T>        type of block which the target object should have
     * @return empty, not configured block
     */
    <T extends Block> T createBlock( Class<T> blockClass );

    /**
     * Create a new world with the given options
     *
     * @param name    of the new world
     * @param options which should be used to generate the world
     * @return new world
     */
    World createWorld( String name, CreateOptions options );

    /**
     * Get the chunk generator registry
     *
     * @return the chunk generator registry
     */
    ChunkGeneratorRegistry getChunkGeneratorRegistry();

    /**
     * Get the internal version of this server. This contains a git hash so its different on each build
     *
     * @return version of this server
     */
    String getVersion();

    /**
     * Dispatch a command as console
     *
     * @param line which should be executed (without the /)
     */
    void dispatchCommand( String line );

    /**
     * Get a collection of all worlds on this server
     *
     * @return collection of worlds
     */
    Collection<World> getWorlds();

    /**
     * Create a new scoreboard
     *
     * @return new scoreboard
     */
    Scoreboard createScoreboard();

}
