/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.helper;

import io.gomint.server.inventory.Inventory;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStackPlace {

    private final int slot;
    private final Inventory<?> inventory;

    public ItemStackPlace(int slot, Inventory<?> inventory) {
        this.slot = slot;
        this.inventory = inventory;
    }

    public int getSlot() {
        return slot;
    }

    public Inventory<?> getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStackPlace that = (ItemStackPlace) o;
        return slot == that.slot &&
            Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, inventory);
    }

}
