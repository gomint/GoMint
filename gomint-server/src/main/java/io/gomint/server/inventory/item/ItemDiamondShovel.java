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
@RegisterInfo( id = 277 )
public class ItemDiamondShovel extends ItemReduceTierDiamond implements io.gomint.inventory.item.ItemDiamondShovel {

    // CHECKSTYLE:OFF
    public ItemDiamondShovel( short data, int amount ) {
        super( 277, data, amount );
    }

    public ItemDiamondShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 277, data, amount, nbt );
    }
    // CHECKSTYLE:ON


    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 4 ); // 1 from shovel type, 3 from diamond material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.DIAMOND_SHOVEL;
    }

}