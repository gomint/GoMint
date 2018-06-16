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
@RegisterInfo( id = 276 )
public class ItemDiamondSword extends ItemReduceTierSpecial implements io.gomint.inventory.item.ItemDiamondSword, ItemSword {

    // CHECKSTYLE:OFF
    public ItemDiamondSword( short data, int amount ) {
        super( 276, data, amount );
    }

    public ItemDiamondSword( short data, int amount, NBTTagCompound nbt ) {
        super( 276, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 7 ); // 4 from sword type, 3 from diamond material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.DIAMOND_SWORD;
    }

    @Override
    public int getEnchantAbility() {
        return 10;
    }

}
