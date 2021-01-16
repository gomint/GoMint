package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBed;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bed", id = 355, def = true)
@RegisterInfo(sId = "minecraft:item.bed", id = 26)
public class ItemBed extends ItemStack< io.gomint.inventory.item.ItemBed> implements io.gomint.inventory.item.ItemBed {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public ItemType itemType() {
        return ItemType.BED;
    }

    @Override
    public BlockColor color() {
        switch (this.data()) {
            case 0:
                return BlockColor.WHITE;
            case 1:
                return BlockColor.ORANGE;
            case 2:
                return BlockColor.MAGENTA;
            case 3:
                return BlockColor.LIGHT_BLUE;
            case 4:
                return BlockColor.YELLOW;
            case 5:
                return BlockColor.LIME;
            case 6:
                return BlockColor.PINK;
            case 7:
                return BlockColor.GRAY;
            case 8:
                return BlockColor.LIGHT_GRAY;
            case 9:
                return BlockColor.CYAN;
            case 10:
                return BlockColor.PURPLE;
            case 11:
                return BlockColor.BLUE;
            case 12:
                return BlockColor.BROWN;
            case 13:
                return BlockColor.GREEN;
            case 14:
                return BlockColor.RED;
            default:
                return BlockColor.BLACK;
        }
    }

    @Override
    public ItemBed color(BlockColor color) {
        switch (color) {
            case WHITE:
                this.data((short) 0);
                break;
            case ORANGE:
                this.data((short) 1);
                break;
            case MAGENTA:
                this.data((short) 2);
                break;
            case LIGHT_BLUE:
                this.data((short) 3);
                break;
            case YELLOW:
                this.data((short) 4);
                break;
            case LIME:
                this.data((short) 5);
                break;
            case PINK:
                this.data((short) 6);
                break;
            case GRAY:
                this.data((short) 7);
                break;
            case LIGHT_GRAY:
                this.data((short) 8);
                break;
            case CYAN:
                this.data((short) 9);
                break;
            case PURPLE:
                this.data((short) 10);
                break;
            case BLUE:
                this.data((short) 11);
                break;
            case BROWN:
                this.data((short) 12);
                break;
            case GREEN:
                this.data((short) 13);
                break;
            case RED:
                this.data((short) 14);
                break;
            case BLACK:
            default:
                this.data((short) 15);
        }

        return this;
    }

    @Override
    public Block block() {
        BlockBed block = (BlockBed) super.block();
        block.color(this.color());
        block.head(false);
        return block;
    }

}
