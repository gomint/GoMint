package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFluorine;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_9" )
public class Fluorine extends BlockElement implements BlockFluorine {

    @Override
    public String getBlockId() {
        return "minecraft:element_9";
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
        return BlockType.FLUORINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 9;
    }

    @Override
    public int getElectrons() {
        return 9;
    }

    @Override
    public int getNeutrons() {
        return 9;
    }

    @Override
    public int getAtomicWeight() {
        return 18;
    }

    @Override
    public String getName() {
        return "Fluorine";
    }
}
