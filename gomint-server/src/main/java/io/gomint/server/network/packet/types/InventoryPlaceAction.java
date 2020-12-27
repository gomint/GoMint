/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

public class InventoryPlaceAction extends InventoryTransferAction {

    public InventoryPlaceAction() {
        super(true, true);
    }

    @Override
    public int weight() {
        return 2;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventoryPlaceAction\", \"_super\": " + super.toString() + "}";
    }

}
