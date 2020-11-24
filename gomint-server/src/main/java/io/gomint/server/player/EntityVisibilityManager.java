/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.player;

import io.gomint.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketDespawnEntity;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.world.Chunk;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityVisibilityManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityVisibilityManager.class );
    private final EntityPlayer player;
    private final ObjectSet<Entity> visible = new ObjectOpenHashSet<>();

    public EntityVisibilityManager(EntityPlayer player) {
        this.player = player;
    }

    public void updateAddedChunk(ChunkAdapter chunk ) {
        LOGGER.debug( "Checking chunk {}, {}", chunk.getX(), chunk.getZ() );

        // Check if we should be able to see this entity
        chunk.iterateEntities( Entity.class, entity -> {
            LOGGER.debug( "Found entity {}", entity );

            if ( ( (io.gomint.server.entity.Entity) entity ).shouldBeSeen( EntityVisibilityManager.this.player ) ) {
                EntityVisibilityManager.this.addEntity( entity );
            }
        } );
    }

    public void updateEntity( Entity entity, Chunk chunk ) {
        if ( !( (io.gomint.server.entity.Entity) entity ).shouldBeSeen( this.player ) ) {
            return;
        }

        int currentX = CoordinateUtils.fromBlockToChunk( (int) this.player.getPositionX() );
        int currentZ = CoordinateUtils.fromBlockToChunk( (int) this.player.getPositionZ() );

        if ( Math.abs( chunk.getX() - currentX ) > this.player.getViewDistance() ||
            Math.abs( chunk.getZ() - currentZ ) > this.player.getViewDistance() ) {
            removeEntity( entity );
        } else {
            addEntity( entity );
        }
    }

    public void updateRemoveChunk( ChunkAdapter chunk ) {
        // Check for removing entities
        chunk.iterateEntities( Entity.class, EntityVisibilityManager.this::removeEntity );
    }

    public void removeEntity( Entity entity ) {
        if ( this.visible.remove( entity ) && !this.player.equals( entity ) ) {
            io.gomint.server.entity.Entity implEntity = (io.gomint.server.entity.Entity) entity;
            this.despawnEntity( implEntity );
        }
    }

    private void despawnEntity( io.gomint.server.entity.Entity entity ) {
        LOGGER.debug( "Removing entity {} for {}", entity, this.player.getName() );
        entity.detach( this.player );

        PacketDespawnEntity despawnEntity = new PacketDespawnEntity();
        despawnEntity.setEntityId( entity.getEntityId() );
        this.player.getConnection().addToSendQueue( despawnEntity );
    }

    public void addEntity( Entity entity ) {
        if ( !this.visible.contains( entity ) && !this.player.equals( entity ) ) {
            LOGGER.debug( "Spawning entity {} for {}", entity, this.player.getName() );
            io.gomint.server.entity.Entity implEntity = (io.gomint.server.entity.Entity) entity;

            implEntity.preSpawn( this.player.getConnection() );
            this.player.getConnection().addToSendQueue( implEntity.createSpawnPacket( this.player ) );
            implEntity.postSpawn( this.player.getConnection() );

            implEntity.attach( this.player );
            this.visible.add( entity );
        }
    }

    public void clear() {
        for ( Entity curr : this.visible ) {
            if ( curr instanceof io.gomint.server.entity.Entity ) {
                this.despawnEntity( (io.gomint.server.entity.Entity) curr );
            }
        }

        this.visible.clear();
    }

    public boolean isVisible( Entity entity ) {
        return Objects.equals( entity, this.player ) || this.visible.contains( entity );
    }

}
