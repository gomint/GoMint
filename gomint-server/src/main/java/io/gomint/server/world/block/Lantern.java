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
    public long breakTime() {
        return 17500;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float blastResistance() {
        return 3.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LANTERN;
    }

    @Override
    public BlockLantern type(LanternType type) {
        switch (type) {
            case NORMAL:
                this.blockId("minecraft:lantern");
                return this;
            default:
                this.blockId("minecraft:soul_lantern");
        }

        return this;
    }

    @Override
    public LanternType type() {
        switch (this.blockId()) {
            case "minecraft:lantern":
                return LanternType.NORMAL;
            default:
                return LanternType.SOUL;
        }
    }

    @Override
    public boolean hanging() {
        return HANGING.state(this);
    }

    @Override
    public BlockLantern hanging(boolean hanging) {
        HANGING.state(this, hanging);
        return this;
    }

}
