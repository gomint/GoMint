/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla.client;

import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEncryptionRequest;
import io.gomint.server.network.packet.PacketResourcePackStack;
import io.gomint.server.network.packet.PacketStartGame;
import io.gomint.server.network.packet.PacketWorldChunk;

import static io.gomint.server.network.Protocol.PACKET_ENCRYPTION_REQUEST;
import static io.gomint.server.network.Protocol.PACKET_RESOURCEPACK_STACK;
import static io.gomint.server.network.Protocol.PACKET_START_GAME;
import static io.gomint.server.network.Protocol.PACKET_WORLD_CHUNK;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AdditionalProtocol {

    private AdditionalProtocol() {

    }

    /**
     * Creates a new packet instance given the packet ID found inside the first byte of any
     * packet's data.
     *
     * @param id The ID of the the packet to create
     * @return The created packet or null if it could not be created
     */
    public static Packet createPacket( int id ) {
        switch ( id ) {
            case PACKET_WORLD_CHUNK:
                return new PacketWorldChunk();

            case PACKET_START_GAME:
                return new PacketStartGame();

            case PACKET_ENCRYPTION_REQUEST:
                return new PacketEncryptionRequest();

            case PACKET_RESOURCEPACK_STACK:
                return new PacketResourcePackStack();

            default:
                return null;
        }
    }

}
