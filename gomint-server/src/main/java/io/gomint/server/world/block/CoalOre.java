package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCoal;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockCoalOre;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coal_ore" )
public class CoalOre extends Block implements BlockCoalOre {

    @Override
    public String getBlockId() {
        return "minecraft:coal_ore";
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.COAL_ORE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> drops(ItemStack itemInHand ) {
        if( isCorrectTool( itemInHand ) ) {
            ((WorldAdapter) this.location.getWorld()).createExpOrb( this.location, ThreadLocalRandom.current().nextInt( 3 ) );
            return new ArrayList<ItemStack>(){{
                add( ItemCoal.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
