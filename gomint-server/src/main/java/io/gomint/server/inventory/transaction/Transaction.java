package io.gomint.server.inventory.transaction;


/**
 * @author geNAZt
 * @version 1.0
 */
public interface Transaction<I, S, T> extends io.gomint.inventory.transaction.Transaction<I, S, T> {

    /**
     * Called when the transaction has been a success
     */
    void commit();

    /**
     * Called when a transaction failed
     */
    void revert();

    /**
     * Get inventory window id
     *
     * @return window id
     */
    byte getInventoryWindowId();

}
