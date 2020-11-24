/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketEmoteList;

public class PacketEmoteListHandler implements PacketHandler<PacketEmoteList> {

    @Override
    public void handle(PacketEmoteList packet, long currentTimeMillis, PlayerConnection connection) throws Exception {
        // Don't use this
    }

}
