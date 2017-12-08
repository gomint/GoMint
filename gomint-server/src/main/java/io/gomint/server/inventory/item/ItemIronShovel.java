package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 256 )
public class ItemIronShovel extends ItemReduceTierIron implements io.gomint.inventory.item.ItemIronShovel {

    // CHECKSTYLE:OFF
    public ItemIronShovel( short data, int amount ) {
        super( 256, data, amount );
    }

    public ItemIronShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 256, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 3 ); // 1 from shovel type, 2 from iron material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.IRON_SHOVEL;
    }

}