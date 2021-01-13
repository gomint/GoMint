package io.gomint.server.world.block;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFire;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fire" )
public class Fire extends Block implements BlockFire {

    @Override
    public String getBlockId() {
        return "minecraft:fire";
    }

    @Override
    public boolean canBeReplaced( ItemStack item ) {
        return true;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        entityLiving.attack( 1.0f, EntityDamageEvent.DamageSource.FIRE );
        entityLiving.setBurning( 8, TimeUnit.SECONDS );
    }

    @Override
    public boolean punch( EntityPlayer player ) {
        this.blockType( Air.class );
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public List<ItemStack> drops(ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public BlockType blockType() {
        return BlockType.FIRE;
    }

}
