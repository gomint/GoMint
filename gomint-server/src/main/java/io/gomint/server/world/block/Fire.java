package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 51 )
public class Fire extends Block implements io.gomint.world.block.BlockFire {

    @Override
    public int getBlockId() {
        return 51;
    }

    @Override
    public boolean canBeReplaced( ItemStack item ) {
        return true;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        EntityDamageEvent damageEvent = new EntityDamageEvent( entityLiving, EntityDamageEvent.DamageSource.FIRE, 1.0f );
        entityLiving.damage( damageEvent );
        entityLiving.setFire( 8 );
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public BlockType getType() {
        return BlockType.FIRE;
    }

}