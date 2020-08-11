/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;

public abstract class AbstractTileEntityComponent implements TileEntityComponent {

    private final Items items;

    public AbstractTileEntityComponent(Items items) {
        this.items = items;
    }

    io.gomint.server.inventory.item.ItemStack getItemStack( NBTTagCompound compound ) {
        // Item not there?
        if ( compound == null ) {
            return this.items.create( 0, (short) 0, (byte) 0, null );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 0 );

        // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
        try {
            return this.items.create( compound.getShort( "id", (short) 0 ), data, amount, compound.getCompound( "tag", false ) );
        } catch ( ClassCastException e ) {
            try {
                return this.items.create( compound.getString( "id", "minecraft:air" ), data, amount, compound.getCompound( "tag", false ) );
            } catch ( ClassCastException e1 ) {
                return this.items.create( compound.getInteger( "id", 0 ), data, amount, compound.getCompound( "tag", false ) );
            }
        }
    }

    void putItemStack( io.gomint.server.inventory.item.ItemStack itemStack, NBTTagCompound compound ) {
        compound.addValue( "id", (short) itemStack.getMaterial() );
        compound.addValue( "Damage", itemStack.getData() );
        compound.addValue( "Count", itemStack.getAmount() );

        if ( itemStack.getNbtData() != null ) {
            NBTTagCompound itemTag = itemStack.getNbtData().deepClone( "tag" );
            compound.addValue( "tag", itemTag );
        }
    }

}
