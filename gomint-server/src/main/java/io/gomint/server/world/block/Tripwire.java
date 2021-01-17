package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.world.block.BlockTripwire;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:tripWire" )
public class Tripwire extends Block implements BlockTripwire {

    @Override
    public String blockId() {
        return "minecraft:tripWire";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.TRIPWIRE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.x(),
            this.location.y(),
            this.location.z(),
            this.location.x() + 1,
            this.location.y() + 0.15625f,
            this.location.z() + 1
        ) );
    }

}
