package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBed;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockBed;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 26 )
public class Bed extends Block implements io.gomint.world.block.BlockBed {

    private static final Logger LOGGER = LoggerFactory.getLogger( Bed.class );
    private static final byte OCCUPIED = 0x04;
    private static final byte HEAD = 0x08;

    @Override
    public int getBlockId() {
        return 26;
    }

    @Override
    public long getBreakTime() {
        return 350;
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
        byte rotation = (byte) ( this.getBlockData() & 0x03 );
        io.gomint.world.block.Block otherHalf = null;

        // Switch side when head block
        if ( this.isHeadPart() ) {
            rotation += 2;
            rotation &= 0x03;
        }

        switch ( rotation ) {
            case 0:
                otherHalf = this.getSide( Facing.SOUTH );
                break;
            case 1:
                otherHalf = this.getSide( Facing.WEST );
                break;
            case 2:
                otherHalf = this.getSide( Facing.NORTH );
                break;
            case 3:
                otherHalf = this.getSide( Facing.EAST );
                break;
        }

        return otherHalf;
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
        LOGGER.debug( "Found other block: {}", otherHalf );

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
        return ( this.getBlockData() & Bed.HEAD ) == Bed.HEAD;
    }

    @Override
    public void setHeadPart( boolean value ) {
        if ( !value && isHeadPart() ) {
            this.setBlockData( (byte) ( this.getBlockData() - Bed.HEAD ) );
        } else if ( value && !isHeadPart() ) {
            this.setBlockData( (byte) ( this.getBlockData() + Bed.HEAD ) );
        }
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
            // Check for other block
            Vector2 directionPlane = entity.getDirectionPlane();
            double xAbs = Math.abs( directionPlane.getX() );
            double zAbs = Math.abs( directionPlane.getZ() );

            if ( zAbs > xAbs ) {
                if ( directionPlane.getZ() > 0 ) {
                    Block other = block.getSide( Facing.SOUTH );
                    if ( !other.isSolid() ) {
                        return false;
                    }

                    Block replacingHead = other.getSide( BlockFace.UP );
                    return replacingHead.canBeReplaced( item );
                } else {
                    Block other = block.getSide( Facing.NORTH );
                    if ( !other.isSolid() ) {
                        return false;
                    }

                    Block replacingHead = other.getSide( BlockFace.UP );
                    return replacingHead.canBeReplaced( item );
                }
            } else {
                if ( directionPlane.getX() > 0 ) {
                    Block other = block.getSide( Facing.EAST );
                    if ( !other.isSolid() ) {
                        return false;
                    }

                    Block replacingHead = other.getSide( BlockFace.UP );
                    return replacingHead.canBeReplaced( item );
                } else {
                    Block other = block.getSide( Facing.WEST );
                    if ( !other.isSolid() ) {
                        return false;
                    }

                    Block replacingHead = other.getSide( BlockFace.UP );
                    return replacingHead.canBeReplaced( item );
                }
            }
        }

        return false;
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "color", (byte) item.getData() );

        Vector2 directionPlane = entity.getDirectionPlane();
        double xAbs = Math.abs( directionPlane.getX() );
        double zAbs = Math.abs( directionPlane.getZ() );

        if ( zAbs > xAbs ) {
            if ( directionPlane.getZ() > 0 ) {
                return new PlacementData( 26, (byte) 0, compound );
            } else {
                return new PlacementData( 26, (byte) 2, compound );
            }
        } else {
            if ( directionPlane.getX() > 0 ) {
                return new PlacementData( 26, (byte) 3, compound );
            } else {
                return new PlacementData( 26, (byte) 1, compound );
            }
        }
    }

    @Override
    public void afterPlacement( PlacementData data ) {
        Block otherBlock = (Block) this.getOtherBlock();

        NBTTagCompound compound = new NBTTagCompound( "" );
        this.getTileEntity().toCompound( compound );

        data.setMetaData( (byte) ( this.getBlockData() | Bed.HEAD ) );
        data.setCompound( compound );

        otherBlock.setBlockFromPlacementData( data );
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

        return new ArrayList<ItemStack>() {{
            add( bed );
        }};
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
