package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 268 )
public class Helium extends BlockElement {

    @Override
    public int getBlockId() {
        return 268;
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

    @Override
    public String getSymbol() {
        return "He";
    }

    @Override
    public String getMetalKind() {
        return "nonmetal";
    }

    @Override
    public float getAtomicMass() {
        return 4.00f;
    }
}
