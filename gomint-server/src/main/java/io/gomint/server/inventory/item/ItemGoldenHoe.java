package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.block.Dirt;
import io.gomint.server.world.block.Farmland;
import io.gomint.server.world.block.GrassBlock;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 294 )
 public class ItemGoldenHoe extends ItemReduceTierGolden implements io.gomint.inventory.item.ItemGoldenHoe {

    // CHECKSTYLE:OFF
    public ItemGoldenHoe( short data, int amount ) {
        super( 294, data, amount );
    }

    public ItemGoldenHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 294, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    public boolean interact ( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if( clickedBlock instanceof Dirt || clickedBlock instanceof GrassBlock ) {
            clickedBlock.setType( Farmland.class );
            this.damage( 1 );
            return true;
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.GOLDEN_HOE;
    }

}
