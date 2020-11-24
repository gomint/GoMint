/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketEmoteList extends Packet {

    private long runtimeId;
    private List<UUID> emoteIds;

    public PacketEmoteList() {
        super(Protocol.PACKET_EMOTE_LIST);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.runtimeId = buffer.readUnsignedVarLong();
        int count = buffer.readUnsignedVarInt();
        this.emoteIds = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            this.emoteIds.add(buffer.readUUID());
        }
    }

    public long getRuntimeId() {
        return runtimeId;
    }

    public void setRuntimeId(long runtimeId) {
        this.runtimeId = runtimeId;
    }

    public List<UUID> getEmoteIds() {
        return emoteIds;
    }

    public void setEmoteIds(List<UUID> emoteIds) {
        this.emoteIds = emoteIds;
    }
}
