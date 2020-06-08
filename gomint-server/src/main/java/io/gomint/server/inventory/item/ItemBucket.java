package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.*;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 325 )
public class ItemBucket extends ItemStack implements io.gomint.inventory.item.ItemBucket {

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
            default:
                return Content.EMPTY;
        }
    }

    /**
     * Returns {@code -1} if the the content type of this bucket
     * is not equal to {@link Content#LAVA}
     */
    @Override
    public long getBurnTime() {
        return this.getContent() == Content.LAVA ? 1000000 : -1;
    }

    @Override
    public String getBlockId() {
        switch ( this.getData() ) {
            case 10:
                return "minecraft:flowing_lava";
            case 8:
                return "minecraft:flowing_water";
            case 1:
                return null;  // Its not possible to empty out milk
            default:
                return super.getBlockId();
        }
    }

    @Override
    public void afterPlacement() {
        // We transform into an empty bucket
        this.setData( (short) 0 );
        this.updateInventories( false );
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock != null ) {
            if ( clickedBlock instanceof BlockLiquid ) {
                if ( ( (BlockLiquid) clickedBlock ).getFillHeight() > 0.9f ) {
                    this.setContent( clickedBlock instanceof BlockFlowingWater || clickedBlock instanceof BlockStationaryWater ?
                        Content.WATER : Content.LAVA );
                    entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
                    clickedBlock.setType( BlockAir.class );
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
