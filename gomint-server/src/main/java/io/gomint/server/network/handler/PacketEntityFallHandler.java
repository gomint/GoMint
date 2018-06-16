/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketEntityFall;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketEntityFallHandler implements PacketHandler<PacketEntityFall> {

    @Override
    public void handle( PacketEntityFall packet, long currentTimeMillis, PlayerConnection connection ) {

    }

}
