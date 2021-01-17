package io.gomint.server.world.block;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCactus;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cactus" )
public class Cactus extends Block implements BlockCactus {

    @Override
    public String blockId() {
        return "minecraft:cactus";
    }

    @Override
    public long breakTime() {
        return 600;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public void onEntityCollision(Entity<?> entity ) {
        if ( entity instanceof EntityLiving ) {
            ( (EntityLiving<?>) entity ).attack( 1.0f, EntityDamageEvent.DamageSource.CACTUS );
        }
    }

    @Override
    public float blastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CACTUS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
