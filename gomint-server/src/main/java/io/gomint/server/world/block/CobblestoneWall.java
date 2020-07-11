package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cobblestone_wall" )
public class CobblestoneWall extends Block implements io.gomint.world.block.BlockCobblestoneWall {

    @Override
    public String getBlockId() {
        return "minecraft:cobblestone_wall";
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COBBLESTONE_WALL;
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
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX() + 0.25f,
            this.location.getY(),
            this.location.getZ() + 0.25f ,
            this.location.getX() + 0.75f,
            this.location.getY() + 1,
            this.location.getZ() + 0.75f
        ) );
    }

}
