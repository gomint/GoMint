/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

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
public abstract class ChestBase extends ContainerBlock {

    protected static final BlockfaceFromPlayerBlockState DIRECTION = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, false);

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
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
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        boolean ok = super.beforePlacement(entity, item, face, location);
        DIRECTION.detectFromPlacement(this, entity, item, face);
        return ok;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        ChestTileEntity tileEntity = this.getTileEntity();
        if (tileEntity != null) {
            tileEntity.interact(entity, face, facePos, item);
        }

        return true;
    }

    @Override
    public void afterPlacement() {
        // Check for pairing
        for (Direction value : Direction.values()) {
            Block side = this.getSide(value);
            if (side.getBlockType() == this.getBlockType()) {
                ChestTileEntity tileEntity = this.getTileEntity();
                tileEntity.pair(((ChestBase) side).getTileEntity());
                side.updateBlock();
            }
        }

        super.afterPlacement();
    }

    protected Inventory getInventory() {
        ChestTileEntity tileEntity = this.getTileEntity();
        if (tileEntity != null) {
            return tileEntity.getInventory();
        }

        return null;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        List<ItemStack> items = super.getDrops(itemInHand);

        // We also drop the inventory
        ChestTileEntity chestTileEntity = this.getTileEntity();
        chestTileEntity.unpair();
        chestTileEntity.getInventory()
            .items()
            .filter(Objects::nonNull)
            .filter(item -> item.getItemType() != ItemType.AIR)
            .forEach(items::add);

        return items;
    }

}
