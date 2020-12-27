/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

public class InventoryConsumeAction extends InventoryTransferAction {

    public InventoryConsumeAction() {
        super(true, false);
    }

    @Override
    public int weight() {
        return 8;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventoryConsumeAction\", \"_super\": " + super.toString() + "}";
    }

}
