package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBlockOfEmerald;
import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemIronPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBlockOfEmerald;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:emerald_block" )
public class BlockOfEmerald extends Block implements BlockBlockOfEmerald {

    @Override
    public String blockId() {
        return "minecraft:emerald_block";
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
        return BlockType.BLOCK_OF_EMERALD;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        // Only iron and up
        return new Class[]{
            ItemIronPickaxe.class,
            ItemDiamondPickaxe.class,
        };
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return new ArrayList<>() {{
                add( ItemBlockOfEmerald.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

}
