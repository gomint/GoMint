/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.entity.EntityDespawnEvent;
import io.gomint.event.entity.EntitySpawnEvent;
import io.gomint.math.Location;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEntityMetadata;
import io.gomint.server.network.packet.PacketEntityMotion;
import io.gomint.server.network.packet.PacketEntityRelativeMovement;
import io.gomint.server.network.packet.PacketPlayerlist;
import io.gomint.server.util.Values;
import io.gomint.world.Chunk;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class that manages all entities inside a world.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EntityManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);

    private final WorldAdapter world;
    private Long2ObjectMap<Entity<?>> entitiesById;
    private Long2ObjectMap<Entity<?>> spawnedInThisTick;

    private boolean currentlyTicking;

    /**
     * Construct a new Entity manager for the given world
     *
     * @param world the world for which this manager is
     */
    EntityManager(WorldAdapter world) {
        this.world = world;
        this.entitiesById = new Long2ObjectOpenHashMap<>();
        this.spawnedInThisTick = new Long2ObjectOpenHashMap<>();
    }

    /**
     * Updates all entities managed by the EntityManager.
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last update in seconds
     */
    public synchronized void update(long currentTimeMS, float dT) {
        // --------------------------------------
        // Update all entities:
        Set<io.gomint.server.entity.Entity<?>> movedEntities = null;
        Set<io.gomint.server.entity.Entity<?>> metadataChangedEntities = null;
        LongSet removeEntities = null;

        this.currentlyTicking = true;

        if (!this.entitiesById.isEmpty()) {
            for (Long2ObjectMap.Entry<Entity<?>> entry : this.entitiesById.long2ObjectEntrySet()) {
                io.gomint.server.entity.Entity<?> entity = (io.gomint.server.entity.Entity<?>) entry.getValue();
                if (!entity.ticking()) {
                    if (entity.dead()) {
                        if (removeEntities == null) {
                            removeEntities = new LongOpenHashSet();
                        }

                        removeEntities.add(entry.getLongKey());
                    }

                    // Check if entity moved via external teleport
                    if (entity.getTransform().dirty()) {
                        ChunkAdapter current = (ChunkAdapter) entity.chunk();

                        if (movedEntities == null) {
                            movedEntities = new HashSet<>();
                        }

                        if (!(entity instanceof io.gomint.server.entity.EntityPlayer) && current != null && !current.equals(entity.chunk())) {
                            current.removeEntity(entity);
                        }

                        movedEntities.add(entity);
                    }

                    continue;
                }

                if (!entity.dead()) {
                    ChunkAdapter current = (ChunkAdapter) entity.chunk();
                    entity.update(currentTimeMS, dT);

                    if (!entity.dead()) {
                        if (entity.metadata().isDirty()) {
                            if (metadataChangedEntities == null) {
                                metadataChangedEntities = new HashSet<>();
                            }

                            metadataChangedEntities.add(entity);
                        }

                        if (entity.getTransform().dirty()) {
                            if (movedEntities == null) {
                                movedEntities = new HashSet<>();
                            }

                            if (!(entity instanceof io.gomint.server.entity.EntityPlayer) && current != null && !current.equals(entity.chunk())) {
                                current.removeEntity(entity);
                            }

                            movedEntities.add(entity);
                        }
                    }
                } else {
                    if (removeEntities == null) {
                        removeEntities = new LongOpenHashSet();
                    }

                    removeEntities.add(entry.getLongKey());
                }
            }
        }

        if (removeEntities != null && !removeEntities.isEmpty()) {
            LongIterator removeIterator = removeEntities.iterator();
            while (removeIterator.hasNext()) {
                despawnEntity(this.entitiesById.get(removeIterator.nextLong()));
            }
        }

        this.currentlyTicking = false;

        // --------------------------------------
        // Merge created entities
        this.mergeSpawnedEntities();

        // --------------------------------------
        // Metadata batches:
        this.sendMetaChanges(metadataChangedEntities, currentTimeMS);

        // --------------------------------------
        // Create movement batches:
        this.sendMovementChanges(movedEntities);
    }

    private void sendMovementChanges(Set<io.gomint.server.entity.Entity<?>> movedEntities) {
        if (movedEntities != null && !movedEntities.isEmpty()) {
            for (io.gomint.server.entity.Entity<?> movedEntity : movedEntities) {
                // Check if we need to move chunks
                Chunk chunk = movedEntity.chunk();
                if (chunk == null) {
                    int chunkX = CoordinateUtils.fromBlockToChunk((int) movedEntity.positionX());
                    int chunkZ = CoordinateUtils.fromBlockToChunk((int) movedEntity.positionZ());

                    // The entity moved in a not loaded chunk. We have two options now:
                    // 1. Load the chunk
                    // 2. Don't move the entity
                    if (this.world.server().serverConfig().loadChunksForEntities()) {
                        chunk = this.world.loadChunk(chunkX, chunkZ, true);
                    } else {
                        // "Revert" movement
                        int maxX = CoordinateUtils.getChunkMax(chunkX);
                        int minX = CoordinateUtils.getChunkMin(chunkX);
                        int maxZ = CoordinateUtils.getChunkMax(chunkZ);
                        int minZ = CoordinateUtils.getChunkMin(chunkZ);

                        // Clamp X
                        float x = movedEntity.positionX();
                        if (x > maxX) {
                            x = maxX;
                        } else if (x < minX) {
                            x = minX;
                        }

                        // Clamp Z
                        float z = movedEntity.positionX();
                        if (z > maxZ) {
                            z = maxZ;
                        } else if (z < minZ) {
                            z = minZ;
                        }

                        movedEntity.position(x, movedEntity.positionY(), z);
                        continue;
                    }
                }

                // Set the new entity
                if (!(movedEntity instanceof io.gomint.server.entity.EntityPlayer) && chunk instanceof ChunkAdapter) {
                    ChunkAdapter castedChunk = (ChunkAdapter) chunk;
                    if (!castedChunk.knowsEntity(movedEntity)) {
                        castedChunk.addEntity(movedEntity);
                    }
                }

                // Check if we need to send a full movement (we send one every second to stop eventual desync)
                boolean needsFullMovement = movedEntity.needsFullMovement();
                PacketEntityRelativeMovement relativeMovement = null;
                if (!needsFullMovement) {
                    Location old = movedEntity.oldPosition();

                    relativeMovement = new PacketEntityRelativeMovement();
                    relativeMovement.setEntityId(movedEntity.id());

                    relativeMovement.setOldX(old.x());
                    relativeMovement.setOldY(old.y());
                    relativeMovement.setOldZ(old.z());
                    relativeMovement.setX(movedEntity.positionX());
                    relativeMovement.setY(movedEntity.positionY());
                    relativeMovement.setZ(movedEntity.positionZ());

                    relativeMovement.setOldHeadYaw(old.headYaw());
                    relativeMovement.setOldYaw(old.yaw());
                    relativeMovement.setOldPitch(old.pitch());
                    relativeMovement.setHeadYaw(movedEntity.headYaw());
                    relativeMovement.setYaw(movedEntity.yaw());
                    relativeMovement.setPitch(movedEntity.pitch());
                }

                movedEntity.updateOldPosition();

                // Prepare movement packet
                Packet packetEntityMovement = movedEntity.getMovementPacket();

                PacketEntityMotion entityMotion = null;
                if (movedEntity.isMotionSendingEnabled()) {
                    entityMotion = new PacketEntityMotion();
                    entityMotion.setEntityId(movedEntity.id());
                    entityMotion.setVelocity(movedEntity.velocity());
                }

                // Check which player we need to inform about this movement
                for (io.gomint.server.entity.EntityPlayer player : this.world.getPlayers0().keySet()) {
                    if (player.connection().state() != PlayerConnectionState.PLAYING ||
                        (movedEntity instanceof io.gomint.server.entity.EntityPlayer &&
                            (player.isHidden((io.gomint.server.entity.EntityPlayer) movedEntity) || player.equals(movedEntity)))) {
                        continue;
                    }

                    player.entityVisibilityManager().updateEntity(movedEntity, chunk);
                    if (player.entityVisibilityManager().isVisible(movedEntity)) {
                        if (needsFullMovement) {
                            player.connection().addToSendQueue(packetEntityMovement);
                        } else {
                            player.connection().addToSendQueue(relativeMovement);
                        }

                        if (entityMotion != null) {
                            player.connection().addToSendQueue(entityMotion);
                        }
                    }
                }
            }
        }
    }

    private void sendMetaChanges(Set<io.gomint.server.entity.Entity<?>> metadataChangedEntities, long currentTimeMS) {
        if (metadataChangedEntities != null && !metadataChangedEntities.isEmpty()) {
            for (io.gomint.server.entity.Entity<?> entity : metadataChangedEntities) {
                int chunkX = CoordinateUtils.fromBlockToChunk((int) entity.positionX());
                int chunkZ = CoordinateUtils.fromBlockToChunk((int) entity.positionZ());

                // Create PacketEntityMetadata
                PacketEntityMetadata packetEntityMetadata = new PacketEntityMetadata();
                packetEntityMetadata.setEntityId(entity.id());
                packetEntityMetadata.setMetadata(entity.metadata());
                packetEntityMetadata.setTick(currentTimeMS / (int) Values.CLIENT_TICK_MS);

                // Send to all players
                for (io.gomint.server.entity.EntityPlayer entityPlayer : this.world.getPlayers0().keySet()) {
                    if (entityPlayer.connection().state() != PlayerConnectionState.PLAYING ||
                        (entity instanceof io.gomint.server.entity.EntityPlayer &&
                            entityPlayer.isHidden((io.gomint.server.entity.EntityPlayer) entity))) {
                        continue;
                    }

                    Chunk playerChunk = entityPlayer.chunk();
                    if (Math.abs(playerChunk.x() - chunkX) <= entityPlayer.viewDistance() &&
                        Math.abs(playerChunk.z() - chunkZ) <= entityPlayer.viewDistance()) {
                        entityPlayer.connection().addToSendQueue(packetEntityMetadata);
                    }
                }
            }
        }
    }

    private void mergeSpawnedEntities() {
        if (!this.spawnedInThisTick.isEmpty()) {
            for (Long2ObjectMap.Entry<Entity<?>> entry : this.spawnedInThisTick.long2ObjectEntrySet()) {
                this.entitiesById.put(entry.getLongKey(), entry.getValue());
            }

            this.spawnedInThisTick.clear();
        }
    }

    /**
     * Gets an entity given its unique ID.
     *
     * @param entityId The entity's unique ID
     * @return The entity if found or null otherwise
     */
    public synchronized Entity<?> findEntity(long entityId) {
        Entity<?> entity = this.entitiesById.get(entityId);
        if (entity == null) {
            return this.spawnedInThisTick.get(entityId);
        }

        return entity;
    }

    /**
     * Spawns the given entity at the specified position.
     *
     * @param entity    The entity to spawn
     * @param positionX The x coordinate to spawn the entity at
     * @param positionY The y coordinate to spawn the entity at
     * @param positionZ The z coordinate to spawn the entity at
     */
    public synchronized void spawnEntityAt(Entity<?> entity, float positionX, float positionY, float positionZ) {
        this.spawnEntityAt(entity, positionX, positionY, positionZ, 0.0F, 0.0F);
    }

    /**
     * Spawns the given entity at the specified position with the specified rotation.
     *
     * @param entity    The entity to spawn
     * @param positionX The x coordinate to spawn the entity at
     * @param positionY The y coordinate to spawn the entity at
     * @param positionZ The z coordinate to spawn the entity at
     * @param yaw       The yaw value of the entity ; will be applied to both the entity's body and head
     * @param pitch     The pitch value of the entity
     */
    public synchronized void spawnEntityAt(Entity<?> entity, float positionX, float positionY, float positionZ, float yaw, float pitch) {
        // Give a entity spawn event around
        EntitySpawnEvent event = this.world.server().pluginManager().callEvent(new EntitySpawnEvent(entity));
        if (event.cancelled()) {
            return;
        }

        // Only allow server implementations
        if (!(entity instanceof io.gomint.server.entity.Entity)) {
            return;
        }

        io.gomint.server.entity.Entity<?> cEntity = (io.gomint.server.entity.Entity<?>) entity;

        // Set the position and yaw
        cEntity.position(positionX, positionY, positionZ);
        cEntity.yaw(yaw);
        cEntity.headYaw(yaw);
        cEntity.pitch(pitch);

        if (this.currentlyTicking) {
            this.spawnedInThisTick.put(entity.id(), entity);
        } else {
            this.entitiesById.put(entity.id(), entity);
        }

        // Register to the correct chunk
        Chunk chunk = cEntity.chunk();
        if (chunk == null) {
            int chunkX = CoordinateUtils.fromBlockToChunk((int) cEntity.positionX());
            int chunkZ = CoordinateUtils.fromBlockToChunk((int) cEntity.positionZ());
            chunk = this.world.loadChunk(chunkX, chunkZ, true);
        }

        // Set the new entity
        if (chunk instanceof ChunkAdapter) {
            ChunkAdapter castedChunk = (ChunkAdapter) chunk;
            if (!castedChunk.knowsEntity(cEntity)) {
                castedChunk.addEntity(cEntity);
            }
        }

        // If this is a player send full playerlist
        if (entity instanceof io.gomint.server.entity.EntityPlayer) {
            io.gomint.server.entity.EntityPlayer entityPlayer = (io.gomint.server.entity.EntityPlayer) entity;
            PacketPlayerlist playerlist = null;

            // Remap all current living entities
            for (EntityPlayer player : entityPlayer.world().server().onlinePlayers()) {
                if (!player.isHidden(entityPlayer) && !player.equals(entityPlayer)) {
                    if (playerlist == null) {
                        playerlist = new PacketPlayerlist();
                        playerlist.setMode((byte) 0);
                        playerlist.setEntries(new ArrayList<PacketPlayerlist.Entry>() {{
                            add(new PacketPlayerlist.Entry(entityPlayer));
                        }});
                    }

                    ((io.gomint.server.entity.EntityPlayer) player).connection().send(playerlist);
                }
            }
        }

        // Check which player we need to inform about this movement
        for (io.gomint.server.entity.EntityPlayer entityPlayer : this.world.getPlayers0().keySet()) {
            if (entity instanceof io.gomint.server.entity.EntityPlayer && (entityPlayer.isHidden((EntityPlayer) entity) || entityPlayer.equals(entity))) {
                LOGGER.debug("Skipping spawning of {} for {} (is hidden or same entity)", entity, entityPlayer.name());
                continue;
            }

            Chunk playerChunk = entityPlayer.chunk();
            if (Math.abs(playerChunk.x() - chunk.x()) <= entityPlayer.viewDistance() &&
                Math.abs(playerChunk.z() - chunk.z()) <= entityPlayer.viewDistance()) {

                LOGGER.debug("Spawning {} would be in distance for {}", entity, entityPlayer.name());

                if (((io.gomint.server.entity.Entity<?>) entity).canSee(entityPlayer)) {
                    entityPlayer.entityVisibilityManager().addEntity(entity);
                }
            }
        }

        LOGGER.debug("Spawning entity {} at {}", entity.getClass(), entity.location());
    }

    /**
     * Despawns an entity
     *
     * @param entity The entity which should be despawned
     */
    public synchronized void despawnEntity(Entity<?> entity) {
        // Only allow server implementations
        if (!(entity instanceof io.gomint.server.entity.Entity)) {
            return;
        }

        io.gomint.server.entity.Entity<?> cEntity = (io.gomint.server.entity.Entity<?>) entity;

        // Inform all others
        EntityDespawnEvent entityDespawnEvent = new EntityDespawnEvent(entity);
        this.world.server().pluginManager().callEvent(entityDespawnEvent);

        // Remove from chunk
        Chunk chunk = cEntity.chunk();
        if (chunk instanceof ChunkAdapter) {
            ((ChunkAdapter) chunk).removeEntity(cEntity);
        }

        // Broadcast entity despawn
        for (EntityPlayer player : this.world.onlinePlayers()) {
            if (player instanceof io.gomint.server.entity.EntityPlayer) {
                ((io.gomint.server.entity.EntityPlayer) player).entityVisibilityManager().removeEntity(entity);
            }
        }

        // Remove from maps
        this.entitiesById.remove(entity.id());
        this.spawnedInThisTick.remove(entity.id());
    }

    public synchronized void addFromChunk(Long2ObjectMap<Entity<?>> entities) {
        for (Long2ObjectMap.Entry<Entity<?>> entry : entities.long2ObjectEntrySet()) {
            this.entitiesById.put(entry.getLongKey(), entry.getValue());
        }
    }

    public Set<Entity<?>> findEntities(String tag) {
        Set<Entity<?>> entities = null;
        for ( Long2ObjectMap.Entry<Entity<?>> entry : this.entitiesById.long2ObjectEntrySet() ) {
            Entity<?> entity = entry.getValue();
            if (entity.tags().contains(tag)) {
                if (entities == null) {
                    entities = new HashSet<>();
                }

                entities.add(entity);
            }
        }

        return entities;
    }

}
