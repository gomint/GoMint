/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketSetSpawnPosition extends Packet {

    public enum SpawnType {
        PLAYER,
        WORLD
    }

    private SpawnType spawnType;
    private BlockPosition position;
    private boolean force;

    /**
     * Construct a new packet
     */
    public PacketSetSpawnPosition() {
        super( Protocol.PACKET_SET_SPAWN_POSITION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.spawnType.ordinal() );
        writeBlockPosition( this.position, buffer );
        buffer.writeBoolean( this.force );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
