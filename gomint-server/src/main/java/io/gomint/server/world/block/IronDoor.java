package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:iron_door" )
public class IronDoor extends Door implements io.gomint.world.block.BlockIronDoor {

    @Override
    public String getBlockId() {
        return "minecraft:iron_door";
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public float getBlastResistance() {
        return 25.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_DOOR;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
