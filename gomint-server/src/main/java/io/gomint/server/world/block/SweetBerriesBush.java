package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSweetBerriesBush;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sweet_berry_bush" )
public class SweetBerriesBush extends Growable implements BlockSweetBerriesBush {

    @Override
    public String getBlockId() {
        return "minecraft:sweet_berry_bush";
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

          //TODO DAMAGE

    @Override
    public BlockType getType() {
        return BlockType.SWEETBERRIESBUSH;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
