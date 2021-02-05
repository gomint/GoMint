/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketSetSpawnPosition extends Packet {

    public enum SpawnType {
        PLAYER,
        WORLD
    }

    private SpawnType spawnType;
    private BlockPosition playerPosition;
    private int dimension;
    private BlockPosition worldSpawn;

    /**
     * Construct a new packet
     */
    public PacketSetSpawnPosition() {
        super(Protocol.PACKET_SET_SPAWN_POSITION);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeSignedVarInt(this.spawnType.ordinal());
        writeBlockPosition(this.playerPosition, buffer);
        buffer.writeSignedVarInt(this.dimension);
        writeBlockPosition(this.worldSpawn, buffer);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }

    public SpawnType getSpawnType() {
        return this.spawnType;
    }

    public void setSpawnType(SpawnType spawnType) {
        this.spawnType = spawnType;
    }

    public BlockPosition getPlayerPosition() {
        return this.playerPosition;
    }

    public void setPlayerPosition(BlockPosition playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public BlockPosition getWorldSpawn() {
        return this.worldSpawn;
    }

    public void setWorldSpawn(BlockPosition worldSpawn) {
        this.worldSpawn = worldSpawn;
    }
}
