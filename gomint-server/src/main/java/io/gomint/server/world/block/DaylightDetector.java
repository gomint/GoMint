package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.DaylightDetectorTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 151 )
public class DaylightDetector extends Block implements io.gomint.world.block.BlockDaylightDetector {

    @Override
    public int getBlockId() {
        return 151;
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DAYLIGHT_DETECTOR;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        return new DaylightDetectorTileEntity( this.location );
    }

}
