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
@RegisterInfo( id = 273 )
public class ItemStoneShovel extends ItemReduceTierStone implements io.gomint.inventory.item.ItemStoneShovel {

    // CHECKSTYLE:OFF
    public ItemStoneShovel( short data, int amount ) {
        super( 273, data, amount );
    }

    public ItemStoneShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 273, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    public boolean interact ( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if( entity instanceof EntityPlayer && clickedBlock instanceof GrassBlock ) {
            clickedBlock.setType( GrassPath.class );
            return true;
        }

        return false;
    }

    @Override
    public void gotInHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.ITEM_ATTACK_DAMAGE, 2 ); // 1 from shovel type, 1 from stone material
    }

    @Override
    public void removeFromHand( EntityPlayer player ) {
        player
            .getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.ITEM_ATTACK_DAMAGE );
    }

    @Override
    public ItemType getType() {
        return ItemType.STONE_SHOVEL;
    }

}
