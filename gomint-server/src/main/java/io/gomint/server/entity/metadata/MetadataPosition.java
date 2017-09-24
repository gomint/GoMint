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
public class MetadataPosition extends MetadataValue {

    private int x;
    private int y;
    private int z;

    /**
     * Constructs a new metadata int triple
     */
    MetadataPosition() {

    }

    /**
     * Constructs a new metadata int triple and initializes its values.
     *
     * @param x The x-value to set
     * @param y The y-value to set
     * @param z The z-value to set
     */
    public MetadataPosition( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x-value of this metadata int triple.
     *
     * @return The x-value of this metadata int triple
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the x-value of this metadata int triple.
     *
     * @param x The x-value to set
     */
    public void setX( int x ) {
        this.x = x;
    }

    /**
     * Gets the y-value of this metadata int triple.
     *
     * @return The y-value of this metadata int triple
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the y-value of this metadata int triple.
     *
     * @param y The y-value to set
     */
    public void setY( int y ) {
        this.y = y;
    }

    /**
     * Gets the z-value of this metadata int triple.
     *
     * @return The z-value of this metadata int triple
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Sets the z-value of this metadata int triple.
     *
     * @param z The z-value to set
     */
    public void setZ( int z ) {
        this.z = z;
    }

    /**
     * Sets all values of this metadata int triple.
     *
     * @param x The x-value to set
     * @param y The y-value to set
     * @param z The z-value to set
     */
    public void set( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // ========================== METADATA VALUE ========================== //
    @Override
    void serialize( PacketBuffer buffer, int index ) {
        super.serialize( buffer, index );
        buffer.writeSignedVarInt( this.x );
        buffer.writeSignedVarInt( this.y );
        buffer.writeSignedVarInt( this.z );
    }

    @Override
    void deserialize( PacketBuffer buffer ) {
        this.x = buffer.readSignedVarInt();
        this.y = buffer.readByte() & 0xFF;
        this.z = buffer.readSignedVarInt();
    }

    @Override
    byte getTypeId() {
        return MetadataContainer.METADATA_POSITION;
    }

}
