package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.SignDirectionBlockState;
import io.gomint.world.block.BlockStandingSign;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;
import io.gomint.world.block.data.SignDirection;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:standing_sign", def = true)
@RegisterInfo(sId = "minecraft:jungle_standing_sign")
@RegisterInfo(sId = "minecraft:acacia_standing_sign")
@RegisterInfo(sId = "minecraft:birch_standing_sign")
@RegisterInfo(sId = "minecraft:spruce_standing_sign")
@RegisterInfo(sId = "minecraft:darkoak_standing_sign")
@RegisterInfo(sId = "minecraft:crimson_standing_sign")
@RegisterInfo(sId = "minecraft:warped_standing_sign")
public class StandingSign extends Sign implements BlockStandingSign {

    private enum LogTypeMagic {
        OAK("minecraft:standing_sign"),
        SPRUCE("minecraft:spruce_standing_sign"),
        BIRCH("minecraft:birch_standing_sign"),
        JUNGLE("minecraft:jungle_standing_sign"),
        ACACIA("minecraft:acacia_standing_sign"),
        DARK_OAK("minecraft:darkoak_standing_sign"),
        CRIMSON("minecraft:crimson_standing_sign"),
        WARPED("minecraft:warped_standing_sign");

        private final String blockId;

        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    private static final SignDirectionBlockState DIRECTION = new SignDirectionBlockState(() -> new String[]{"ground_sign_direction"});

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
    public SignDirection getSignDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    public void setSignDirection(SignDirection direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public BlockType blockType() {
        return BlockType.SIGN;
    }
}
