package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.inventory.item.ItemSword;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 283 )
public class ItemGoldenSword extends ItemReduceTierSpecial implements io.gomint.inventory.item.ItemGoldenSword, ItemSword {

    // CHECKSTYLE:OFF
    public ItemGoldenSword( short data, int amount ) {
        super( 283, data, amount );
    }

    public ItemGoldenSword( short data, int amount, NBTTagCompound nbt ) {
        super( 283, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 4 ); // 4 from sword type
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.GOLDEN_SWORD;
    }

}