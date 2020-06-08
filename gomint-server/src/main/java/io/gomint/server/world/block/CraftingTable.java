package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import io.gomint.server.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:crafting_table" )
public class CraftingTable extends Block implements io.gomint.world.block.BlockCraftingTable {

    private static final Logger LOGGER = LoggerFactory.getLogger( CraftingTable.class );

    @Override
    public String getBlockId() {
        return "minecraft:crafting_table";
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        if ( entity instanceof EntityPlayer ) {
            // This should be a container open
            LOGGER.debug( "Changing to 3x3 crafting grid for player " + ( (EntityPlayer) entity ).getName() );
            ( (EntityPlayer) entity ).getCraftingInventory().resizeAndClear( 9 );
            ( (EntityPlayer) entity ).getCraftingInputInventory().resizeAndClear( 9 );
        }

        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CRAFTING_TABLE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
