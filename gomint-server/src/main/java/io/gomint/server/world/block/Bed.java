package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.inventory.item.ItemBed;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.RotatedFacingBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockBed;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;
import io.gomint.world.block.data.Facing;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 26 )
@EqualsAndHashCode( callSuper = true )
public class Bed extends Block implements io.gomint.world.block.BlockBed {

    private BooleanBlockState occupied = new BooleanBlockState();
    private BooleanBlockState head = new BooleanBlockState();
    private RotatedFacingBlockState facing = new RotatedFacingBlockState();

    @Override
    public int getBlockId() {
        return 26;
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean onBreak( boolean creative ) {
        Bed otherHalf = (Bed) this.getOtherHalf();
        if ( otherHalf != null ) {
            otherHalf.setType( Air.class );
        }

        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BED;
    }

    private io.gomint.world.block.Block getOtherBlock() {
        // Select which side we need to check
        Facing facingToOtherHalf = this.facing.getState();
        if ( this.isHeadPart() ) {
            switch ( facingToOtherHalf ) {
                case NORTH:
                    facingToOtherHalf = Facing.SOUTH;
                    break;
                case SOUTH:
                    facingToOtherHalf = Facing.NORTH;
                    break;
                case EAST:
                    facingToOtherHalf = Facing.WEST;
                    break;
                case WEST:
                    facingToOtherHalf = Facing.EAST;
                    break;
                default:
                    return null;
            }
        }

        return this.getSide( facingToOtherHalf );
    }

    @Override
    public BlockColor getColor() {
        BedTileEntity tileEntity = this.getTileEntity();
        return tileEntity.getColor();
    }

    @Override
    public void setColor( BlockColor color ) {
        BedTileEntity tileEntity = this.getTileEntity();
        tileEntity.setColor( color );

        this.updateBlock();
    }

    @Override
    public BlockBed getOtherHalf() {
        io.gomint.world.block.Block otherHalf = getOtherBlock();

        // Check if other part is a bed
        if ( otherHalf != null && otherHalf.getType() == BlockType.BED ) {
            Bed otherBedHalf = (Bed) otherHalf;
            if ( otherBedHalf.isHeadPart() != this.isHeadPart() ) {
                return otherBedHalf;
            }
        }

        return null;
    }

    @Override
    public boolean isHeadPart() {
        return this.head.getState();
    }

    @Override
    public void setHeadPart( boolean value ) {
        this.head.setState( value );
        this.updateBlock();
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    public boolean beforePlacement( Entity entity, ItemStack item, Location location ) {
        // We need to check if we are placed on a solid block
        Block block = (Block) location.getWorld().getBlockAt( location.toBlockPosition() ).getSide( BlockFace.DOWN );
        if ( block.isSolid() ) {
            // Calc facing
            this.facing.detectFromPlayer( (EntityPlayer) entity );

            // Check for other block
            Block other = block.getSide( this.facing.getState() );
            if ( !other.isSolid() ) {
                return false;
            }

            Block replacingHead = other.getSide( BlockFace.UP );
            return replacingHead.canBeReplaced( item );
        }

        return false;
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "color", (byte) item.getData() );

        // Calc facing
        this.facing.detectFromPlayer( (EntityPlayer) entity );

        this.calculateBlockData();
        return new PlacementData( 26, this.getBlockData(), compound );
    }

    @Override
    public void afterPlacement( PlacementData data ) {
        Block otherBlock = (Block) this.getOtherBlock();
        if ( otherBlock != null ) {
            NBTTagCompound compound = new NBTTagCompound( "" );
            this.getTileEntity().toCompound( compound , SerializationReason.PERSIST );

            data.setMetaData( (byte) ( this.getBlockData() | 0x08 ) );
            data.setCompound( compound );

            otherBlock.setBlockFromPlacementData( data );
        }
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return new BedTileEntity( compound, this.world );
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        ItemBed bed = ItemBed.create( 1 );
        bed.setColor( ( (BedTileEntity) this.getTileEntity() ).getColor() );
        return Lists.newArrayList( bed );
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.5625f,
            this.location.getZ() + 1
        ) );
    }

    @Override
    public void generateBlockStates() {
        this.facing.fromData( (byte) ( this.getBlockData() & 0x03 ) );
        this.occupied.fromData( (byte) ( this.getBlockData() >> 2 & 0x01 ) );
        this.head.fromData( (byte) ( this.getBlockData() >> 3 & 0x01 ) );
    }

    @Override
    public void calculateBlockData() {
        this.resetBlockData();
        this.addToBlockData( this.facing.toData() ); // 0 - 3 (1+2)
        this.addToBlockData( (byte) ( this.occupied.toData() << 2 ) ); // 4 (3)
        this.addToBlockData( (byte) ( this.head.toData() << 3 ) ); // 8 (4)
    }

}
