/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
public class MetadataFloat extends MetadataValue {

    private float value;

    /**
     * Constructs a new metadata float
     */
    public MetadataFloat() {

    }

    /**
     * Constructs a new metadata float and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata float with
     */
    public MetadataFloat( float value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata float.
     *
     * @return The value of this metadata float
     */
    public float getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata float.
     *
     * @param value The value of this metadata float
     */
    public void setValue( float value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeLFloat( this.value );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = buffer.readLFloat();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_FLOAT;
    }

    @Override
    public String toString() {
        return "MetadataFloat{" +
            "value=" + value +
            '}';
    }

}
