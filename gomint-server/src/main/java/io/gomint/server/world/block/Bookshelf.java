package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBook;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBookshelf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bookshelf" )
public class Bookshelf extends Block implements BlockBookshelf {

    @Override
    public String blockId() {
        return "minecraft:bookshelf";
    }

    @Override
    public long breakTime() {
        return 2250;
    }

    @Override
    public float blastResistance() {
        return 7.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BOOKSHELF;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        return new ArrayList<>(){{
            add( ItemBook.create( 3 ) );
        }};
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

}
