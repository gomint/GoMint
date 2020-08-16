package io.gomint.server.entity.tileentity;

import io.gomint.math.MojangRotation;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Skull")
public class SkullTileEntity extends TileEntity {

    private MojangRotation rotation;
    private byte skullType;

    /**
     * Construct a new skull tile based on given data
     *
     * @param block which holds this tile
     */
    public SkullTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.rotation = new MojangRotation( compound.getByte( "Rot", (byte) 0 ) );
        this.skullType = compound.getByte( "SkullType", (byte) 0 );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Skull" );
        compound.addValue( "Rot", this.rotation.getRotationValue() );
        compound.addValue( "SkullType", this.skullType );
    }

    public MojangRotation getRotation() {
        return rotation;
    }

    public void setRotation(MojangRotation rotation) {
        this.rotation = rotation;
    }

    public byte getSkullType() {
        return skullType;
    }

    public void setSkullType(byte skullType) {
        this.skullType = skullType;
    }

}
