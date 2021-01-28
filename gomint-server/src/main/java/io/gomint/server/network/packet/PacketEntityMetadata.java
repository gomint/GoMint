/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.Protocol;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketEntityMetadata extends Packet {

    private long entityId;
    private MetadataContainer metadata;
    private long tick;

    public PacketEntityMetadata() {
        super( Protocol.PACKET_ENTITY_METADATA );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        this.metadata.serialize( buffer );
        buffer.writeUnsignedVarLong(this.tick);
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.metadata = new MetadataContainer();
        this.metadata.deserialize( buffer );
        this.tick = buffer.readUnsignedVarLong();
    }

    public long getTick() {
        return this.tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public MetadataContainer getMetadata() {
        return this.metadata;
    }

    public void setMetadata(MetadataContainer metadata) {
        this.metadata = metadata;
    }
}
