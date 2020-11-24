/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketViolationWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketViolationWarningHandler implements PacketHandler<PacketViolationWarning> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketViolationWarningHandler.class);

    @Override
    public void handle(PacketViolationWarning packet, long currentTimeMillis, PlayerConnection connection) throws Exception {
        LOGGER.warn("Got packet violation warning, type: {} - 0x{} => {}", packet.getSeverity(), Integer.toHexString(packet.getPacketId()), packet.getMessage());
    }

}
