package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPlank;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 5, sId = ItemPlank.PLANK_ID1)
@RegisterInfo(sId = ItemPlank.CRIMSON_ID, id = -242)
@RegisterInfo(sId = ItemPlank.WARPED_ID, id = -243)
public class ItemPlank extends ItemStack implements io.gomint.inventory.item.ItemPlank {

    /**
     * Item id for normal planks
     */
    protected static final String PLANK_ID1 = "minecraft:planks";

    /**
     * Item id for crimson planks
     */
    protected static final String CRIMSON_ID = "minecraft:crimson_planks";

    /**
     * Item id from warped planks
     */
    protected static final String WARPED_ID = "minecraft:warped_planks";

    private enum LogTypeMagic {
        OAK(PLANK_ID1, (short) 0),
        SPRUCE(PLANK_ID1, (short) 1),
        BIRCH(PLANK_ID1, (short) 2),
        JUNGLE(PLANK_ID1, (short) 3),
        ACACIA(PLANK_ID1, (short) 4),
        DARK_OAK(PLANK_ID1, (short) 5),
        CRIMSON(CRIMSON_ID, (short) 0),
        WARPED(WARPED_ID, (short) 0);

        private final String id;
        private final short data;

        LogTypeMagic(String id, short data) {
            this.id = id;
            this.data = data;
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.PLANK;
    }

    @Override
    public LogType getPlankType() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.id.equals(this.getMaterial()) && value.data == this.getData()) {
                return LogType.valueOf(value.name());
            }
        }

        return LogType.OAK;
    }

    @Override
    public void setPlankType(LogType type) {
        LogTypeMagic state = LogTypeMagic.valueOf(type.name());
        this.setMaterial(state.id);
        this.setData(state.data);
    }

    @Override
    public Block getBlock() {
        BlockPlank plank = (BlockPlank) super.getBlock();
        plank.type(this.getPlankType());
        return plank;
    }

    @Override
    public long getBurnTime() {
        boolean isNether = (this.getPlankType() == LogType.CRIMSON || this.getPlankType() == LogType.WARPED);
        return !isNether ? 15000 : 0;
    }
}
