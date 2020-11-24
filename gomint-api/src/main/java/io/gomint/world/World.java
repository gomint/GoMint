/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityItemDrop;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.world.block.Block;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @author BlackyPaw
 * @version 1.0
 * @stability 2
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
     * Sets the spawn location of the world
     *
     * @param location The world's spawn location
     */
    void setSpawnLocation( Location location );

    /**
     * Gets the spawn location of the world.
     *
     * @return The world's spawn location
     */
    Location getSpawnLocation();

    /**
     * Set the difficulty of the world
     *
     * @param difficulty The world's new difficulty
     */
    void setDifficulty( Difficulty difficulty );

    /**
     * Returns the difficulty of the world
     *
     * @return The world's difficulty
     */
    Difficulty getDifficulty();

    /**
     * Get the block at that position or null if the position is not loaded in the world.
     *
     * @param pos of the position
     * @return block which has been found at that position or null
     */
    <T extends Block> T getBlockAt( BlockPosition pos );

    /**
     * Get the block at that position or air if the position is not loaded in the world.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return block which has been found at that position or air (when chunk has not been loaded)
     */
    <T extends Block> T getBlockAt( int x, int y, int z );

    /**
     * Get the block at that position or air if the position is not loaded in the world.
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param z     z coordinate
     * @param layer on which layer is this block
     * @return block which has been found at that position or air (when chunk has not been loaded)
     */
    <T extends Block> T getBlockAt( int x, int y, int z, WorldLayer layer );

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
    Collection<EntityPlayer> getPlayers();

    /**
     * Play a sound at the location given
     *
     * @param location The location where the sound should be played
     * @param sound    The sound which should be played
     * @param pitch    The pitch at which the sound should be played
     * @param data     additional data for the sound
     * @throws IllegalArgumentException when the sound data given is incorrect for the sound wanted to play
     */
    void playSound( Vector location, Sound sound, byte pitch, SoundData data );

    /**
     * Play a sound at the location given
     *
     * @param location The location where the sound should be played
     * @param sound    The sound which should be played
     * @param pitch    The pitch at which the sound should be played
     */
    void playSound( Vector location, Sound sound, byte pitch );

    /**
     * Send a particle to this world
     *
     * @param location of the particle in the client
     * @param particle which should be send
     */
    void sendParticle( Vector location, Particle particle );

    /**
     * Send a particle to this world
     *
     * @param location of the particle in the client
     * @param particle which should be send
     * @param data     data with which this particle should be send
     * @throws IllegalArgumentException when the particle data is incorrect for the particle which should be send
     */
    void sendParticle( Vector location, Particle particle, ParticleData data );

    /**
     * Get a list of bounding boxes which collide with the given box
     *
     * @param entity          When includeEntities is true this is the entity which will be exempted
     * @param bb              The bounding box which should be used to check for collision with
     * @param includeEntities Should we return blocks only or also entities? True for entities, false without entities
     * @return either a list of collisions or null
     */
    List<AxisAlignedBB> getCollisionCubes( Entity entity, AxisAlignedBB bb, boolean includeEntities );

    /**
     * Create a entity drop in the given world
     *
     * @param vector  for the item drop
     * @param itemStack which is stored inside the drop
     * @return the created and spawned entity
     */
    EntityItemDrop createItemDrop( Vector vector, ItemStack itemStack );

    /**
     * Unload this world. All remaining players in this world get called through the consumer
     *
     * @param playerConsumer which gets called for every player in this world
     */
    void unload( Consumer<EntityPlayer> playerConsumer );

    /**
     * Iterate over all loaded chunks and find the blocks specified for the blockClass.
     * This method is very expensive since it fully blocks the server until the search operation has
     * been completed.
     *
     * @param blockClass    for which we search
     * @param blockConsumer which gets called for every found block
     * @param <T>           type of block
     */
    <T extends Block> void iterateBlocks( Class<T> blockClass, Consumer<T> blockConsumer );

    /**
     * Iterate over all loaded chunks and find the entities specified for the entityClass.
     *
     * @param entityClass    for which we search
     * @param entityConsumer which gets called for every found entity
     * @param <T>            type of entity
     */
    <T extends Entity> void iterateEntities( Class<T> entityClass, Consumer<T> entityConsumer );

    /**
     * Iterate over all loaded chunks in this  world
     *
     * @param chunkConsumer which gets called for every chunk loaded
     */
    void iterateChunks( Consumer<Chunk> chunkConsumer );

    /**
     * Generate a empty chunk
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     * @return chunk with only air in it
     */
    Chunk generateEmptyChunk( int x, int z );

    /**
     * Get the chunk which has been asked
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     * @return chunk or null when not generated
     */
    Chunk getChunk( int x, int z );

    /**
     * Get or generate a chunk
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     * @return chunk
     */
    Chunk getOrGenerateChunk( int x, int z );

    /**
     * Save all data to disk
     */
    void save();

    /**
     * Get the highest block at the given coordinates
     *
     * @param x coordinate
     * @param z coordinate
     * @return highest block at the normal layer
     */
    Block getHighestBlockAt( int x, int z );

    /**
     * Get the highest block at the given coordinates. Please care this can be wrong, height calculations
     * are always done on the normal layer. So the highest block on the underwater layer may be wrong at all
     * times.
     *
     * @param x coordinate
     * @param z coordinate
     * @param layer on which we want to lookup
     * @return highest block at the normal layer
     */
    Block getHighestBlockAt( int x, int z, WorldLayer layer );

    /**
     * Unload a chunk from memory. This may trigger a save
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     */
    void unloadChunk(int x, int z);

    /**
     * Set current day/night time. Time in a world is given in 24h format (0 is midnight, 12 is noon, 18 is 6p.m. etc.)
     *
     * @param time which should be set
     */
    void setTime(Duration time);

    /**
     * Get the current world time
     *
     * @return world time
     */
    Duration getTime();

    /**
     * Get entities which have the given tag
     *
     * @param tag for which we want to search
     * @return set of entities which have the tag applied
     */
    Set<Entity> getEntitiesByTag(String tag);

}
