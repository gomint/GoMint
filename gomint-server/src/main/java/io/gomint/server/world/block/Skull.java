package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.MojangRotation;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.SkullTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 144 )
public class Skull extends Block implements io.gomint.world.block.BlockSkull {

    @Override
    public int getBlockId() {
        return 144;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
            this.location.getX() + 0.25f,
            this.location.getY(),
            this.location.getZ() + 0.25f,
            this.location.getX() + 0.75f,
            this.location.getY() + 0.5f,
            this.location.getZ() + 0.75f
        );
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Rot", MojangRotation.fromEntityForBlock( entity ).getRotationValue() );
        return new PlacementData( (byte) item.getData(), compound );
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        if ( compound == null ) {
            compound = new NBTTagCompound( "" );
        }

        compound.addValue( "SkullType", this.getBlockData() );
        return new SkullTileEntity( compound, this.world );
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>(){{
            add( Items.create( 397, getBlockData(), (byte) 1, null ) );
        }};
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SKULL;
    }

}