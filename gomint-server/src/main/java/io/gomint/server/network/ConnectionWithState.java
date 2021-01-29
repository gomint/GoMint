/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.crypto.Processor;
import io.gomint.server.network.packet.Packet;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ConnectionWithState {

    void send( Packet packet );
    PlayerConnectionState state();
    boolean isPlayer();
    int protocolID();
    Processor outputProcessor();

}
