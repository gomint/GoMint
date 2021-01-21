package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemLog;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.AxisBlockState;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.LogType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:log", def = true)
@RegisterInfo(sId = "minecraft:log2")
@RegisterInfo(sId = "minecraft:crimson_stem")
@RegisterInfo(sId = "minecraft:warped_stem")
@RegisterInfo(sId = "minecraft:stripped_oak_log")
@RegisterInfo(sId = "minecraft:stripped_spruce_log")
@RegisterInfo(sId = "minecraft:stripped_acacia_log")
@RegisterInfo(sId = "minecraft:stripped_dark_oak_log")
@RegisterInfo(sId = "minecraft:stripped_jungle_log")
@RegisterInfo(sId = "minecraft:stripped_birch_log")
@RegisterInfo(sId = "minecraft:stripped_crimson_stem")
@RegisterInfo(sId = "minecraft:stripped_warped_stem")
@RegisterInfo(sId = "minecraft:stripped_crimson_hyphae")
@RegisterInfo(sId = "minecraft:stripped_warped_hyphae")
@RegisterInfo(sId = "minecraft:wood")
@RegisterInfo(sId = "minecraft:warped_hyphae")
@RegisterInfo(sId = "minecraft:crimson_hyphae")
public class Log extends Block implements BlockLog {

    private static final String OLD_LOG_TYPE = "old_log_type";
    private static final String OLD_LOG_ID = "minecraft:log";
    private static final String OLD_WOOD_ID = "minecraft:wood";
    private static final String OLD_WOOD_TYPE = "wood_type";

    private static final String NEW_LOG_TYPE = "new_log_type";
    private static final String NEW_LOG_ID = "minecraft:log2";

    private enum LogTypeMagic {
        OAK(OLD_WOOD_ID, OLD_LOG_ID, "oak"),
        SPRUCE(OLD_WOOD_ID, OLD_LOG_ID, "spruce"),
        BIRCH(OLD_WOOD_ID, OLD_LOG_ID, "birch"),
        JUNGLE(OLD_WOOD_ID, OLD_LOG_ID, "jungle"),
        ACACIA(OLD_WOOD_ID, NEW_LOG_ID, "acacia"),
        DARK_OAK(OLD_WOOD_ID, NEW_LOG_ID, "dark_oak"),
        CRIMSON("minecraft:crimson_hyphae", "minecraft:crimson_stem", ""),
        WARPED("minecraft:warped_hyphae", "minecraft:warped_stem", "");

        private final String fullTextureBlockId;
        private final String blockId;
        private final String value;

        LogTypeMagic(String fullTextureBlockId, String blockId, String value) {
            this.fullTextureBlockId = fullTextureBlockId;
            this.blockId = blockId;
            this.value = value;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{OLD_LOG_TYPE, NEW_LOG_TYPE, OLD_WOOD_TYPE};
    }, LogTypeMagic.values(), v -> v.value, v -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    private static final AxisBlockState AXIS = new AxisBlockState(() -> new String[]{"pillar_axis"});
    private static final BooleanBlockState STRIPPED = new BooleanBlockState(() -> new String[]{"stripped_bit"});

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        super.beforePlacement(entity, item, face, location, clickVector);
        AXIS.detectFromPlacement(this, entity, item, face, clickVector);
        return true;
    }

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public float blastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        if (entity instanceof EntityPlayer && this.isCorrectTool(item) && !this.stripped()) {
            this.stripped(true);
            return true;
        }

        return false;
    }

    @Override
    public boolean stripped() {
        return this.blockId().startsWith("minecraft:stripped_");
    }

    private BlockLog update(LogType type, boolean stripped, boolean fullTexture) {
        LogTypeMagic state = LogTypeMagic.valueOf(type.name());
        STRIPPED.state(this, stripped);

        if (!stripped) {
            this.blockIdOnStateChange(fullTexture ? state.fullTextureBlockId : state.blockId);
            VARIANT.state(this, state);
        } else {
            this.setBlockIdFromType(state, fullTexture);
        }

        return this;
    }

    @Override
    public BlockLog stripped(boolean stripped) {
        boolean isCurrentlyStripped = this.stripped();
        if (stripped == isCurrentlyStripped) {
            return this;
        }

        return this.update(this.type(), stripped, this.barkOnAllSides());
    }

    @Override
    public BlockLog type(LogType type) {
        return this.update(type, this.stripped(), this.barkOnAllSides());
    }

    private void setBlockIdFromType(LogTypeMagic type, boolean fullTexture) {
        switch (type) {
            case OAK:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_oak_log");
                break;
            case BIRCH:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_birch_log");
                break;
            case JUNGLE:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_jungle_log");
                break;
            case SPRUCE:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_spruce_log");
                break;
            case ACACIA:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_acacia_log");
                break;
            case DARK_OAK:
                this.blockId(fullTexture ? type.fullTextureBlockId : "minecraft:stripped_dark_oak_log");
                break;
            case CRIMSON:
                this.blockId(fullTexture ? "minecraft:stripped_crimson_hyphae" : "minecraft:stripped_crimson_stem");
                break;
            case WARPED:
                this.blockId(fullTexture ? "minecraft:stripped_warped_hyphae" : "minecraft:stripped_warped_stem");
                break;
        }
    }

    @Override
    public LogType type() {
        switch (this.blockId()) {
            default:
                return LogType.valueOf(VARIANT.state(this).name());
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
            case "minecraft:stripped_crimson_stem":
            case "minecraft:stripped_crimson_hyphae":
            case "minecraft:crimson_hyphae":
            case "minecraft:crimson_stem":
                return LogType.CRIMSON;
            case "minecraft:stripped_warped_hyphae":
            case "minecraft:stripped_warped_stem":
            case "minecraft:warped_hyphae":
            case "minecraft:warped_stem":
                return LogType.WARPED;
        }
    }

    @Override
    public BlockLog barkOnAllSides(boolean allSides) {
        boolean isAllSides = this.barkOnAllSides();
        if (allSides == isAllSides) {
            return this;
        }

        return this.update(this.type(), this.stripped(), allSides);
    }

    @Override
    public boolean barkOnAllSides() {
        return this.blockId().equals(OLD_WOOD_ID) ||
            this.blockId().endsWith("_hyphae");
    }

    @Override
    public BlockLog axis(Axis axis) {
        AXIS.state(this, axis);
        return this;
    }

    @Override
    public Axis axis() {
        return AXIS.state(this);
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        ItemLog item = ItemLog.create(1);
        item.type(this.type());
        item.stripped(this.stripped());
        item.barkOnAllSides(this.barkOnAllSides());
        return Collections.singletonList(item);
    }

}
