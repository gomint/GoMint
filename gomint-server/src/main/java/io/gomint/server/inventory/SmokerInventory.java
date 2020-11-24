/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.type.WindowType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SmokerInventory extends ContainerInventory {

    /**
     * Construct a new container inventory
     *
     * @param owner of the container (mostly a tile or normal entity)
     */
    public SmokerInventory(Items items, InventoryHolder owner) {
        super(items, owner, 3);
    }

    @Override
    public WindowType getType() {
        return WindowType.SMOKER;
    }

    @Override
    public void onOpen(EntityPlayer player) {

    }

    @Override
    public void onClose(EntityPlayer player) {

    }

}
