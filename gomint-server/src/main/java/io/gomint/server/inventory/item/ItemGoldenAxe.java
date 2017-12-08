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
@RegisterInfo( id = 286 )
 public class ItemGoldenAxe extends ItemReduceTierGolden implements io.gomint.inventory.item.ItemGoldenAxe {

    // CHECKSTYLE:OFF
    public ItemGoldenAxe( short data, int amount ) {
        super( 286, data, amount );
    }

    public ItemGoldenAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 286, data, amount, nbt );
    }
    // CHECKSTYLE:ON


    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 3 ); // 3 from axe type
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.GOLDEN_AXE;
    }

}