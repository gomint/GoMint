/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

public class PacketViolationWarning extends Packet {

    private int type;
    private int severity;
    private int packetId;
    private String message;

    public PacketViolationWarning() {
        super(Protocol.PACKET_VIOLATION_WARNING);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.type = buffer.readSignedVarInt();
        this.severity = buffer.readSignedVarInt();
        this.packetId = buffer.readSignedVarInt();
        this.message = buffer.readString();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
