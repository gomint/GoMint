package io.gomint.server.world.block;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.block.BlockSlimeBlock;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:slime" )
public class SlimeBlock extends Block implements BlockSlimeBlock {

    @Override
    public String blockId() {
        return "minecraft:slime";
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SLIME_BLOCK;
    }

    @Override
    public void stepOn(Entity<?> entity ) {
        if( !((EntityPlayer) entity).sneaking() ) {
            entity.resetFallDistance();
        }
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
