package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDirt;
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
@RegisterInfo( sId = "minecraft:podzol" )
public class Podzol extends Block implements io.gomint.world.block.BlockPodzol {

    @Override
    public String getBlockId() {
        return "minecraft:podzol";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PODZOL;
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
        return ToolPresets.SHOVEL;
    }

}
