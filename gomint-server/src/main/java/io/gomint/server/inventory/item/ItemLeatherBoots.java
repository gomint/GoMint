package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 301 )
public class ItemLeatherBoots extends ItemArmor implements io.gomint.inventory.item.ItemLeatherBoots {

    // CHECKSTYLE:OFF
    public ItemLeatherBoots( short data, int amount ) {
        super( 301, data, amount );
    }

    public ItemLeatherBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 301, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getReductionValue() {
        return 1;
    }

    @Override
    public boolean interact( EntityPlayer entity, int face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack) entity.getArmorInventory().getBoots() ) ) {
                entity.getArmorInventory().setBoots( this );
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
            }
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.LEATHER_BOOTS;
    }

}