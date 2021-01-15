/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;


import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemStack;

/**
 * @author geNAZt
 */
public interface Session {

    /**
     * Get the output inventory
     *
     * @return inventory which contains output
     */
    Inventory<?> getOutput();

    /**
     *
     * @return
     */
    boolean process();

    /**
     * Called when a transaction wants the session to consume a item
     *
     * @param item which should be consumed
     * @param slot filled when the consume needs to be in a specific slot
     */
    void addInput(ItemStack<?> item, int slot);

    /**
     * Get called when all transactions have been done and some cleanup needs to be done
     */
    void postProcess();

}
