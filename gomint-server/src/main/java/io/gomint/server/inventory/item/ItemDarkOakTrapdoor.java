/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dark_oak_trapdoor" )
public class ItemDarkOakTrapdoor extends ItemStack< io.gomint.inventory.item.ItemDarkOakTrapdoor> implements io.gomint.inventory.item.ItemDarkOakTrapdoor {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }
    
    @Override
    public ItemType itemType() {
        return ItemType.DARK_OAK_TRAPDOOR;
    }

}
