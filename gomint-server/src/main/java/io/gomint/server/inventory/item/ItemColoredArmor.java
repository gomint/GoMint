package io.gomint.server.inventory.item;

import io.gomint.taglib.NBTTagCompound;

import java.awt.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemColoredArmor extends ItemArmor implements io.gomint.inventory.item.ItemColoredArmor {

    private static final String NBT_CUSTOM_COLOR_TAG = "customColor";

    public ItemColoredArmor( int material, short data, int amount ) {
        super( material, data, amount );
    }

    public ItemColoredArmor( int material, short data, int amount, NBTTagCompound nbt ) {
        super( material, data, amount, nbt );
    }

    @Override
    public Color getColor() {
        // Do we have a NBT tag?
        if ( this.getNbtData() == null ) {
            return null;
        }

        // Do we have color data?
        int rgb = this.getNbtData().getInteger( NBT_CUSTOM_COLOR_TAG, -1 );
        if ( rgb == -1 ) {
            return null;
        }


        return new Color( rgb );
    }

    @Override
    public void setColor( Color color ) {
        this.enforceNBTData();
        int rgb = color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
        this.getNbtData().addValue( NBT_CUSTOM_COLOR_TAG, rgb );
    }

}
