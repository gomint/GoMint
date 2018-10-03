package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLithium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_3" )
public class Lithium extends BlockElement implements BlockLithium {

    @Override
    public String getBlockId() {
        return "minecraft:element_3";
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getType() {
        return BlockType.LITHIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 3;
    }

    @Override
    public int getElectrons() {
        return 3;
    }

    @Override
    public int[] getNeutrons() {
        return new int[] {3, 4};
    }

    @Override
    public int getAtomicWeight() {
        return 7;
    }

    @Override
    public String getName() {
        return "Lithium";
    }
}
