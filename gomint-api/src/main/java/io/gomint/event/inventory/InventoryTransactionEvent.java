/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.inventory;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.inventory.transaction.Transaction;

import java.util.List;
import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class InventoryTransactionEvent extends CancellablePlayerEvent {

    private final List<Transaction> transactions;

    /**
     * Create a transaction event to control inventory movements
     *
     * @param player       which has executed this transaction
     * @param transactions which should be executed
     */
    public InventoryTransactionEvent( EntityPlayer player, List<Transaction> transactions ) {
        super( player );
        this.transactions = transactions;
    }

    /**
     * Get the transactions which should be executed
     *
     * @return transactions which may be applied
     */
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InventoryTransactionEvent that = (InventoryTransactionEvent) o;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactions);
    }

    @Override
    public String toString() {
        return "InventoryTransactionEvent{" +
            "transactions=" + transactions +
            '}';
    }

}
