/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "MobSpawner")
public class MobSpawnerTileEntity extends TileEntity {

    private int entityId;

    // Display
    private float displayWidth;
    private float displayScale;
    private float displayHeight;

    // Spawn rules
    private short maxNearbyEntities;
    private short playerRange;
    private short spawnRange;

    // Delay rules
    private short maxDelay;
    private short minDelay;
    private short delay;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public MobSpawnerTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.entityId = compound.getInteger( "EntityId", 0 );

        this.displayWidth = compound.getFloat( "DisplayEntityWidth", 0.8f );
        this.displayScale = compound.getFloat( "DisplayEntityScale", 1.0f );
        this.displayHeight = compound.getFloat( "DisplayEntityHeight", 1.8f );

        this.maxNearbyEntities = compound.getShort( "MaxNearbyEntities", (short) 6 );
        this.playerRange = compound.getShort( "RequiredPlayerRange", (short) 16 );
        this.spawnRange = compound.getShort( "SpawnRange", (short) 4 );

        this.maxDelay = compound.getShort( "MaxSpawnDelay", (short) 800 );
        this.minDelay = compound.getShort( "MinSpawnDelay", (short) 200 );
        this.delay = compound.getShort( "Delay", (short) 0 );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "MobSpawner" );

        if ( reason == SerializationReason.PERSIST ) {
            compound.addValue( "MaxNearbyEntities", this.maxNearbyEntities );
            compound.addValue( "RequiredPlayerRange", this.playerRange );
            compound.addValue( "SpawnRange", this.spawnRange );

            compound.addValue( "MaxSpawnDelay", this.maxDelay );
            compound.addValue( "MinSpawnDelay", this.minDelay );
            compound.addValue( "Delay", this.delay );
        }

        compound.addValue( "EntityId", this.entityId );
        compound.addValue( "DisplayEntityWidth", this.displayWidth );
        compound.addValue( "DisplayEntityScale", this.displayScale );
        compound.addValue( "DisplayEntityHeight", this.displayHeight );
    }

}
