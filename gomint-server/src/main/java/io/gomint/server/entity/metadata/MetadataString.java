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
public class MetadataString extends MetadataValue {

    private String value;

    /**
     * Constructs a new metadata string
     */
    public MetadataString() {

    }

    /**
     * Constructs a new metadata string and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata string with
     */
    public MetadataString( String value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata string.
     *
     * @return The value of this metadata string
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata string.
     *
     * @param value The value of this metadata string
     */
    public void setValue( String value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeString( this.value );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = buffer.readString();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_STRING;
    }

    @Override
    public String toString() {
        return "MetadataString{" +
            "value='" + value + '\'' +
            '}';
    }
}
