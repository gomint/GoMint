/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.BannerTileEntity;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

import java.util.List;
import java.util.Objects;

public abstract class ChestBase extends ContainerBlock {

    protected static final BlockfaceBlockState DIRECTION = new BlockfaceBlockState(() -> new String[]{"facing_direction"});

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
        super.createTileEntity( compound );
        return this.world.getServer().getTileEntities().construct(ChestTileEntity.class, compound, this, this.world.getServer().getItems());
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        DIRECTION.detectFromPlacement(this, entity, item, face, block, clickedBlock, clickVector);
        return new PlacementData(this.identifier, null);
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        ChestTileEntity tileEntity = this.getTileEntity();
        if (tileEntity != null) {
            tileEntity.interact(entity, face, facePos, item);
        }

        return true;
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
        chestTileEntity.getInventory()
            .items()
            .filter(Objects::nonNull)
            .filter(item -> item.getItemType() != ItemType.AIR)
            .forEach(items::add);

        return items;
    }

}
