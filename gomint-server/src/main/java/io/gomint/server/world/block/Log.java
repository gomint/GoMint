package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.AxisBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;
import lombok.Getter;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:log", def = true)
@RegisterInfo(sId = "minecraft:log2")
@RegisterInfo(sId = "minecraft:stripped_oak_log")
@RegisterInfo(sId = "minecraft:stripped_spruce_log")
@RegisterInfo(sId = "minecraft:stripped_acacia_log")
@RegisterInfo(sId = "minecraft:stripped_dark_oak_log")
@RegisterInfo(sId = "minecraft:stripped_jungle_log")
@RegisterInfo(sId = "minecraft:stripped_birch_log")
public class Log extends Block implements BlockLog {

    private static final String OLD_LOG_TYPE = "old_log_type";
    private static final String OLD_LOG_ID = "minecraft:log";

    private static final String NEW_LOG_TYPE = "new_log_type";
    private static final String NEW_LOG_ID = "minecraft:log2";

    @Getter
    private enum LogTypeMagic {
        OAK(OLD_LOG_ID, OLD_LOG_TYPE, "oak"),
        SPRUCE(OLD_LOG_ID, OLD_LOG_TYPE, "spruce"),
        BIRCH(OLD_LOG_ID, OLD_LOG_TYPE, "birch"),
        JUNGLE(OLD_LOG_ID, OLD_LOG_TYPE, "jungle"),
        ACACIA(NEW_LOG_ID, NEW_LOG_TYPE, "acacia"),
        DARK_OAK(NEW_LOG_ID, NEW_LOG_TYPE, "dark_oak");


        private final String key;
        private final String value;
        private final String blockId;

        LogTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        if (v == null) {
            return new String[]{OLD_LOG_TYPE, NEW_LOG_TYPE};
        }

        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.getValue().equals(v)) {
                return value.getKey().equals(OLD_LOG_TYPE) ? new String[]{OLD_LOG_TYPE, NEW_LOG_TYPE} : new String[]{NEW_LOG_TYPE, OLD_LOG_TYPE};
            }
        }

        return new String[]{OLD_LOG_TYPE, NEW_LOG_TYPE};
    }, LogTypeMagic.values(), LogTypeMagic::getValue, v -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.getValue().equals(v)) {
                return value;
            }
        }

        return null;
    });
    private static final AxisBlockState AXIS = new AxisBlockState(() -> new String[]{"pillar_axis"});

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        if (entity instanceof EntityPlayer && this.isCorrectTool(item) && !this.isStripped()) {
            this.setStripped(true);
            return true;
        }

        return false;
    }

    @Override
    public boolean isStripped() {
        return !this.getBlockId().equals("minecraft:log") && !this.getBlockId().equals("minecraft:log2");
    }

    @Override
    public void setStripped(boolean stripped) {
        boolean isCurrentlyStripped = this.isStripped();
        if (stripped == isCurrentlyStripped) {
            return;
        }

        if (stripped) {
            if (this.getBlockId().equals("minecraft:log")) {
                switch (VARIANT.getState(this)) {
                    case OAK:
                        this.setBlockId("minecraft:stripped_oak_log");
                        break;
                    case BIRCH:
                        this.setBlockId("minecraft:stripped_birch_log");
                        break;
                    case JUNGLE:
                        this.setBlockId("minecraft:stripped_jungle_log");
                        break;
                    case SPRUCE:
                        this.setBlockId("minecraft:stripped_spruce_log");
                        break;
                }
            } else if (this.getBlockId().equals("minecraft:log2")) {
                switch (VARIANT.getState(this)) {
                    case ACACIA:
                        this.setBlockId("minecraft:stripped_acacia_log");
                        break;
                    case DARK_OAK:
                        this.setBlockId("minecraft:stripped_dark_oak_log");
                        break;
                }
            }
        } else {
            LogTypeMagic newState;
            switch (this.getBlockId()) {
                default:
                case "minecraft:stripped_oak_log":
                    newState = LogTypeMagic.OAK;
                    break;
                case "minecraft:stripped_birch_log":
                    newState = LogTypeMagic.BIRCH;
                    break;
                case "minecraft:stripped_jungle_log":
                    newState = LogTypeMagic.JUNGLE;
                    break;
                case "minecraft:stripped_spruce_log":
                    newState = LogTypeMagic.SPRUCE;
                    break;
                case "minecraft:stripped_acacia_log":
                    newState = LogTypeMagic.ACACIA;
                    break;
                case "minecraft:stripped_dark_oak_log":
                    newState = LogTypeMagic.DARK_OAK;
                    break;
            }

            this.setBlockIdOnStateChange(newState.getBlockId());
            VARIANT.setState(this, newState);
        }
    }

    @Override
    public void setLogType(LogType type) {
        LogTypeMagic newState = LogTypeMagic.valueOf(type.name());

        if (!this.isStripped()) {
            this.setBlockIdOnStateChange(newState.getBlockId());
            VARIANT.setState(this, newState);
        } else {
            switch (type) {
                case OAK:
                    this.setBlockId("minecraft:stripped_oak_log");
                    break;
                case BIRCH:
                    this.setBlockId("minecraft:stripped_birch_log");
                    break;
                case JUNGLE:
                    this.setBlockId("minecraft:stripped_jungle_log");
                    break;
                case SPRUCE:
                    this.setBlockId("minecraft:stripped_spruce_log");
                    break;
                case ACACIA:
                    this.setBlockId("minecraft:stripped_acacia_log");
                    break;
                case DARK_OAK:
                    this.setBlockId("minecraft:stripped_dark_oak_log");
                    break;
            }
        }
    }

    @Override
    public LogType getLogType() {
        switch (this.getBlockId()) {
            default:
                return LogType.valueOf(VARIANT.getState(this).name());
            case "minecraft:stripped_oak_log":
                return LogType.OAK;
            case "minecraft:stripped_birch_log":
                return LogType.BIRCH;
            case "minecraft:stripped_jungle_log":
                return LogType.JUNGLE;
            case "minecraft:stripped_spruce_log":
                return LogType.SPRUCE;
            case "minecraft:stripped_acacia_log":
                return LogType.ACACIA;
            case "minecraft:stripped_dark_oak_log":
                return LogType.DARK_OAK;
        }
    }

    @Override
    public void setAxis(Axis axis) {
        AXIS.setState(this, axis);
    }

    @Override
    public Axis getAxis() {
        return AXIS.getState(this);
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        // TODO: Check for new data values
        if (this.getBlockId().equals("minecraft:log")) {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), (short) 0, (byte) 1, null);
            return Lists.newArrayList(itemStack);
        } else if (this.getBlockId().equals("minecraft:log2")) {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), (short) 0, (byte) 1, null);
            return Lists.newArrayList(itemStack);
        } else {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), (short) 0, (byte) 1, null);
            return Lists.newArrayList(itemStack);
        }
    }

}
