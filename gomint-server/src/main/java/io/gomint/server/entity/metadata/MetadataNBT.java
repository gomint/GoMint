package io.gomint.server.entity.metadata;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;

import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class MetadataNBT extends MetadataValue {

    private NBTTagCompound value;

    /**
     * Constructs a new metadata item
     */
    public MetadataNBT() {

    }

    /**
     * Constructs a new metadata NBTTagCompound and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata item with
     */
    public MetadataNBT(NBTTagCompound value) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata NBTTagCompound.
     *
     * @return The value of this metadata NBTTagCompound
     */
    public NBTTagCompound getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata NBTTagCompound.
     *
     * @param value The value of this metadata NBTTagCompound
     */
    public void setValue(NBTTagCompound value) {
        this.value = value;
    }

    @Override
    void serialize(PacketBuffer buffer, int index) {
        super.serialize(buffer, index);

        NBTWriter writer = new NBTWriter(buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN);
        writer.setUseVarint(true);

        try {
            writer.write(this.value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void deserialize(PacketBuffer buffer) {
        NBTReader reader = new NBTReader(buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN);
        reader.setUseVarint(true);

        NBTTagCompound compound = null;

        try {
            compound = reader.parse();
        } catch (IOException | AllocationLimitReachedException e) {
            e.printStackTrace();
        }

        this.value = compound;
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_NBT;
    }

}
