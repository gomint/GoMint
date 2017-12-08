/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import com.koloboke.collect.map.LongObjCursor;
import com.koloboke.function.LongObjConsumer;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.server.network.packet.*;
import io.gomint.server.util.collection.EntityIDMap;
import io.gomint.world.Chunk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper class that manages all entities inside a world.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EntityManager {

    private final WorldAdapter world;
    private EntityIDMap entitiesById;
    private EntityIDMap spawnedInThisTick;

    private boolean currentlyTicking;

    /**
     * Construct a new Entity manager for the given world
     *
     * @param world the world for which this manager is
     */
    EntityManager( WorldAdapter world ) {
        this.world = world;
        this.entitiesById = EntityIDMap.withExpectedSize( 20 );
        this.spawnedInThisTick = EntityIDMap.withExpectedSize( 20 );
    }

    /**
     * Updates all entities managed by the EntityManager.
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last update in seconds
     */
    public void update( long currentTimeMS, float dT ) {
        // --------------------------------------
        // Update all entities:
        Set<io.gomint.server.entity.Entity> movedEntities = null;
        Set<io.gomint.server.entity.Entity> metadataChangedEntities = null;
        this.currentlyTicking = true;

        LongObjCursor<Entity> cursor = this.entitiesById.cursor();
        while ( cursor.moveNext() ) {
            io.gomint.server.entity.Entity entity = (io.gomint.server.entity.Entity) cursor.value();
            if ( !entity.isDead() ) {
                ChunkAdapter current = (ChunkAdapter) entity.getChunk();
                entity.update( currentTimeMS, dT );

                if ( !entity.isDead() ) {
                    if ( entity.getMetadata().isDirty() ) {
                        if ( metadataChangedEntities == null ) {
                            metadataChangedEntities = new HashSet<>();
                        }

                        metadataChangedEntities.add( entity );
                    }

                    if ( entity.getTransform().isDirty() ) {
                        if ( movedEntities == null ) {
                            movedEntities = new HashSet<>();
                        }

                        if ( !( entity instanceof io.gomint.server.entity.EntityPlayer ) && current != null && !current.equals( entity.getChunk() ) ) {
                            current.removeEntity( entity );
                        }

                        movedEntities.add( entity );
                    }
                }
            } else {
                cursor.remove();
                despawnEntity( entity );
            }
        }

        this.currentlyTicking = false;

        // --------------------------------------
        // Merge created entities
        this.mergeSpawnedEntities();

        // --------------------------------------
        // Metadata batches:
        this.sendMetaChanges( metadataChangedEntities );

        // --------------------------------------
        // Create movement batches:
        this.sendMovementChanges( movedEntities );
    }

    private void sendMovementChanges( Set<io.gomint.server.entity.Entity> movedEntities ) {
        if ( movedEntities != null && movedEntities.size() > 0 ) {
            for ( io.gomint.server.entity.Entity movedEntity : movedEntities ) {
                // Check if we need to move chunks
                Chunk chunk = movedEntity.getChunk();
                if ( chunk == null ) {
                    int chunkX = CoordinateUtils.fromBlockToChunk( (int) movedEntity.getPositionX() );
                    int chunkZ = CoordinateUtils.fromBlockToChunk( (int) movedEntity.getPositionZ() );

                    // The entity moved in a not loaded chunk. We have two options now:
                    // 1. Load the chunk
                    // 2. Don't move the entity
                    if ( this.world.getServer().getServerConfig().isLoadChunksForEntities() ) {
                        chunk = this.world.loadChunk( chunkX, chunkZ, true );
                    } else {
                        // "Revert" movement
                        int maxX = CoordinateUtils.getChunkMax( chunkX );
                        int minX = CoordinateUtils.getChunkMin( chunkX );
                        int maxZ = CoordinateUtils.getChunkMax( chunkZ );
                        int minZ = CoordinateUtils.getChunkMin( chunkZ );

                        // Clamp X
                        float x = movedEntity.getPositionX();
                        if ( x > maxX ) {
                            x = maxX;
                        } else if ( x < minX ) {
                            x = minX;
                        }

                        // Clamp Z
                        float z = movedEntity.getPositionX();
                        if ( z > maxZ ) {
                            z = maxZ;
                        } else if ( z < minZ ) {
                            z = minZ;
                        }

                        movedEntity.setPosition( x, movedEntity.getPositionY(), z );
                        continue;
                    }
                }

                // Set the new entity
                if ( !( movedEntity instanceof io.gomint.server.entity.EntityPlayer ) && chunk instanceof ChunkAdapter ) {
                    ChunkAdapter castedChunk = (ChunkAdapter) chunk;
                    if ( !castedChunk.knowsEntity( movedEntity ) ) {
                        castedChunk.addEntity( movedEntity );
                    }
                }

                // Prepare movement packet
                PacketEntityMovement packetEntityMovement = new PacketEntityMovement();
                packetEntityMovement.setEntityId( movedEntity.getEntityId() );

                packetEntityMovement.setX( movedEntity.getPositionX() );
                packetEntityMovement.setY( movedEntity.getPositionY() + movedEntity.getEyeHeight() );
                packetEntityMovement.setZ( movedEntity.getPositionZ() );

                packetEntityMovement.setYaw( movedEntity.getYaw() );
                packetEntityMovement.setHeadYaw( movedEntity.getHeadYaw() );
                packetEntityMovement.setPitch( movedEntity.getPitch() );

                // Check which player we need to inform about this movement
                for ( io.gomint.server.entity.EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
                    if ( movedEntity instanceof io.gomint.server.entity.EntityPlayer ) {
                        if ( entityPlayer.isHidden( (EntityPlayer) movedEntity ) || entityPlayer.equals( movedEntity ) ) {
                            continue;
                        }
                    }

                    entityPlayer.getEntityVisibilityManager().updateEntity( movedEntity, chunk );
                    if ( entityPlayer.getEntityVisibilityManager().isVisible( movedEntity ) ) {
                        entityPlayer.getConnection().addToSendQueue( packetEntityMovement );
                    }
                }
            }
        }
    }

    private void sendMetaChanges( Set<io.gomint.server.entity.Entity> metadataChangedEntities ) {
        if ( metadataChangedEntities != null && metadataChangedEntities.size() > 0 ) {
            for ( io.gomint.server.entity.Entity entity : metadataChangedEntities ) {
                int chunkX = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionX() );
                int chunkZ = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionZ() );

                // Create PacketEntityMetadata
                PacketEntityMetadata packetEntityMetadata = new PacketEntityMetadata();
                packetEntityMetadata.setEntityId( entity.getEntityId() );
                packetEntityMetadata.setMetadata( entity.getMetadata() );

                // Send to all players
                for ( io.gomint.server.entity.EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
                    if ( entity instanceof io.gomint.server.entity.EntityPlayer ) {
                        if ( entityPlayer.isHidden( (EntityPlayer) entity ) ) {
                            continue;
                        }
                    }

                    Chunk playerChunk = entityPlayer.getChunk();
                    if ( Math.abs( playerChunk.getX() - chunkX ) <= entityPlayer.getViewDistance() &&
                        Math.abs( playerChunk.getZ() - chunkZ ) <= entityPlayer.getViewDistance() ) {
                        entityPlayer.getConnection().addToSendQueue( packetEntityMetadata );
                    }
                }
            }
        }
    }

    private void mergeSpawnedEntities() {
        this.spawnedInThisTick.cursor().forEachForward( new LongObjConsumer<Entity>() {
            @Override
            public void accept( long l, Entity entity ) {
                entitiesById.justPut( l, entity );
            }
        } );
        this.spawnedInThisTick.clear();
    }

    /**
     * Gets an entity given its unique ID.
     *
     * @param entityId The entity's unique ID
     * @return The entity if found or null otherwise
     */
    public Entity findEntity( long entityId ) {
        Entity entity = this.entitiesById.get( entityId );
        if ( entity == null ) {
            return this.spawnedInThisTick.get( entityId );
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
    public void spawnEntityAt( Entity entity, float positionX, float positionY, float positionZ ) {
        this.spawnEntityAt( entity, positionX, positionY, positionZ, 0.0F, 0.0F );
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
    public void spawnEntityAt( Entity entity, float positionX, float positionY, float positionZ, float yaw, float pitch ) {
        // TODO: Entity spawn event

        // Only allow server implementations
        if ( !( entity instanceof io.gomint.server.entity.Entity ) ) {
            return;
        }

        io.gomint.server.entity.Entity cEntity = (io.gomint.server.entity.Entity) entity;

        // Set the position and yaw
        cEntity.setPosition( positionX, positionY, positionZ );
        cEntity.setYaw( yaw );
        cEntity.setHeadYaw( yaw );
        cEntity.setPitch( pitch );

        if ( this.currentlyTicking ) {
            this.spawnedInThisTick.justPut( entity.getEntityId(), entity );
        } else {
            this.entitiesById.justPut( entity.getEntityId(), entity );
        }

        // Register to the correct chunk
        Chunk chunk = cEntity.getChunk();
        if ( chunk == null ) {
            int chunkX = CoordinateUtils.fromBlockToChunk( (int) cEntity.getPositionX() );
            int chunkZ = CoordinateUtils.fromBlockToChunk( (int) cEntity.getPositionZ() );
            chunk = this.world.loadChunk( chunkX, chunkZ, true );
        }

        // Set the new entity
        if ( chunk instanceof ChunkAdapter ) {
            ChunkAdapter castedChunk = (ChunkAdapter) chunk;
            if ( !castedChunk.knowsEntity( cEntity ) ) {
                castedChunk.addEntity( cEntity );
            }
        }

        // If this is a player send full playerlist
        if ( entity instanceof io.gomint.server.entity.EntityPlayer ) {
            io.gomint.server.entity.EntityPlayer entityPlayer = (io.gomint.server.entity.EntityPlayer) entity;
            PacketPlayerlist playerlist = null;

            // Remap all current living entities
            List<PacketPlayerlist.Entry> listEntry = null;
            for ( EntityPlayer player : entityPlayer.getWorld().getServer().getPlayers() ) {
                if ( !player.isHidden( entityPlayer ) && !player.equals( entityPlayer ) ) {
                    if ( playerlist == null ) {
                        playerlist = new PacketPlayerlist();
                        playerlist.setMode( (byte) 0 );
                        playerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
                            add( new PacketPlayerlist.Entry( entityPlayer.getUUID(),
                                entityPlayer.getEntityId(),
                                entityPlayer.getDisplayName(),
                                entityPlayer.getXboxID(),
                                entityPlayer.getSkin() ) );
                        }} );
                    }

                    ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().send( playerlist );
                }

                if ( !entityPlayer.isHidden( player ) && !entityPlayer.equals( player ) ) {
                    if ( listEntry == null ) {
                        listEntry = new ArrayList<>();
                    }

                    listEntry.add( new PacketPlayerlist.Entry( player.getUUID(), player.getEntityId(),
                        player.getDisplayName(), player.getXboxID(), player.getSkin() ) );
                }
            }

            if ( listEntry != null ) {
                // Send player list
                PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
                packetPlayerlist.setMode( (byte) 0 );
                packetPlayerlist.setEntries( listEntry );
                entityPlayer.getConnection().send( packetPlayerlist );
            }
        }

        // Check which player we need to inform about this movement
        for ( io.gomint.server.entity.EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
            if ( entity instanceof io.gomint.server.entity.EntityPlayer ) {
                if ( entityPlayer.isHidden( (EntityPlayer) entity ) || entityPlayer.equals( entity ) ) {
                    continue;
                }
            }

            Chunk playerChunk = entityPlayer.getChunk();
            if ( Math.abs( playerChunk.getX() - chunk.getX() ) <= entityPlayer.getViewDistance() &&
                Math.abs( playerChunk.getZ() - chunk.getZ() ) <= entityPlayer.getViewDistance() ) {
                entityPlayer.getEntityVisibilityManager().addEntity( entity );
            }
        }
    }

    /**
     * Despawns an entity
     *
     * @param entity The entity which should be despawned
     */
    public void despawnEntity( Entity entity ) {
        // Only allow server implementations
        if ( !( entity instanceof io.gomint.server.entity.Entity ) ) {
            return;
        }

        io.gomint.server.entity.Entity cEntity = (io.gomint.server.entity.Entity) entity;

        // Remove from chunk
        Chunk chunk = cEntity.getChunk();
        if ( chunk instanceof ChunkAdapter ) {
            ( (ChunkAdapter) chunk ).removeEntity( cEntity );
        }

        // Broadcast entity despawn
        for ( EntityPlayer player : this.world.getPlayers() ) {
            if ( player instanceof io.gomint.server.entity.EntityPlayer ) {
                ( (io.gomint.server.entity.EntityPlayer) player ).getEntityVisibilityManager().removeEntity( entity );
            }
        }

        // Remove from maps
        this.entitiesById.justRemove( entity.getEntityId() );
        this.spawnedInThisTick.justRemove( entity.getEntityId() );
    }

}
