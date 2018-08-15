package io.gomint.server.world.block;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 165 )
public class SlimeBlock extends Block implements io.gomint.world.block.BlockSlimeBlock {

    @Override
    public int getBlockId() {
        return 165;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SLIME_BLOCK;
    }

    @Override
    public void stepOn( Entity entity ) {
        if( !((EntityPlayer) entity).isSneaking() ) {
            entity.resetFallDistance();
        }
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
