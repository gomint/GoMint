package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemVines;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockType;

import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 106 )
public class Vines extends Block implements io.gomint.world.block.BlockVines {

    @Override
    public int getBlockId() {
        return 106;
    }

    @Override
    public long getBreakTime() {
        return 300;
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
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void stepOn( Entity entity ) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.VINES;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        if( isCorrectTool( itemInHand ) ) {
            return new ArrayList<ItemStack>() {{
                add( ItemVines.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

}
