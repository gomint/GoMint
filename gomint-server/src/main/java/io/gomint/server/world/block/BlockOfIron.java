package io.gomint.server.world.block;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 42 )
public class BlockOfIron extends Block implements io.gomint.world.block.BlockBlockOfIron {

    @Override
    public int getBlockId() {
        return 42;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_IRON;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        // Only stone, iron and up
        return new Class[]{
            ItemIronPickaxe.class,
            ItemDiamondPickaxe.class,
            ItemStonePickaxe.class
        };
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return new ArrayList<ItemStack>(){{
                add( ItemBlockOfIron.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

}
