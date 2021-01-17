/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import com.google.common.base.Preconditions;
import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAzt
 * @version 1.0
 */
public class ItemComponent extends AbstractTileEntityComponent {

    private final String key;
    private ItemStack<?> holdingItem = ItemAir.create( 0 );

    public ItemComponent(TileEntity entity, Items items, String key) {
        super(entity, items);
        this.key = key;
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        this.writeItem(compound, this.key, this.holdingItem);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        this.holdingItem = this.readItem(compound, this.key);
    }

    @Override
    public void update(long currentTimeMS, float dT) {

    }

    public void item(ItemStack<?> item) {
        Preconditions.checkArgument(item != null, "Item needs to be not null");
        this.holdingItem = item;
    }

}
