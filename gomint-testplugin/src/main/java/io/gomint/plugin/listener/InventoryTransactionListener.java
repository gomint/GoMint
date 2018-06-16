/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.inventory.InventoryTransactionEvent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InventoryTransactionListener implements EventListener {

    @EventHandler
    public void onInventoryTransaction( InventoryTransactionEvent e ) {
        e.setCancelled( true );
    }

}
