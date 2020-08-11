package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockBeeHive;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:beehive")
public class BeeHive extends Block implements BlockBeeHive {

    private static final BlockfaceFromPlayerBlockState FACING = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, false);
    private static final ProgressBlockState HONEY_LEVEL = new ProgressBlockState(() -> new String[]{"honey_level"}, 5, aVoid -> {});

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.6f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEE_HIVE;
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        super.calculatePlacementData(entity, item, face, block, clickedBlock, clickVector);
        FACING.detectFromPlacement(this, entity, item, face, block, clickedBlock, clickVector);
        HONEY_LEVEL.detectFromPlacement(this, entity, item, face, block, clickedBlock, clickVector);
        return new PlacementData(this.identifier, null);
    }

    @Override
    public void setFacing(Facing facing) {
        FACING.setState(this, facing);
    }

    @Override
    public Facing getFacing() {
        return FACING.getState(this);
    }

}
