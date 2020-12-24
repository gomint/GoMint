/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.util.tick.Tickable;
import io.gomint.world.Chunk;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntitySpawner implements Tickable {

    private enum EntitySpawnTypes {
        MONSTER(EntityTags.TAG_MOB, 70),
        CREATURE(EntityTags.TAG_ANIMAL, 10),
        AMBIENT(EntityTags.TAG_CREATURE, 15),
        WATER_CREATURE(EntityTags.TAG_WATER_CREATURE, 5);

        private final String tag;
        private final int spawnLimit;

        EntitySpawnTypes(String tag, int spawnLimit) {
            this.tag = tag;
            this.spawnLimit = spawnLimit;
        }
    }

    private static final int CHUNK_SPAWN_RADIUS = 8;
    private static final int MOB_COUNT_DIV = (int) Math.pow(17.0D, 2.0D);
    private static final Logger LOGGER = LoggerFactory.getLogger(EntitySpawner.class);
    private final WorldAdapter world;

    /**
     * Construct a new entity spawner for the given world
     *
     * @param world for which this spawner should spawn entities
     */
    public EntitySpawner(WorldAdapter world) {
        this.world = world;
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        // ---
        // Get all chunks in which entities can be spawned
        LongSet spawnableChunks = new LongOpenHashSet();

        // Get all players in that world
        Object2ObjectMap.FastEntrySet<io.gomint.server.entity.EntityPlayer, ChunkAdapter> entrySet =
            (Object2ObjectMap.FastEntrySet<EntityPlayer, ChunkAdapter>) this.world.getPlayers0().object2ObjectEntrySet();
        ObjectIterator<Object2ObjectMap.Entry<EntityPlayer, ChunkAdapter>> iterator = entrySet.fastIterator();
        while (iterator.hasNext()) {
            Object2ObjectMap.Entry<EntityPlayer, ChunkAdapter> entry = iterator.next();
            ChunkAdapter playerChunk = entry.getValue();
            EntityPlayer player = entry.getKey();

            // We get all chunks around them which can be selected to host entities
            for (int x = -CHUNK_SPAWN_RADIUS; x <= CHUNK_SPAWN_RADIUS; ++x) {
                for (int z = -CHUNK_SPAWN_RADIUS; z <= CHUNK_SPAWN_RADIUS; ++z) {
                    boolean borderChunk = x == -CHUNK_SPAWN_RADIUS || x == CHUNK_SPAWN_RADIUS ||
                        z == -CHUNK_SPAWN_RADIUS || z == CHUNK_SPAWN_RADIUS;

                    long chunkHash = CoordinateUtils.toLong(x + playerChunk.getX(), z + playerChunk.getZ());
                    if (!spawnableChunks.contains(chunkHash)) {
                        if (!borderChunk) {
                            if (player.knowsChunk(x + playerChunk.getX(), z + playerChunk.getZ())) {
                                spawnableChunks.add(chunkHash);
                            }
                        }
                    }
                }
            }
        }

        for (EntitySpawnTypes value : EntitySpawnTypes.values()) {
            Set<Entity> entities = this.world.getEntitiesByTag(value.tag);

            int amountOfSpawnedEntities = entities == null ? 0 : entities.size();
            int amountOfSpawnableEntities = value.spawnLimit * spawnableChunks.size() / MOB_COUNT_DIV;

            if (amountOfSpawnedEntities <= amountOfSpawnableEntities) {
                for (long spawnableChunk : spawnableChunks) {
                    Chunk chunk = this.world.getChunk(spawnableChunk);
                    if (chunk != null) {
                        // Block spawnBlock = getRandomChunkBlock(spawnableChunk);
                        // LOGGER.info("Spawning block: {}", spawnBlock.getBlockType());
                    }
                }
            }
        }
    }

    private static Block getRandomChunkBlock(Chunk chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();

        int i = x * 16 + ThreadLocalRandom.current().nextInt(16);
        int j = z * 16 + ThreadLocalRandom.current().nextInt(16);
        return chunk.getWorld().getHighestBlockAt(i, j).getSide(Facing.UP);
    }

}
