package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.BlockFlowingLava;
import io.gomint.world.block.BlockFlowingWater;
import io.gomint.world.block.BlockLiquid;
import io.gomint.world.block.BlockStationaryWater;
import io.gomint.world.block.data.Facing;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bucket", id = 325 )
public class ItemBucket extends ItemStack<io.gomint.inventory.item.ItemBucket> implements io.gomint.inventory.item.ItemBucket {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public io.gomint.inventory.item.ItemBucket content(Content type ) {
        switch ( type ) {
            case LAVA:
                this.data( (short) 10 );
                break;
            case WATER:
                this.data( (short) 8 );
                break;
            case MILK:
                this.data( (short) 1 );
                break;
            default:
                this.data( (short) 0 );
        }

        return this;
    }

    @Override
    public Content content() {
        switch ( this.data() ) {
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
     * Returns null if the the content type of this bucket
     * is not equal to {@link Content#LAVA}
     */
    @Override
    public Duration burnTime() {
        return this.content() == Content.LAVA ? Duration.ofMillis(1000000) : null;
    }

    @Override
    public Block block() {
        switch ( this.data() ) {
            case 10:
                return this.blocks.get(BlockFlowingLava.class);
            case 8:
                return this.blocks.get(BlockFlowingWater.class);
            default:
            case 1:
                return null;  // Its not possible to empty out milk
        }
    }

    @Override
    public io.gomint.inventory.item.ItemBucket afterPlacement() {
        // We transform into an empty bucket
        this.data( (short) 0 );
        return this.updateInventories( false );
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock != null ) {
            if ( clickedBlock instanceof BlockLiquid) {
                if ( ( (BlockLiquid<?>) clickedBlock ).fillHeight() > 0.9f ) {
                    this.content( clickedBlock instanceof BlockFlowingWater || clickedBlock instanceof BlockStationaryWater ?
                        Content.WATER : Content.LAVA );
                    entity.inventory().item( entity.inventory().itemInHandSlot(), this );
                    clickedBlock.blockType( BlockAir.class );
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.BUCKET;
    }

}
