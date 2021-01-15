/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockIdentifierComponent extends AbstractTileEntityComponent {

    private final String key;
    private BlockIdentifier blockIdentifier;

    public BlockIdentifierComponent(TileEntity entity, Items items, String key) {
        super(entity, items);
        this.key = key;
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        putBlockIdentifier(this.blockIdentifier, compound.getCompound(this.key, true));
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        this.blockIdentifier = getBlockIdentifier(compound.getCompound(this.key, false));
    }

    @Override
    public void update(long currentTimeMS, float dT) {

    }

    public BlockIdentifier getBlockIdentifier() {
        return this.blockIdentifier;
    }

}
