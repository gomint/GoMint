package io.gomint.server.world.block;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 81 )
public class Cactus extends Block implements io.gomint.world.block.BlockCactus {

    @Override
    public int getBlockId() {
        return 81;
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void onEntityCollision( Entity entity ) {
        if ( entity instanceof EntityLiving ) {
            ( (EntityLiving) entity ).attack( 1.0f, EntityDamageEvent.DamageSource.CACTUS );
        }
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CACTUS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
