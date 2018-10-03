package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNitrogen;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:element_7" )
public class Nitrogen extends BlockElement implements BlockNitrogen {

    @Override
    public String getBlockId() {
        return "minecraft:element_7";
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
        return BlockType.NITROGEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 7;
    }

    @Override
    public int getElectrons() {
        return 7;
    }

    @Override
    public int[] getNeutrons() {
        return new int[] {7, 8};
    }

    @Override
    public int getAtomicWeight() {
        return 14;
    }

    @Override
    public String getName() {
        return "Nitrogen";
    }
}
