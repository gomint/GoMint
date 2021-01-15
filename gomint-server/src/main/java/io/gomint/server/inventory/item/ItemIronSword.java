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
@RegisterInfo( sId = "minecraft:iron_sword", id = 267 )
public class ItemIronSword extends ItemReduceTierSpecial<io.gomint.inventory.item.ItemIronSword> implements io.gomint.inventory.item.ItemIronSword, ItemSword {

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 6 ); // 4 from sword type, 2 from iron material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType itemType() {
        return ItemType.IRON_SWORD;
    }

    @Override
    public int enchantAbility() {
        return 14;
    }

}
