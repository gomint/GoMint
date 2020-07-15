package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.entity.Entity;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:rail")
public class Rail extends Block implements io.gomint.world.block.BlockRail {

    @Override
    public String getBlockId() {
        return "minecraft:rail";
    }

    @Override
    public long getBreakTime() {
        return 1050;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 3.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAIL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public boolean beforePlacement(Entity entity, ItemStack item, Facing face, Location location) {
        Block down = entity.getWorld().getBlockAt(location.toBlockPosition().add(BlockPosition.DOWN));
        return !down.isTransparent();
    }

}
