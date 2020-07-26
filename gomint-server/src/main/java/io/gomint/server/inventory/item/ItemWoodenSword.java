package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemSword;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.AttributeModifierType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 268 )
public class ItemWoodenSword extends ItemReduceTierSpecial implements io.gomint.inventory.item.ItemWoodenSword, ItemSword {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 4 ); // 4 from sword type
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WOODEN_SWORD;
    }

    @Override
    public int getEnchantAbility() {
        return 15;
    }

}
