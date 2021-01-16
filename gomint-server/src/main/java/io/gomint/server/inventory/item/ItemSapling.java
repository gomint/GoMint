package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSapling;
import io.gomint.world.block.data.LogType;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sapling", id = 6)
@RegisterInfo( sId = "minecraft:crimson_fungus", id = -228 )
@RegisterInfo( sId = "minecraft:warped_fungus", id = -229 )
public class ItemSapling extends ItemStack< io.gomint.inventory.item.ItemSapling> implements io.gomint.inventory.item.ItemSapling {

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
    public ItemType itemType() {
        return ItemType.SAPLING;
    }

    @Override
    public Duration burnTime() {
        LogType type = this.type();
        return (type == LogType.CRIMSON || type == LogType.WARPED) ? null : Duration.ofMillis(5000);
    }

    @Override
    public ItemSapling type(LogType type) {
        LogTypeMagic magic = LogTypeMagic.valueOf(type.name());
        this.material(magic.id);
        this.data(magic.data);
        return this;
    }

    @Override
    public LogType type() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.data == this.data() && value.id.equals(this.material())) {
                return LogType.valueOf(value.name());
            }
        }

        return LogType.OAK;
    }

    @Override
    public Block block() {
        BlockSapling sapling = (BlockSapling) super.block();
        sapling.type(this.type());
        return sapling;
    }

}
