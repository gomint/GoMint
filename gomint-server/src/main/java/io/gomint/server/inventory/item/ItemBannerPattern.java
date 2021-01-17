package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:banner_pattern" )
public class ItemBannerPattern extends ItemStack< io.gomint.inventory.item.ItemBannerPattern> implements io.gomint.inventory.item.ItemBannerPattern {

    @Override
    public ItemType itemType() {
        return ItemType.BANNER_PATTERN;
    }

}
