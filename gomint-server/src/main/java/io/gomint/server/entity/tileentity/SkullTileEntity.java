package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.SkullType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Skull")
public class SkullTileEntity extends TileEntity {

    private float rotation;
    private byte skullType;

    /**
     * Construct a new skull tile based on given data
     *
     * @param block which holds this tile
     */
    public SkullTileEntity(Block block, Items items) {
        super(block, items);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        this.rotation = compound.getFloat("Rotation", 0f);
        this.skullType = compound.getByte("SkullType", (byte) 0);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Skull");
        compound.addValue("Rotation", this.rotation);
        compound.addValue("SkullType", this.skullType);
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public SkullType getSkullType() {
        return SkullType.values()[this.skullType];
    }

    public void setSkullType(SkullType skullType) {
        this.skullType = (byte) skullType.ordinal();
    }

}
