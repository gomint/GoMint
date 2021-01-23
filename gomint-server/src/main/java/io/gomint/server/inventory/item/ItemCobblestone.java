package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone", def = true)
@RegisterInfo(sId = "minecraft:mossy_cobblestone")
public class ItemCobblestone extends ItemStack<io.gomint.inventory.item.ItemCobblestone> implements io.gomint.inventory.item.ItemCobblestone {

    @Override
    public ItemType itemType() {
        return ItemType.COBBLESTONE;
    }

    @Override
    public io.gomint.inventory.item.ItemCobblestone type(CobblestoneType type) {
        return type == CobblestoneType.NORMAL ? this.material("minecraft:cobblestone") : this.material("minecraft:mossy_cobblestone");
    }

    @Override
    public CobblestoneType type() {
        return "minecraft:cobblestone".equals(this.material()) ? CobblestoneType.NORMAL : CobblestoneType.MOSSY;
    }

}
