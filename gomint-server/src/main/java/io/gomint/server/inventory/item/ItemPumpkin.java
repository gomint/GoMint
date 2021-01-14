package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPumpkin;
import io.gomint.world.block.data.PumpkinType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:pumpkin", id = 86, def = true)
@RegisterInfo(sId = "minecraft:carved_pumpkin", id = -155)
public class ItemPumpkin extends ItemStack implements io.gomint.inventory.item.ItemPumpkin {

    @Override
    public ItemType getItemType() {
        return ItemType.PUMPKIN;
    }

    @Override
    public PumpkinType getType() {
        return this.getMaterial().equals("minecraft:pumpkin") ? PumpkinType.NORMAL : PumpkinType.CARVED;
    }

    @Override
    public void setType(PumpkinType type) {
        this.setMaterial(type == PumpkinType.NORMAL ? "minecraft:pumpkin" : "minecraft:carved_pumpkin");
    }

    @Override
    public Block getBlock() {
        BlockPumpkin pumpkin = (BlockPumpkin) super.getBlock();
        pumpkin.type(this.getType());
        return pumpkin;
    }

}
