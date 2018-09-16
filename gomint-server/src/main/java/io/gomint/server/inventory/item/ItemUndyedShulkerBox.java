/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 205 )
public class ItemUndyedShulkerBox extends ItemStack implements io.gomint.inventory.item.ItemUndyedShulkerBox {



    @Override
    public String getBlockId() {
        return "minecraft:undyed_shulker_box";
    }

    @Override
    public ItemType getType() {
        return ItemType.UNDYED_SHULKER_BOX;
    }

}
