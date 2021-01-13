package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWallSign;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wall_sign", def = true)
@RegisterInfo(sId = "minecraft:jungle_wall_sign")
@RegisterInfo(sId = "minecraft:acacia_wall_sign")
@RegisterInfo(sId = "minecraft:birch_wall_sign")
@RegisterInfo(sId = "minecraft:spruce_wall_sign")
@RegisterInfo(sId = "minecraft:darkoak_wall_sign")
@RegisterInfo(sId = "minecraft:crimson_wall_sign")
@RegisterInfo(sId = "minecraft:warped_wall_sign")
public class WallSign extends Sign implements BlockWallSign {

    private enum LogTypeMagic {
        OAK("minecraft:wall_sign"),
        SPRUCE("minecraft:spruce_wall_sign"),
        BIRCH("minecraft:birch_wall_sign"),
        JUNGLE("minecraft:jungle_wall_sign"),
        ACACIA("minecraft:acacia_wall_sign"),
        DARK_OAK("minecraft:darkoak_wall_sign"),
        CRIMSON("minecraft:crimson_wall_sign"),
        WARPED("minecraft:warped_wall_sign");

        private final String blockId;

        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    private static final BlockfaceFromPlayerBlockState FACING = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, false);

    @Override
    public LogType getWoodType() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.blockId.equals(this.getBlockId())) {
                return LogType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setWoodType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());
        this.setBlockId(newState.blockId);
    }

    @Override
    public BlockType blockType() {
        return BlockType.WALL_SIGN;
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
