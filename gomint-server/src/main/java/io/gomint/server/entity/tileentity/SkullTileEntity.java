package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.math.MojangRotation;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class SkullTileEntity extends TileEntity {

    private MojangRotation rotation;
    private byte skullType;

    /**
     * Construct a new skull tile based on given data
     *
     * @param rotation  of the skull
     * @param skullType type of the skull
     * @param location  of the skull in the given world
     */
    public SkullTileEntity( byte rotation, byte skullType, Location location ) {
        super( location );

        this.rotation = new MojangRotation( rotation );
        this.skullType = skullType;
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public SkullTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        this.rotation = new MojangRotation( tagCompound.getByte( "Rot", (byte) 0 ) );
        this.skullType = tagCompound.getByte( "SkullType", (byte) 0 );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Skull" );
        compound.addValue( "Rot", this.rotation.getRotationValue() );
        compound.addValue( "SkullType", this.skullType );
    }

}
