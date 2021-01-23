package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_stairs", def = true)
@RegisterInfo(sId = "minecraft:mossy_cobblestone_stairs")
public class ItemCobblestoneStairs extends ItemStack<io.gomint.inventory.item.ItemCobblestoneStairs> implements io.gomint.inventory.item.ItemCobblestoneStairs {

    @Override
    public ItemType itemType() {
        return ItemType.COBBLESTONE_STAIRS;
    }

    @Override
    public io.gomint.inventory.item.ItemCobblestoneStairs type(CobblestoneType type) {
        return type == CobblestoneType.NORMAL ? this.material("minecraft:stone_stairs") : this.material("minecraft:mossy_cobblestone_stairs");
    }

    @Override
    public CobblestoneType type() {
        return "minecraft:stone_stairs".equals(this.material()) ? CobblestoneType.NORMAL : CobblestoneType.MOSSY;
    }

}
