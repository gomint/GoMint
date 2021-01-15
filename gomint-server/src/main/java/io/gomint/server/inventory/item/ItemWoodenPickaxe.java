package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.AttributeModifierType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:wooden_pickaxe", id = 270 )
public class ItemWoodenPickaxe extends ItemReduceTierWooden<io.gomint.inventory.item.ItemWoodenPickaxe> implements io.gomint.inventory.item.ItemWoodenPickaxe {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(10000);
    }

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 2 ); // 2 from pickaxe type
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .attributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_PICKAXE;
    }

}
