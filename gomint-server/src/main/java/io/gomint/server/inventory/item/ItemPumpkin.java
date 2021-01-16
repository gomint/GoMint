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
public class ItemPumpkin extends ItemStack< io.gomint.inventory.item.ItemPumpkin> implements io.gomint.inventory.item.ItemPumpkin {

    @Override
    public ItemType itemType() {
        return ItemType.PUMPKIN;
    }

    @Override
    public PumpkinType type() {
        return this.material().equals("minecraft:pumpkin") ? PumpkinType.NORMAL : PumpkinType.CARVED;
    }

    @Override
    public ItemPumpkin type(PumpkinType type) {
        this.material(type == PumpkinType.NORMAL ? "minecraft:pumpkin" : "minecraft:carved_pumpkin");
        return this;
    }

    @Override
    public Block block() {
        BlockPumpkin pumpkin = (BlockPumpkin) super.block();
        pumpkin.type(this.type());
        return pumpkin;
    }

}
