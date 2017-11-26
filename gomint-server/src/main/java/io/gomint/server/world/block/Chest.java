package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockChest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 54 )
public class Chest extends Block implements BlockChest {

    private static final Logger LOGGER = LoggerFactory.getLogger( Chest.class );

    @Override
    public int getBlockId() {
        return 54;
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        ChestTileEntity tileEntity = this.getTileEntity();
        if ( tileEntity != null ) {
            tileEntity.interact( entity, face, facePos, item );
            return true;
        } else {
            LOGGER.warn( "Chest @ " + getLocation() + " has no tile entity" );
        }

        return false;
    }

    @Override
    public Inventory getInventory() {
        ChestTileEntity tileEntity = this.getTileEntity();
        return tileEntity.getInventory();
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        if ( compound == null ) {
            compound = new NBTTagCompound( "" );
        }

        BlockPosition position = this.location.toBlockPosition();

        // Add generic tile entity stuff
        compound.addValue( "x", position.getX() );
        compound.addValue( "y", position.getY() );
        compound.addValue( "z", position.getZ() );

        return new ChestTileEntity( compound, this.world );
    }

    @Override
    public float getBlastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CHEST;
    }

}