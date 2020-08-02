/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
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

}
