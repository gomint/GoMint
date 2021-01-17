package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCarpet;
import io.gomint.world.block.BlockType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:carpet" )
public class Carpet extends Block implements BlockCarpet {

    @Override
    public String blockId() {
        return "minecraft:carpet";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.x(),
            this.location.y(),
            this.location.z(),
            this.location.x() + 1,
            this.location.y() + 0.0625f,
            this.location.z() + 1
        ) );
    }

    @Override
    public float blastResistance() {
        return 0.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CARPET;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
