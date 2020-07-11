package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.AttributeModifierType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.GrassBlock;
import io.gomint.server.world.block.GrassPath;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 269 )
public class ItemWoodenShovel extends ItemReduceTierWooden implements io.gomint.inventory.item.ItemWoodenShovel {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock instanceof GrassBlock ) {
            clickedBlock.setBlockType( GrassPath.class );
            this.calculateUsageAndUpdate( 1 );
            return true;
        }

        return false;
    }

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, AttributeModifierType.ADDITION, 2 );
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_SHOVEL;
    }

}
