/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.metadata;

import io.gomint.jraknet.PacketBuffer;
import lombok.ToString;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@ToString
public class MetadataShort extends MetadataValue {

    private short value;

    /**
     * Constructs a new metadata short
     */
    public MetadataShort() {

    }

    /**
     * Constructs a new metadata short and initializes it with the specified value.
     *
     * @param value The value to initialize the metadata short with
     */
    public MetadataShort( short value ) {
        this.value = value;
    }

    /**
     * Gets the value of this metadata short.
     *
     * @return The value of this metadata short
     */
    public short getValue() {
        return this.value;
    }

    /**
     * Sets the value of this metadata short.
     *
     * @param value The value of this metadata short
     */
    public void setValue( short value ) {
        this.value = value;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeLShort( this.value );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.value = buffer.readLShort();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_SHORT;
    }

}
