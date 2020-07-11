package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:carpet" )
public class Carpet extends Block implements io.gomint.world.block.BlockCarpet {

    @Override
    public String getBlockId() {
        return "minecraft:carpet";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.0625f,
            this.location.getZ() + 1
        ) );
    }

    @Override
    public float getBlastResistance() {
        return 0.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CARPET;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
