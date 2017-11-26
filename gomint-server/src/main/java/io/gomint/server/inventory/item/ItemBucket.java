package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.*;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 325 )
public class ItemBucket extends ItemStack implements io.gomint.inventory.item.ItemBucket {

    private static final Logger LOGGER = LoggerFactory.getLogger( ItemBucket.class );

    // CHECKSTYLE:OFF
    public ItemBucket( short data, int amount ) {
        super( 325, data, amount );
    }

    public ItemBucket( short data, int amount, NBTTagCompound nbt ) {
        super( 325, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public void setContent( Content type ) {
        switch ( type ) {
            case LAVA:
                this.setData( (short) 10 );
                break;
            case WATER:
                this.setData( (short) 8 );
                break;
            case MILK:
                this.setData( (short) 1 );
                break;
            default:
                this.setData( (short) 0 );
        }
    }

    @Override
    public Content getContent() {
        switch ( this.getData() ) {
            case 10:
                return Content.LAVA;
            case 8:
                return Content.WATER;
            case 1:
                return Content.MILK;
        }

        return Content.EMPTY;
    }

    @Override
    public int getBlockId() {
        switch ( this.getData() ) {
            case 10:
                return 10;
            case 8:
                return 8;
            case 1:
                return -1;  // Its not possible to empty out milk
        }

        return super.getBlockId();
    }

    @Override
    public boolean afterPlacement() {
        // We transform into an empty bucket
        this.setData( (short) 0 );
        return false;
    }

    @Override
    public boolean interact( EntityPlayer entity, int face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock != null ) {
            Block liquidBlock = clickedBlock.getSide( face );
            if ( liquidBlock instanceof BlockLiquid ) {
                if ( ( (BlockLiquid) liquidBlock ).getFillHeight() > 0.9f ) {
                    this.setContent( liquidBlock instanceof BlockFlowingWater || liquidBlock instanceof BlockStationaryWater ?
                        Content.WATER : Content.LAVA );
                    entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
                    liquidBlock.setType( BlockAir.class );
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.BUCKET;
    }

}