package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemFlintAndSteel;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.active.EntityPrimedTNT;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockTNT;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 46 )
public class TNT extends Block implements BlockTNT {

    @Override
    public int getBlockId() {
        return 46;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        if ( entity instanceof EntityPlayer && item instanceof ItemFlintAndSteel ) {
            EntityPlayer player = (EntityPlayer) entity;
            io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) item;
            if ( itemStack.damage( 1 ) ) {
                player.getInventory().setItem( player.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
            } else {
                player.getInventory().setItem( player.getInventory().getItemInHandSlot(), item );
            }

            prime( 4 );
            return true;
        }

        return false;
    }

    public void prime( float secondsUntilExplosion ) {
        int primeTicks = (int) ( secondsUntilExplosion * 20f );

        // Set this to air
        this.setType( Air.class );

        // Spawn tnt entity
        EntityPrimedTNT entityTNT = new EntityPrimedTNT( this.world, this.location.add( 0.5f, 0.5f, 0.5f ), primeTicks );
        this.world.spawnEntityAt( entityTNT, entityTNT.getPositionX(), entityTNT.getPositionY(), entityTNT.getPositionZ() );
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.TNT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
