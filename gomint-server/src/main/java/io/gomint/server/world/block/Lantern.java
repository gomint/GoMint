package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.world.block.BlockLantern;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LanternType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lantern", def = true)
@RegisterInfo(sId = "minecraft:soul_lantern")
public class Lantern extends Block implements BlockLantern {

    private static final BooleanBlockState HANGING = new BooleanBlockState(() -> new String[]{"hanging"});

    @Override
    public long getBreakTime() {
        return 17500;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 3.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LANTERN;
    }

    @Override
    public void setLanternType(LanternType type) {
        switch (type) {
            case NORMAL:
                this.setBlockId("minecraft:lantern");
                return;
            default:
                this.setBlockId("minecraft:soul_lantern");
        }
    }

    @Override
    public LanternType getLanternType() {
        switch (this.getBlockId()) {
            case "minecraft:lantern":
                return LanternType.NORMAL;
            default:
                return LanternType.SOUL;
        }
    }

    @Override
    public boolean isHanging() {
        return HANGING.getState(this);
    }

    @Override
    public void setHanging(boolean hanging) {
        HANGING.setState(this, hanging);
    }

}
