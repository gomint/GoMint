package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSapling;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sapling", id = 6)
@RegisterInfo( sId = "minecraft:crimson_fungus", id = -228 )
@RegisterInfo( sId = "minecraft:warped_fungus", id = -229 )
public class ItemSapling extends ItemStack implements io.gomint.inventory.item.ItemSapling {

    private enum LogTypeMagic {
        OAK("minecraft:sapling", (short) 0),
        SPRUCE("minecraft:sapling", (short) 1),
        BIRCH("minecraft:sapling", (short) 2),
        JUNGLE("minecraft:sapling", (short) 3),
        ACACIA("minecraft:sapling", (short) 4),
        DARK_OAK("minecraft:sapling", (short) 5),
        CRIMSON("minecraft:crimson_fungus", (short) 0),
        WARPED("minecraft:warped_fungus", (short) 0);

        private final String id;
        private final short data;
        LogTypeMagic(String id, short data) {
            this.id = id;
            this.data = data;
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SAPLING;
    }

    @Override
    public long getBurnTime() {
        LogType type = this.getLogType();
        return (type == LogType.CRIMSON || type == LogType.WARPED) ? 0 : 5000;
    }

    @Override
    public void setLogType(LogType type) {
        LogTypeMagic magic = LogTypeMagic.valueOf(type.name());
        this.setMaterial(magic.id);
        this.setData(magic.data);
    }

    @Override
    public LogType getLogType() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.data == this.getData() && value.id.equals(this.getMaterial())) {
                return LogType.valueOf(value.name());
            }
        }

        return LogType.OAK;
    }

    @Override
    public Block getBlock() {
        BlockSapling sapling = (BlockSapling) super.getBlock();
        sapling.setLogType(this.getLogType());
        return sapling;
    }

}
