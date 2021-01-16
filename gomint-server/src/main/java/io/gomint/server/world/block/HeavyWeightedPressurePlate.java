package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockHeavyWeightedPressurePlate;
import io.gomint.world.block.BlockType;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.registry.RegisterInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:heavy_weighted_pressure_plate" )
public class HeavyWeightedPressurePlate extends Block implements BlockHeavyWeightedPressurePlate {

    @Override
    public String getBlockId() {
        return "minecraft:heavy_weighted_pressure_plate";
    }

    @Override
    public long breakTime() {
        return 750;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.x(),
            this.location.y(),
            this.location.z(),
            this.location.x() + 1,
            this.location.y() + 0.1f,
            this.location.z() + 1
        ) );
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.HEAVY_WEIGHTED_PRESSURE_PLATE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }
}
