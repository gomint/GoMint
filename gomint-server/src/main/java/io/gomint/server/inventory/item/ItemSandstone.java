package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSandstone;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sandstone", def = true)
@RegisterInfo(sId = "minecraft:red_sandstone")
public class ItemSandstone extends ItemStack< io.gomint.inventory.item.ItemSandstone> implements io.gomint.inventory.item.ItemSandstone {

    @Override
    public ItemType itemType() {
        return ItemType.SANDSTONE;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstone color(Sandcolor color) {
        return this.material(color == Sandcolor.NORMAL ? "minecraft:sandstone" : "minecraft:red_sandstone");
    }

    @Override
    public Sandcolor color() {
        return this.material().equals("minecraft:sandstone") ? Sandcolor.NORMAL : Sandcolor.RED;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstone type(SandstoneType type) {
        switch (type) {
            case NORMAL:
                return this.data((short) 0);
            case CHISELED:
                return this.data((short) 1);
            case CUT:
                return this.data((short) 2);
            case SMOOTH:
                return this.data((short) 3);
        }

        return this;
    }

    @Override
    public SandstoneType type() {
        switch (this.data()) {
            case 0:
                return SandstoneType.NORMAL;
            case 1:
                return SandstoneType.CHISELED;
            case 2:
                return SandstoneType.CUT;
            case 3:
                return SandstoneType.SMOOTH;
        }

        return SandstoneType.NORMAL;
    }

    @Override
    public Block block() {
        BlockSandstone block = (BlockSandstone) super.block();
        return block
            .type(this.type())
            .color(this.color());
    }

}
