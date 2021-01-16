package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPlank;
import io.gomint.world.block.data.LogType;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 5, sId = ItemPlank.PLANK_ID1)
@RegisterInfo(sId = ItemPlank.CRIMSON_ID, id = -242)
@RegisterInfo(sId = ItemPlank.WARPED_ID, id = -243)
public class ItemPlank extends ItemStack< io.gomint.inventory.item.ItemPlank> implements io.gomint.inventory.item.ItemPlank {

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
    public ItemType itemType() {
        return ItemType.PLANK;
    }

    @Override
    public LogType type() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.id.equals(this.material()) && value.data == this.data()) {
                return LogType.valueOf(value.name());
            }
        }

        return LogType.OAK;
    }

    @Override
    public ItemPlank type(LogType type) {
        LogTypeMagic state = LogTypeMagic.valueOf(type.name());
        this.material(state.id);
        this.data(state.data);
        return this;
    }

    @Override
    public Block block() {
        BlockPlank plank = (BlockPlank) super.block();
        plank.type(this.type());
        return plank;
    }

    @Override
    public Duration burnTime() {
        boolean isNether = (this.type() == LogType.CRIMSON || this.type() == LogType.WARPED);
        return !isNether ? Duration.ofMillis(15000) : null;
    }

}
