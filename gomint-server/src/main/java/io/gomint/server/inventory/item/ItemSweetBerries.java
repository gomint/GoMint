package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

@RegisterInfo( id = 477, sId = "minecraft:sweet_berries")
public class ItemSweetBerries extends ItemFood<io.gomint.inventory.item.ItemSweetBerries> implements io.gomint.inventory.item.ItemSweetBerries {

    @Override
    public ItemType itemType() {
        return ItemType.SWEETBERRIES;
    }

    @Override
    public float getSaturation() {
        return 0; //TODO
    }

    @Override
    public float getHunger() {
        return 1;
    }
    
}
