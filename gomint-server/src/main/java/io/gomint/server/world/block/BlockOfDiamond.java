package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBlockOfDiamond;
import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemIronPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 57 )
public class BlockOfDiamond extends Block implements io.gomint.world.block.BlockBlockOfDiamond {

    @Override
    public int getBlockId() {
        return 57;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_DIAMOND;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        // Only iron and up
        return new Class[]{
            ItemIronPickaxe.class,
            ItemDiamondPickaxe.class,
        };
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return new ArrayList<ItemStack>() {{
                add( ItemBlockOfDiamond.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

}
