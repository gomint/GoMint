/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:scaffolding" )
public class ItemScaffolding extends ItemStack< io.gomint.inventory.item.ItemScaffolding> implements io.gomint.inventory.item.ItemScaffolding {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(60000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.SCAFFOLDING;
    }

}
