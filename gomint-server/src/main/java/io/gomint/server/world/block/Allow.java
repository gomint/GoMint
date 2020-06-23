package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:allow" )
public class Allow extends Block implements io.gomint.world.block.BlockAllow{

    @Override
    public String getBlockId() {
        return "minecraft:allow";
    }

    @Override
    public float getBlastResistance() {
        return 0; //No Information ???
    }

    @Override
    public BlockType getType() {
        return null;
    }
}
