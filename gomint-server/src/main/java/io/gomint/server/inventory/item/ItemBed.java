package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 355 )
public class ItemBed extends ItemStack implements io.gomint.inventory.item.ItemBed {

    // CHECKSTYLE:OFF
    public ItemBed( short data, int amount ) {
        super( 355, data, amount );
    }

    public ItemBed( short data, int amount, NBTTagCompound nbt ) {
        super( 355, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public int getBlockId() {
        return 26;
    }

    @Override
    public ItemType getType() {
        return ItemType.BED;
    }

    @Override
    public BlockColor getColor() {
        switch ( this.getData() ) {
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
    public void setColor( BlockColor color ) {
        switch ( color ) {
            case WHITE:
                this.setData( (short) 0 );
                break;
            case ORANGE:
                this.setData( (short) 1 );
                break;
            case MAGENTA:
                this.setData( (short) 2 );
                break;
            case LIGHT_BLUE:
                this.setData( (short) 3 );
                break;
            case YELLOW:
                this.setData( (short) 4 );
                break;
            case LIME:
                this.setData( (short) 5 );
                break;
            case PINK:
                this.setData( (short) 6 );
                break;
            case GRAY:
                this.setData( (short) 7 );
                break;
            case LIGHT_GRAY:
                this.setData( (short) 8 );
                break;
            case CYAN:
                this.setData( (short) 9 );
                break;
            case PURPLE:
                this.setData( (short) 10 );
                break;
            case BLUE:
                this.setData( (short) 11 );
                break;
            case BROWN:
                this.setData( (short) 12 );
                break;
            case GREEN:
                this.setData( (short) 13 );
                break;
            case RED:
                this.setData( (short) 14 );
                break;
            case BLACK:
            default:
                this.setData( (short) 15 );
        }
    }

}
