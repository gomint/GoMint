package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBeryllium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_4" )
public class Beryllium extends BlockElement implements BlockBeryllium {

    @Override
    public String getBlockId() {
        return "minecraft:element_4";
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
        return BlockType.BERYLLIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 4;
    }

    @Override
    public int getElectrons() {
        return 4;
    }

    @Override
    public int getNeutrons() {
        return 5;
    }

    @Override
    public int getAtomicWeight() {
        return 9;
    }

    @Override
    public String getName() {
        return "Beryllium";
    }
}
