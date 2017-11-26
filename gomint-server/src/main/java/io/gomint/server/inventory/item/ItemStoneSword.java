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
@RegisterInfo( id = 272 )
public class ItemStoneSword extends ItemReduceTierSpecial implements io.gomint.inventory.item.ItemStoneSword, ItemSword {

    // CHECKSTYLE:OFF
    public ItemStoneSword( short data, int amount ) {
        super( 272, data, amount );
    }

    public ItemStoneSword( short data, int amount, NBTTagCompound nbt ) {
        super( 272, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 5 ); // 4 from sword type, 1 from stone material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.STONE_SWORD;
    }

}