package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemEmerald;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:emerald_ore" )
public class EmeraldOre extends Block implements io.gomint.world.block.BlockEmeraldOre {

    @Override
    public String getBlockId() {
        return "minecraft:emerald_ore";
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
    public BlockType getType() {
        return BlockType.EMERALD_ORE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
        if ( isCorrectTool( itemInHand ) ) {
            ( (WorldAdapter) this.location.getWorld() ).createExpOrb( this.location, FastRandom.current().nextInt( 3 ) );
            itemStackArrayList.add( ItemEmerald.create( 1 ) );
        }

        return itemStackArrayList;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }
}
