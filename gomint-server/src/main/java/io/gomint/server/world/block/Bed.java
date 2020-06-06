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
import io.gomint.server.util.Bearing;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.FacingBlockState;
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
@RegisterInfo( sId = "minecraft:bed" )
@EqualsAndHashCode( callSuper = true )
public class Bed extends Block implements io.gomint.world.block.BlockBed {

    private FacingBlockState facing = new FacingBlockState( this );
    private BooleanBlockState occupied = new BooleanBlockState( this, states -> true, 2 );
    private BooleanBlockState head = new BooleanBlockState( this, states -> true, 3 );

    @Override
    public String getBlockId() {
        return "minecraft:bed";
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
            facingToOtherHalf = facingToOtherHalf.opposite();
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
            Bearing bearing = Bearing.fromAngle( entity.getYaw() );

            // Check for other block
            Block other = block.getSide( bearing.toFacing() );
            if ( !other.isSolid() ) {
                return false;
            }

            Block replacingHead = other.getSide( BlockFace.UP );
            return replacingHead.canBeReplaced( item );
        }

        return false;
    }

    @Override
    public PlacementData calculatePlacementData( EntityPlayer entity, ItemStack item, BlockFace face, Block block, Block clickedBlock, Vector clickVector ) {
        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "color", (byte) item.getData() );

        // Calc block states
        PlacementData data = super.calculatePlacementData( entity, item, face, block, clickedBlock, clickVector );
        return data.setCompound( compound );
    }

    @Override
    public void afterPlacement( PlacementData data ) {
        Block otherBlock = (Block) this.getOtherBlock();
        if ( otherBlock != null ) {
            NBTTagCompound compound = new NBTTagCompound( "" );
            this.getTileEntity().toCompound( compound, SerializationReason.PERSIST );

            // TODO: Calculate proper state map
            BlockIdentifier identifier = new BlockIdentifier( data.getBlockIdentifier().getBlockId(), null, (short) 0 );
            data.setBlockIdentifier( identifier );
            data.setCompound( compound );

            Bed bed = otherBlock.setBlockFromPlacementData( data );
            bed.head.setState( true );
        }
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return new BedTileEntity( this );
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

}
