/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.passive;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.server.network.packet.PacketUpdateBlockSynched;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 66 )
public class EntityFallingBlock extends Entity implements io.gomint.entity.passive.EntityFallingBlock {

    private int blockId;
    private byte blockData;

    private BlockPosition position;

    /**
     * Constructs a new EntityFallingBlock
     *
     * @param block Which will be represented by this entity
     * @param world The world in which this entity is in
     */
    public EntityFallingBlock( Block block, WorldAdapter world ) {
        super( EntityType.FALLING_BLOCK, world );
        this.initEntity();
        this.setBlock( block );
    }

    /**
     * Create new entity falling block for API
     */
    public EntityFallingBlock() {
        super( EntityType.FALLING_BLOCK, null );
        this.initEntity();
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        if ( this.isDead() ) {
            return;
        }

        super.update( currentTimeMS, dT );

        // Are we onground?
        if ( this.onGround ) {
            this.despawn();

            // Generate new item drop
            this.world.createItemDrop( this.getLocation(), this.world.getServer().getItems().create( this.blockId, this.blockData, (byte) 1, null ) );
        }
    }

    @Override
    protected void fall() {
        // We don't need fall damage here
        this.fallDistance = 0;
    }

    private void initEntity() {
        this.setSize( 0.98f, 0.98f );
        this.offsetY = 0.49f;

        GRAVITY = 0.04f;
        DRAG = 0.02f;
    }

    @Override
    public void setBlock( io.gomint.world.block.Block block ) {
        Block block1 = (Block) block;

        this.blockId = block1.getBlockId();
        this.blockData = block1.getBlockData();
        this.metadataContainer.putInt( MetadataContainer.DATA_VARIANT, BlockRuntimeIDs.fromLegacy( block1.getBlockId(), block1.getBlockData() ) );
        this.position = block1.getLocation().toBlockPosition();
    }

    @Override
    public void postSpawn( PlayerConnection connection ) {
        PacketUpdateBlockSynched blockSynched = new PacketUpdateBlockSynched();
        blockSynched.setAction( 1 );
        blockSynched.setEntityId( this.getEntityId() );
        blockSynched.setPosition( this.position );
        blockSynched.setBlockId( BlockRuntimeIDs.fromLegacy( this.blockId, this.blockData ) );
        blockSynched.setLayer( 0 );
        blockSynched.setFlags( PacketUpdateBlock.FLAG_ALL );
        connection.addToSendQueue( blockSynched );
    }

}
