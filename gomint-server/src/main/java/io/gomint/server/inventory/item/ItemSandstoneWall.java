package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPrismarineWall;
import io.gomint.world.block.BlockSandstoneWall;
import io.gomint.world.block.data.Sandcolor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[5]", def = true)
@RegisterInfo(sId = "minecraft:cobblestone_wall[12]", def = true)
public class ItemSandstoneWall extends ItemStack<io.gomint.inventory.item.ItemSandstoneWall> implements io.gomint.inventory.item.ItemSandstoneWall {

    @Override
    public ItemType itemType() {
        return ItemType.SANDSTONE_WALL;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockSandstoneWall.class)
            .color(this.color());
    }

    @Override
    public io.gomint.inventory.item.ItemSandstoneWall color(Sandcolor color) {
        return color == Sandcolor.NORMAL ? this.data((short) 5) : this.data((short) 12);
    }

    @Override
    public Sandcolor color() {
        return this.data() == 5 ? Sandcolor.NORMAL : Sandcolor.RED;
    }

}
