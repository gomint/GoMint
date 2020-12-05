/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.jraknet.PacketBuffer;

public interface InventoryAction {

    /**
     * Read the implemented action from the given buffer
     *
     * @param buffer
     * @param protocolID
     * @throws Exception
     */
    void deserialize(PacketBuffer buffer, int protocolID) throws Exception;

}
