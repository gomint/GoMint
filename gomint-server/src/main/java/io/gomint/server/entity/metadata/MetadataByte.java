/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.metadata;

import io.gomint.jraknet.PacketBuffer;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class MetadataByte extends MetadataValue {

    private byte value;

    /**
     * Constructs a new metadata byte
     */
    public MetadataByte() {

    }

    /**
     * Constructs a new metadata byte and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata byte with
     */
    public MetadataByte( byte value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata byte.
     *
     * @return The value of this metadata byte
     */
    public byte getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata byte.
     *
     * @param value The value of this metadata byte
     */
    public void setValue( byte value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeByte( this.value );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = buffer.readByte();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_BYTE;
    }

    @Override
    public String toString() {
        return "MetadataByte{" +
            "value=" + this.value +
            '}';
    }

}
