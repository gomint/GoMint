package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.ItemFrameTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 199 )
public class ItemFrame extends Block implements io.gomint.world.block.BlockItemFrame {

    @Override
    public int getBlockId() {
        return 199;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );

        // Set item
        NBTTagCompound item = new NBTTagCompound( "Item" );
        item.addValue( "id", (short) 0 );
        item.addValue( "Damage", (short) 0 );
        item.addValue( "Count", (byte) 0 );
        compound.addValue( "Item", item );
        compound.addValue( "ItemDropChance", 1.0f );
        compound.addValue( "ItemRotation", (byte) 0 );

        return new ItemFrameTileEntity( compound, this.world );
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ITEM_FRAME;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
