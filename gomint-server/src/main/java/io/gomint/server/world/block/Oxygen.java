package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockOxygen;
import io.gomint.world.block.BlockType;

/**
 * @author Kaoot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_8" )
public class Oxygen extends BlockElement implements BlockOxygen {

    @Override
    public String getBlockId() {
        return "minecraft:element_8";
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
        return BlockType.OXYGEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 8;
    }

    @Override
    public int getElectrons() {
        return 8;
    }

    @Override
    public int[] getNeutrons() {
        return new int[] {8, 9, 10};
    }

    @Override
    public int getAtomicWeight() {
        return 16;
    }

    @Override
    public String getName() {
        return "Oxygen";
    }
}
