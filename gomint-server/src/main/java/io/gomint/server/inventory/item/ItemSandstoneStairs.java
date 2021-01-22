package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.RoughnessType;
import io.gomint.world.block.data.Sandcolor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sandstone_stairs", def = true)
@RegisterInfo(sId = "minecraft:red_sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_red_sandstone_stairs")
public class ItemSandstoneStairs extends ItemStack< io.gomint.inventory.item.ItemSandstoneStairs> implements io.gomint.inventory.item.ItemSandstoneStairs {

    @Override
    public ItemType itemType() {
        return ItemType.SANDSTONE_STAIRS;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstoneStairs color(Sandcolor color) {
        switch (color) {
            case NORMAL:
                return this.material(this.type() == RoughnessType.SMOOTH ? "minecraft:smooth_sandstone_stairs" : "minecraft:sandstone_stairs");
            case RED:
                return this.material(this.type() == RoughnessType.SMOOTH ? "minecraft:smooth_red_sandstone_stairs" : "minecraft:red_sandstone_stairs");
        }

        return null;
    }

    @Override
    public Sandcolor color() {
        return this.material().contains("red_") ? Sandcolor.RED : Sandcolor.NORMAL;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstoneStairs type(RoughnessType type) {
        switch (type) {
            case NORMAL:
                return this.material(this.color() == Sandcolor.RED ? "minecraft:red_sandstone_stairs" : "minecraft:sandstone_stairs");
            case SMOOTH:
                return this.material(this.color() == Sandcolor.RED ? "minecraft:smooth_red_sandstone_stairs" : "minecraft:smooth_sandstone_stairs");
        }

        return this;
    }

    @Override
    public RoughnessType type() {
        return this.material().contains("smooth_") ? RoughnessType.SMOOTH : RoughnessType.NORMAL;
    }

}
