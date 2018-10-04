package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHydrogen;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_1" )
public class Hydrogen extends BlockElement implements BlockHydrogen {

    @Override
    public String getBlockId() {
        return "minecraft:element_1";
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
        return BlockType.HYDROGEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 1;
    }

    @Override
    public int getElectrons() {
        return 1;
    }

    @Override
    public int[] getNeutrons() {
        return new int[] {0, 1, 2};
    }

    @Override
    public int[] getAtomicWeight() {
        return new int[] {1};
    }

    @Override
    public String getName() {
        return "Hydrogen";
    }
}
