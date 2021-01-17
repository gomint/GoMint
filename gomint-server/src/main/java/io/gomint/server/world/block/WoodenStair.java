package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockWoodenStair;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:oak_stairs", def = true)
@RegisterInfo( sId = "minecraft:acacia_stairs" )
@RegisterInfo( sId = "minecraft:spruce_stairs" )
@RegisterInfo( sId = "minecraft:birch_stairs" )
@RegisterInfo( sId = "minecraft:dark_oak_stairs" )
@RegisterInfo( sId = "minecraft:jungle_stairs" )
@RegisterInfo( sId = "minecraft:crimson_stairs" )
@RegisterInfo( sId = "minecraft:warped_stairs" )
public class WoodenStair extends Stair<BlockWoodenStair> implements BlockWoodenStair {

    private enum LogTypeMagic {
        OAK("minecraft:oak_stairs"),
        SPRUCE("minecraft:spruce_stairs"),
        BIRCH("minecraft:birch_stairs"),
        JUNGLE("minecraft:jungle_stairs"),
        ACACIA("minecraft:acacia_stairs"),
        DARK_OAK("minecraft:dark_oak_stairs"),
        CRIMSON("minecraft:crimson_stairs"),
        WARPED("minecraft:warped_stairs");

        private final String blockId;
        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.WOODEN_STAIR;
    }

    @Override
    public LogType type() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (this.blockId().equals(value.blockId)) {
                return LogType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public BlockWoodenStair type(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());
        this.blockId(newState.blockId);
        return this;
    }

}
