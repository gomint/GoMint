package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

public class ItemSweetBerries extends ItemFood implements io.gomint.inventory.item.ItemSweetBerries {

    @Override
    public ItemType getType() {
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
