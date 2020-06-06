package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 434, sId = "minecraft:banner_pattern" )
public class ItemBannerPattern extends ItemStack implements io.gomint.inventory.item.ItemBannerPattern {

    @Override
    public ItemType getType() {
        return ItemType.BANNER_PATTERN;
    }

}
