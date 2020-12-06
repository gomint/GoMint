/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

/**
 * @author geNAZt
 */
public class InventoryDestroyCreativeAction extends InventoryTransferAction {

    public InventoryDestroyCreativeAction() {
        super(true, false);
    }

}
