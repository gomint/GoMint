package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDirt;
import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockType;
import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.UpdateReason;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 2 )
public class GrassBlock extends Block implements io.gomint.world.block.BlockGrassBlock {

    @Override
    public int getBlockId() {
        return 2;
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        Block block = this.world.getBlockAt( this.location.toBlockPosition().add( BlockPosition.UP ) );
        byte lightLevel = block.getSkyLightLevel();

        if ( lightLevel >= 9 ) {

        }

        return -1;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public float getBlastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GRASS_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>(){{
            add( ItemDirt.create( 1 ) );
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

}
