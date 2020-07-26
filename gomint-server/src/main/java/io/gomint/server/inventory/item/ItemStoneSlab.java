package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 44, sId = "minecraft:double_stone_slab", def = true)
@RegisterInfo(id = -162, sId = "minecraft:double_stone_slab3")
@RegisterInfo(id = -166, sId = "minecraft:double_stone_slab4")
public class ItemStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemStoneSlab {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStoneSlab.class);

    @Override
    public String getBlockId() {
        switch (this.getMaterial()) {
            case 44:
                return "minecraft:double_stone_slab";
            case -162:
                return "minecraft:double_stone_slab3";
            case -166:
                return "minecraft:double_stone_slab4";
        }

        LOGGER.warn("Unknown stone slab type: {}", this.getMaterial());
        return "unknown";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.STONE_SLAB;
    }

}
