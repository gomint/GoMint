package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.inventory.item.data.DyeType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 351 )
public class ItemDye extends ItemStack implements io.gomint.inventory.item.ItemDye {

    // CHECKSTYLE:OFF
    public ItemDye( short data, int amount ) {
        super( 351, data, amount );
    }

    public ItemDye( short data, int amount, NBTTagCompound nbt ) {
        super( 351, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.DYE;
    }

    @Override
    public void setDyeType( DyeType type ) {
        switch ( type ) {
            case INK_SAC:
                this.setData( (short) 0 );
                break;
            case ROSE_RED:
                this.setData( (short) 1 );
                break;
            case CACTUS_GREEN:
                this.setData( (short) 2 );
                break;
            case COCOA_BEANS:
                this.setData( (short) 3 );
                break;
            case LAPIS_LAZULI:
                this.setData( (short) 4 );
                break;
            case PURPLE:
                this.setData( (short) 5 );
                break;
            case CYAN:
                this.setData( (short) 6 );
                break;
            case LIGHT_GRAY:
                this.setData( (short) 7 );
                break;
            case GRAY:
                this.setData( (short) 8 );
                break;
            case PINK:
                this.setData( (short) 9 );
                break;
            case LIME:
                this.setData( (short) 10 );
                break;
            case DANDELION_YELLOW:
                this.setData( (short) 11 );
                break;
            case LIGHT_BLUE:
                this.setData( (short) 12 );
                break;
            case MAGENTA:
                this.setData( (short) 13 );
                break;
            case ORANGE:
                this.setData( (short) 14 );
                break;
            case BONE_MEAL:
                this.setData( (short) 15 );
                break;
        }
    }

    @Override
    public DyeType getDyeType() {
        short data = this.getData();
        switch ( data ) {
            case 0:
                return DyeType.INK_SAC;
            case 1:
                return DyeType.ROSE_RED;
            case 2:
                return DyeType.CACTUS_GREEN;
            case 3:
                return DyeType.COCOA_BEANS;
            case 4:
                return DyeType.LAPIS_LAZULI;
            case 5:
                return DyeType.PURPLE;
            case 6:
                return DyeType.CYAN;
            case 7:
                return DyeType.LIGHT_GRAY;
            case 8:
                return DyeType.GRAY;
            case 9:
                return DyeType.PINK;
            case 10:
                return DyeType.LIME;
            case 11:
                return DyeType.DANDELION_YELLOW;
            case 12:
                return DyeType.LIGHT_BLUE;
            case 13:
                return DyeType.MAGENTA;
            case 14:
                return DyeType.ORANGE;
            case 15:
                return DyeType.BONE_MEAL;
        }

        return DyeType.INK_SAC;
    }
}
