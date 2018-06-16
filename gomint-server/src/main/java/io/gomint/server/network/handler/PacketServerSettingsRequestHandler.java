/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketServerSettingsRequest;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketServerSettingsRequestHandler implements PacketHandler<PacketServerSettingsRequest> {

    @Override
    public void handle( PacketServerSettingsRequest packet, long currentTimeMillis, PlayerConnection connection ) {
        connection.getEntity().sendServerSettings();
    }

}
