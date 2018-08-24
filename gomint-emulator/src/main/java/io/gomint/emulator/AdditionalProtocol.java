/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;

import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static io.gomint.server.network.Protocol.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AdditionalProtocol {

    /**
     * Creates a new packet instance given the packet ID found inside the first byte of any
     * packet's data.
     *
     * @param id The ID of the the packet to create
     * @return The created packet or null if it could not be created
     */
    public static Packet createPacket( byte id ) {
        switch ( id ) {
            case PACKET_WORLD_CHUNK:
                return new PacketWorldChunk();

            case PACKET_START_GAME:
                return new PacketStartGame();

            case PACKET_WORLD_TIME:
                return new PacketWorldTime();

            case PACKET_ENTITY_METADATA:
                return new PacketEntityMetadata();

            case PACKET_TILE_ENTITY_DATA:
                return new PacketTileEntityData();

            case PACKET_SPAWN_PLAYER:
                return new PacketSpawnPlayer();

            case PACKET_SPAWN_ENTITY:
                return new PacketSpawnEntity();

            case PACKET_AVAILABLE_COMMANDS:
                return new PacketAvailableCommands();

            default:
                // LOGGER.warn( "Unknown client side packetId: {}", Integer.toHexString( id & 0xFF ) );
                return null;
        }
    }

}
