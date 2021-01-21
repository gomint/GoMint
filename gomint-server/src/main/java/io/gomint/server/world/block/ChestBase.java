/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.ChestInventory;
import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.List;
import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ChestBase<B> extends ContainerBlock<B> {

    protected static final BlockfaceFromPlayerBlockState DIRECTION = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, false);

    @Override
    public long breakTime() {
        return 3750;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 12.5f;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(ChestTileEntity.class, compound, this, this.items);
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        boolean ok = super.beforePlacement(entity, item, face, location, clickVector);
        DIRECTION.detectFromPlacement(this, entity, item, face, clickVector);
        return ok;
    }

    @Override
    public boolean interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        ChestTileEntity tileEntity = this.tileEntity();
        if (tileEntity != null) {
            tileEntity.interact(entity, face, facePos, item);
        }

        return true;
    }

    @Override
    public void afterPlacement() {
        // Check for pairing
        for (Direction value : Direction.values()) {
            Block side = this.side(value);
            if (side.blockType() == this.blockType()) {
                ChestTileEntity tileEntity = this.tileEntity();
                tileEntity.pair(side.tileEntity());
                side.updateBlock();
            }
        }

        super.afterPlacement();
    }

    protected Inventory<ChestInventory> inventory() {
        ChestTileEntity tileEntity = this.tileEntity();
        if (tileEntity != null) {
            return tileEntity.inventory();
        }

        return null;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        List<ItemStack<?>> items = super.drops(itemInHand);

        // We also drop the inventory
        ChestTileEntity chestTileEntity = this.tileEntity();
        chestTileEntity.unpair();
        chestTileEntity.inventory()
            .items()
            .filter(Objects::nonNull)
            .filter(item -> item.itemType() != ItemType.AIR)
            .forEach(items::add);

        return items;
    }

}
