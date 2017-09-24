/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.entity.Player;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.*;
import io.gomint.world.Chunk;
import com.koloboke.collect.map.LongObjCursor;
import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityManager.class );

    private final WorldAdapter world;
    private LongObjMap<Entity> entitiesById;
    private LongObjMap<Entity> spawnedInThisTick;

    private boolean currentlyTicking;

    /**
     * Construct a new Entity manager for the given world
     *
     * @param world the world for which this manager is
     */
    public EntityManager( WorldAdapter world ) {
        this.world = world;
        this.entitiesById = HashLongObjMaps.newMutableMap();
        this.spawnedInThisTick = HashLongObjMaps.newMutableMap();
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
        Set<Entity> movedEntities = null;
        Set<Entity> metadataChangedEntities = null;
        this.currentlyTicking = true;

        LongObjCursor<Entity> cursor = this.entitiesById.cursor();
        while ( cursor.moveNext() ) {
            Entity entity = cursor.value();
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

                        if ( !( entity instanceof EntityPlayer ) && !current.equals( entity.getChunk() ) ) {
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
        this.entitiesById.putAll( this.spawnedInThisTick );
        this.spawnedInThisTick.clear();

        // --------------------------------------
        // Metadata batches:
        if ( metadataChangedEntities != null && metadataChangedEntities.size() >  0 ) {
            for ( Entity entity : metadataChangedEntities ) {
                int chunkX = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionX() );
                int chunkZ = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionZ() );

                // Create PacketEntityMetadata
                PacketEntityMetadata packetEntityMetadata = new PacketEntityMetadata();
                packetEntityMetadata.setEntityId( entity.getEntityId() );
                packetEntityMetadata.setMetadata( entity.getMetadata() );

                // Send to all players
                for ( EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
                    if ( entity instanceof EntityPlayer ) {
                        if ( entityPlayer.isHidden( (Player) entity ) ) {
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

        // --------------------------------------
        // Create movement batches:
        if ( movedEntities != null && movedEntities.size() > 0 ) {
            for ( Entity movedEntity : movedEntities ) {
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
                if ( !( movedEntity instanceof EntityPlayer ) && chunk instanceof ChunkAdapter ) {
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

                // Prepare motion packet
                PacketEntityMotion packetEntityMotion = new PacketEntityMotion();
                packetEntityMotion.setEntityId( movedEntity.getEntityId() );
                packetEntityMotion.setVelocity( movedEntity.getVelocity() );

                // Check which player we need to inform about this movement
                for ( EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
                    if ( movedEntity instanceof EntityPlayer ) {
                        if ( entityPlayer.isHidden( (Player) movedEntity ) || entityPlayer.equals( movedEntity ) ) {
                            continue;
                        }
                    }

                    Chunk playerChunk = entityPlayer.getChunk();
                    if ( Math.abs( playerChunk.getX() - chunk.getX() ) <= entityPlayer.getViewDistance() &&
                            Math.abs( playerChunk.getZ() - chunk.getZ() ) <= entityPlayer.getViewDistance() ) {
                        entityPlayer.getConnection().addToSendQueue( packetEntityMovement );
                        entityPlayer.getConnection().addToSendQueue( packetEntityMotion );
                    }
                }
            }
        }
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


        // Set the position and yaw
        entity.setPosition( positionX, positionY, positionZ );
        entity.setYaw( yaw );
        entity.setHeadYaw( yaw );
        entity.setPitch( pitch );

        // Update bounding box
        entity.getBoundingBox().setBounds(
                entity.getPositionX() - ( entity.getWidth() / 2 ),
                entity.getPositionY(),
                entity.getPositionZ() - ( entity.getWidth() / 2 ),
                entity.getPositionX() + ( entity.getWidth() / 2 ),
                entity.getPositionY() + entity.getHeight(),
                entity.getPositionZ() + ( entity.getWidth() / 2 )
        );

        if ( this.currentlyTicking ) {
            this.spawnedInThisTick.put( entity.getEntityId(), entity );
        } else {
            this.entitiesById.put( entity.getEntityId(), entity );
        }

        // Register to the correct chunk
        Chunk chunk = entity.getChunk();
        if ( chunk == null ) {
            int chunkX = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionX() );
            int chunkZ = CoordinateUtils.fromBlockToChunk( (int) entity.getPositionZ() );
            chunk = this.world.loadChunk( chunkX, chunkZ, true );
        }

        // Set the new entity
        if ( chunk instanceof ChunkAdapter ) {
            ChunkAdapter castedChunk = (ChunkAdapter) chunk;
            if ( !castedChunk.knowsEntity( entity ) ) {
                castedChunk.addEntity( entity );
            }
        }

        // If this is a player send full playerlist
        if ( entity instanceof EntityPlayer ) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            PacketPlayerlist playerlist = null;

            // Remap all current living entities
            List<PacketPlayerlist.Entry> listEntry = null;
            for ( Player player : entityPlayer.getWorld().getServer().getPlayers() ) {
                if ( !player.isHidden( entityPlayer ) && !player.equals( entityPlayer ) ) {
                    if ( playerlist == null ) {
                        playerlist = new PacketPlayerlist();
                        playerlist.setMode( (byte) 0 );
                        playerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
                            add( new PacketPlayerlist.Entry( entityPlayer.getUUID(),
                                    entityPlayer.getEntityId(),
                                    entityPlayer.getName(),
                                    entityPlayer.getXboxID(),
                                    entityPlayer.getSkin() ) );
                        }} );
                    }

                    ( (EntityPlayer) player ).getConnection().send( playerlist );
                }

                if ( !entityPlayer.isHidden( player ) && !entityPlayer.equals( player ) ) {
                    if ( listEntry == null ) {
                        listEntry = new ArrayList<>();
                    }

                    listEntry.add( new PacketPlayerlist.Entry( player.getUUID(), player.getEntityId(),
                            player.getName(), player.getXboxID(), player.getSkin() ) );
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

        Packet spawnPacket = entity.createSpawnPacket();

        // Check which player we need to inform about this movement
        for ( EntityPlayer entityPlayer : this.world.getPlayers0().keySet() ) {
            if ( entity instanceof EntityPlayer ) {
                if ( entityPlayer.isHidden( (Player) entity ) || entityPlayer.equals( entity ) ) {
                    continue;
                }
            }

            Chunk playerChunk = entityPlayer.getChunk();
            if ( Math.abs( playerChunk.getX() - chunk.getX() ) <= entityPlayer.getViewDistance() &&
                    Math.abs( playerChunk.getZ() - chunk.getZ() ) <= entityPlayer.getViewDistance() ) {
                entityPlayer.getConnection().send( spawnPacket );
            }
        }
    }

    /**
     * Despawns an entity
     *
     * @param entity The entity which should be despawned
     */
    public void despawnEntity( Entity entity ) {
        // Remove from chunk
        Chunk chunk = entity.getChunk();
        if ( chunk instanceof ChunkAdapter ) {
            ( (ChunkAdapter) chunk ).removeEntity( entity );
        }

        // Broadcast despawn entity packet:
        PacketDespawnEntity packet = new PacketDespawnEntity();
        packet.setEntityId( entity.getEntityId() );

        for ( Player player : this.world.getPlayers() ) {
            if ( player instanceof EntityPlayer ) {
                ( (EntityPlayer) player ).getConnection().addToSendQueue( packet );
            }
        }
    }

}