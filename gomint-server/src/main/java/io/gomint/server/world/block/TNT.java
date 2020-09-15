package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemFlintAndSteel;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemTNT;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.active.EntityPrimedTNT;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockTNT;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.TNTType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:tnt" )
public class TNT extends Block implements BlockTNT {

    private static final BooleanBlockState UNDER_WATER = new BooleanBlockState(() -> new String[]{"allow_underwater_bit"});
    private static final BooleanBlockState EXPLODE = new BooleanBlockState(() -> new String[]{"explode_bit"});

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        EXPLODE.setState(this, false); // Don't explode on breakage
        return super.beforePlacement(entity, item, face, location);
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
    public TNTType getType() {
        return UNDER_WATER.getState(this) ? TNTType.UNDER_WATER : TNTType.NORMAL;
    }

    @Override
    public void setType(TNTType type) {
        UNDER_WATER.setState(this, type == TNTType.UNDER_WATER);
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

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        ItemTNT item = ItemTNT.create(1);
        return Collections.singletonList(item);
    }

}
