package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBook;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bookshelf" )
public class Bookshelf extends Block implements io.gomint.world.block.BlockBookshelf {

    @Override
    public String getBlockId() {
        return "minecraft:bookshelf";
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 7.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BOOKSHELF;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>(){{
            add( ItemBook.create( 3 ) );
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
