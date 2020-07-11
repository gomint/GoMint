package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemFlintAndSteel;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.active.EntityPrimedTNT;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockTNT;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:tnt" )
public class TNT extends Block implements BlockTNT {

    @Override
    public String getBlockId() {
        return "minecraft:tnt";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        if ( entity instanceof EntityPlayer && item instanceof ItemFlintAndSteel ) {
            io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) item;
            itemStack.afterPlacement();

            prime( 4 );
            return true;
        }

        return false;
    }

    @Override
    public void prime( float secondsUntilExplosion ) {
        int primeTicks = (int) ( secondsUntilExplosion * 20f );

        // Set this to air
        this.setBlockType( Air.class );

        // Spawn tnt entity
        EntityPrimedTNT entityTNT = new EntityPrimedTNT( this.world, this.location.add( 0.5f, 0.5f, 0.5f ), primeTicks );
        this.world.spawnEntityAt( entityTNT, entityTNT.getPositionX(), entityTNT.getPositionY(), entityTNT.getPositionZ() );
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TNT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
