/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemFood extends ItemStack implements io.gomint.inventory.item.ItemFood, ItemConsumable {

    // CHECKSTYLE:OFF
    public ItemFood( int material, short data, int amount ) {
        super( material, data, amount );
    }

    public ItemFood( int material, short data, int amount, NBTTagCompound nbt ) {
        super( material, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public void onConsume( EntityPlayer player ) {
        player.addHunger( getHunger() );

        float saturation = Math.min( player.getSaturation() + ( getHunger() * getSaturation() * 2.0f ), player.getHunger() );
        player.setSaturation( saturation );

        // Default manipulation
        this.afterPlacement();
    }

}
