/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.metadata;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class MetadataVector extends MetadataValue {

    private Vector value;

    /**
     * Constructs a new metadata string
     */
    public MetadataVector() {

    }

    /**
     * Constructs a new metadata string and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata string with
     */
    public MetadataVector( Vector value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata string.
     *
     * @return The value of this metadata string
     */
    public Vector getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata string.
     *
     * @param value The value of this metadata string
     */
    public void setValue( Vector value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeLFloat( this.value.getX() );
        buffer.writeLFloat( this.value.getY() );
        buffer.writeLFloat( this.value.getZ() );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_VECTOR;
    }

}
