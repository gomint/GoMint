package io.gomint.server.world.block;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockBlockOfCoal;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coal_block" )
public class BlockOfCoal extends Block implements BlockBlockOfCoal {

    @Override
    public String blockId() {
        return "minecraft:coal_block";
    }

    @Override
    public long breakTime() {
        return 7500;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BLOCK_OF_COAL;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return new ArrayList<>() {{
                add( ItemBlockOfCoal.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

}
