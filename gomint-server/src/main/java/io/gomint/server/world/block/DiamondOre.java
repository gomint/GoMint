package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamond;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockDiamondOre;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:diamond_ore" )
public class DiamondOre extends Block implements BlockDiamondOre {

    @Override
    public String getBlockId() {
        return "minecraft:diamond_ore";
    }

    @Override
    public long breakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.DIAMOND_ORE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        List<ItemStack<?>> drops = new ArrayList<>();
        if ( isCorrectTool( itemInHand ) ) {
            ( (WorldAdapter) this.location.world() ).createExpOrb( this.location, ThreadLocalRandom.current().nextInt( 3 ) );
            drops.add( ItemDiamond.create( 1 ) );
        }

        return drops;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
