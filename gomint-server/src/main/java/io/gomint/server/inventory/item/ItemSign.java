package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 323, sId = "minecraft:sign")
@RegisterInfo(id = 472, sId = "minecraft:spruce_sign")
@RegisterInfo(id = 473, sId = "minecraft:birch_sign")
@RegisterInfo(id = 474, sId = "minecraft:jungle_sign")
@RegisterInfo(id = 475, sId = "minecraft:acacia_sign")
@RegisterInfo(id = 476, sId = "minecraft:darkoak_sign")
public class ItemSign extends ItemStack implements io.gomint.inventory.item.ItemSign {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSign.class);

    @Override
    public String getBlockId() {
        switch (this.getMaterial()) {
            case 323:
                return "minecraft:sign";
            case 472:
                return "minecraft:spruce_sign";
            case 473:
                return "minecraft:birch_sign";
            case 474:
                return "minecraft:jungle_sign";
            case 475:
                return "minecraft:acacia_sign";
            case 476:
                return "minecraft:darkoak_sign";
        }

        LOGGER.warn("Unknown sign type: {}", this.getMaterial());
        return "unknown";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SIGN;
    }

}
