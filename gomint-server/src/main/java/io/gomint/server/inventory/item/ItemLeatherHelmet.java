package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 298 )
public class ItemLeatherHelmet extends ItemColoredArmor implements io.gomint.inventory.item.ItemLeatherHelmet {

    // CHECKSTYLE:OFF
    public ItemLeatherHelmet( short data, int amount ) {
        super( 298, data, amount );
    }

    public ItemLeatherHelmet( short data, int amount, NBTTagCompound nbt ) {
        super( 298, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getReductionValue() {
        return 1;
    }

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack) entity.getArmorInventory().getHelmet() ) ) {
                ItemStack old = (ItemStack) entity.getArmorInventory().getHelmet();
                entity.getArmorInventory().setHelmet( this );
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), old );
            }
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.LEATHER_HELMET;
    }

}
