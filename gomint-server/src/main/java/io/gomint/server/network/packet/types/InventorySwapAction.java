/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

public class InventorySwapAction extends InventoryTransferAction {

    public InventorySwapAction() {
        super(false, true);
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventorySwapAction\", \"_super\": " + super.toString() + "}";
    }
}
