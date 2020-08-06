package io.gomint.server.world.block;

import io.gomint.world.block.BlockEndPortalFrame;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_portal_frame" )
public class EndPortalFrame extends Block implements BlockEndPortalFrame {

    @Override
    public String getBlockId() {
        return "minecraft:end_portal_frame";
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_PORTAL_FRAME;
    }

}
