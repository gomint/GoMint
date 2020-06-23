package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:deny" )
public class Deny extends Block implements io.gomint.world.block.BlockDeny {

    @Override
    public String getBlockId() {
        return "minecraft:deny";
    }

    @Override
    public float getBlastResistance() {
        return 0; //No Informaton??
    }

    @Override
    public BlockType getType() {
        return BlockType.DENY;
    }
}
