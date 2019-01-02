package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.WoodType;

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

    private enum LogType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }

    private enum Log2Type {
        ACACIA,
        DARK_OAK
    }

    private EnumBlockState<LogType> variantLog = new EnumBlockState<>(this, LogType.values(), states -> this.getBlockId().equals("minecraft:log"));
    private EnumBlockState<Log2Type> variantLog2 = new EnumBlockState<>(this, Log2Type.values(), states -> this.getBlockId().equals("minecraft:log2"));

    private DirectionBlockState direction = new DirectionBlockState(this, states -> this.getBlockId().equals("minecraft:log") || this.getBlockId().equals("minecraft:log2"), 2);
    private DirectionBlockState directionStripped = new DirectionBlockState(this, states -> !this.getBlockId().equals("minecraft:log") && !this.getBlockId().equals("minecraft:log2"));

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact(Entity entity, BlockFace face, Vector facePos, ItemStack item) {
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
            Direction storedDirection = this.direction.getState();

            if (this.getBlockId().equals("minecraft:log")) {
                switch (this.variantLog.getState()) {
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

                this.directionStripped.setState(storedDirection);
            } else if (this.getBlockId().equals("minecraft:log2")) {
                switch (this.variantLog2.getState()) {
                    case ACACIA:
                        this.setBlockId("minecraft:stripped_acacia_log");
                        break;
                    case DARK_OAK:
                        this.setBlockId("minecraft:stripped_dark_oak_log");
                        break;
                }

                this.directionStripped.setState(storedDirection);
            }
        } else {
            Direction storedDirection = this.directionStripped.getState();

            switch (this.getBlockId()) {
                case "minecraft:stripped_oak_log":
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.OAK);
                    break;
                case "minecraft:stripped_birch_log":
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.BIRCH);
                    break;
                case "minecraft:stripped_jungle_log":
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.JUNGLE);
                    break;
                case "minecraft:stripped_spruce_log":
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.SPRUCE);
                    break;
                case "minecraft:stripped_acacia_log":
                    this.setBlockId("minecraft:log2");
                    this.variantLog2.setState(Log2Type.ACACIA);
                    break;
                case "minecraft:stripped_dark_oak_log":
                    this.setBlockId("minecraft:log2");
                    this.variantLog2.setState(Log2Type.DARK_OAK);
                    break;
            }

            this.direction.setState(storedDirection);
        }
    }

    @Override
    public void setWoodType(WoodType type) {
        if (!this.isStripped()) {
            switch (type) {
                case OAK:
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.OAK);
                    break;
                case BIRCH:
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.BIRCH);
                    break;
                case JUNGLE:
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.JUNGLE);
                    break;
                case SPRUCE:
                    this.setBlockId("minecraft:log");
                    this.variantLog.setState(LogType.SPRUCE);
                    break;
                case ACACIA:
                    this.setBlockId("minecraft:log2");
                    this.variantLog2.setState(Log2Type.ACACIA);
                    break;
                case DARK_OAK:
                    this.setBlockId("minecraft:log2");
                    this.variantLog2.setState(Log2Type.DARK_OAK);
                    break;
            }
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
    public WoodType getWoodType() {
        switch (this.getBlockId()) {
            case "minecraft:log":
                switch (this.variantLog.getState()) {
                    case OAK:
                        return WoodType.OAK;
                    case SPRUCE:
                        return WoodType.SPRUCE;
                    case JUNGLE:
                        return WoodType.JUNGLE;
                    case BIRCH:
                        return WoodType.BIRCH;
                }

                break;
            case "minecraft:log2":
                switch (this.variantLog2.getState()) {
                    case ACACIA:
                        return WoodType.ACACIA;
                    case DARK_OAK:
                        return WoodType.DARK_OAK;
                }

                break;
            case "minecraft:stripped_oak_log":
                return WoodType.OAK;
            case "minecraft:stripped_birch_log":
                return WoodType.BIRCH;
            case "minecraft:stripped_jungle_log":
                return WoodType.JUNGLE;
            case "minecraft:stripped_spruce_log":
                return WoodType.SPRUCE;
            case "minecraft:stripped_acacia_log":
                return WoodType.ACACIA;
            case "minecraft:stripped_dark_oak_log":
                return WoodType.DARK_OAK;

        }

        return WoodType.OAK;
    }

    @Override
    public void setLogDirection(Direction direction) {
        this.direction.setState(direction);
    }

    @Override
    public Direction getLogDirection() {
        return this.direction.getState();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (this.getBlockId().equals("minecraft:log")) {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), this.variantLog.toData(), (byte) 1, null);
            return Lists.newArrayList(itemStack);
        } else if (this.getBlockId().equals("minecraft:log2")) {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), this.variantLog2.toData(), (byte) 1, null);
            return Lists.newArrayList(itemStack);
        } else {
            ItemStack itemStack = this.world.getServer().getItems().create(getBlockId(), (short) 0, (byte) 1, null);
            return Lists.newArrayList(itemStack);
        }
    }

}
