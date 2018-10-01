package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHelium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_2" )
public class Helium extends BlockElement implements BlockHelium {

    @Override
    public String getBlockId() {
        return "minecraft:element_2";
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
        return BlockType.HELIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 2;
    }

    @Override
    public int getElectrons() {
        return 2;
    }

    @Override
    public int getNeutrons() {
        return 1;
    }

    @Override
    public int getAtomicWeight() {
        return 4;
    }

    @Override
    public String getName() {
        return "Helium";
    }
}
