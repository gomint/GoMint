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
public class MetadataInt extends MetadataValue {

    private int value;

    /**
     * Constructs a new metadata int
     */
    MetadataInt() {

    }

    /**
     * Constructs a new metadata int and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata int with
     */
    public MetadataInt( int value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata int.
     *
     * @return The value of this metadata int
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata int.
     *
     * @param value The value of this metadata int
     */
    public void setValue( int value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeSignedVarInt( this.value );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = buffer.readSignedVarInt();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_INT;
    }

    @Override
    public String toString() {
        return "MetadataInt{" +
            "value=" + value +
            '}';
    }

}
