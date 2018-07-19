package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.GrassBlock;
import io.gomint.server.world.block.GrassPath;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 284 )
public class ItemGoldenShovel extends ItemReduceTierGolden implements io.gomint.inventory.item.ItemGoldenShovel {

    // CHECKSTYLE:OFF
    public ItemGoldenShovel( short data, int amount ) {
        super( 284, data, amount );
    }

    public ItemGoldenShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 284, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock instanceof GrassBlock ) {
            clickedBlock.setType( GrassPath.class );
            this.calculateUsageAndUpdate( 1 );
            return true;
        }

        return false;
    }

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 1 ); // 1 from shovel type
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.GOLDEN_SHOVEL;
    }

}
