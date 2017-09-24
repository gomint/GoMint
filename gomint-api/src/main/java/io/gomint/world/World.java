/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

import io.gomint.entity.Player;
import io.gomint.entity.passive.EntityItemDrop;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.world.block.Block;

import java.util.Collection;
import java.util.List;

/**
 * @author geNAZt
 * @author BlackyPaw
 * @version 1.0
 */
public interface World {

    /**
     * Gets the name of the world itself, i.e. the name of the directory it is stored in.
     *
     * @return The name of the world itself
     */
    String getWorldName();

    /**
     * Gets the level name of the world. This is the name found inside the NBT data of a world.
     *
     * @return The level name of the world
     */
    String getLevelName();

    /**
     * Gets the spawn location of the world.
     *
     * @return The world's spawn location
     */
    Location getSpawnLocation();

    /**
     * Get the block at that position or null if the position is not loaded in the world.
     *
     * @param pos of the position
     * @return block which has been found at that position or null
     */
    <T extends Block> T getBlockAt( BlockPosition pos );

    /**
     * Get the block at that position or null if the position is not loaded in the world.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return block which has been found at that position or null
     */
    <T extends Block> T getBlockAt( int x, int y, int z );

    /**
     * Gets the value of the given gamerule set for this world.
     *
     * @param gamerule The gamerule to get a value for
     * @param <T>      The generic type of the gamerule
     * @return The value of the gamerule
     */
    <T> T getGamerule( Gamerule<T> gamerule );

    /**
     * Sets the value of the given gamerule for this world
     *
     * @param gamerule The gamerule to set a value for
     * @param value    The value which should be set
     * @param <T>      The generic type of the gamerule
     */
    <T> void setGamerule( Gamerule<T> gamerule, T value );

    /**
     * Get a collection of players which are online on this world
     *
     * @return Collection of players online on this world
     */
    Collection<Player> getPlayers();

    /**
     * Play a sound at the location given
     *
     * @param location  The location where the sound should be played
     * @param sound     The sound which should be played
     * @param pitch     The pitch at which the sound should be played
     * @param extraData Any extra data for the client to select the correct sound
     */
    void playSound( Location location, Sound sound, byte pitch, int extraData );

    /**
     * Get a list of bounding boxes which collide with the given box
     *
     * @param entity          When includeEntities is true this is the entity which will be exempted
     * @param bb              The bounding box which should be used to check for collision with
     * @param includeEntities Should we return blocks only or also entities? True for entities, false without entities
     * @return either a list of collisions or null
     */
    List<AxisAlignedBB> getCollisionCubes( io.gomint.entity.Entity entity, AxisAlignedBB bb, boolean includeEntities );

    EntityItemDrop createItemDrop( Location location, ItemStack itemStack );

}
