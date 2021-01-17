package io.gomint.server.inventory.item;
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
@RegisterInfo( sId = "minecraft:iron_pickaxe" )
public class ItemIronPickaxe extends ItemReduceTierIron<io.gomint.inventory.item.ItemIronPickaxe> implements io.gomint.inventory.item.ItemIronPickaxe {

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 4 ); // 2 from pickaxe type, 2 from iron material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType itemType() {
        return ItemType.IRON_PICKAXE;
    }

}
