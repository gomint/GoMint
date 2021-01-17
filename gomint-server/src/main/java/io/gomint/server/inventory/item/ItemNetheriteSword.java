package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemSword;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.AttributeModifierType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:netherite_sword" )
public class ItemNetheriteSword extends ItemReduceTierNetherite<io.gomint.inventory.item.ItemNetheriteSword> implements io.gomint.inventory.item.ItemNetheriteSword, ItemSword {

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 8 );
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType itemType() {
        return ItemType.NETHERITE_SWORD;
    }

    @Override
    public int enchantAbility() {
        return 15;
    }

}
