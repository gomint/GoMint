package io.gomint.server.inventory.transaction;


/**
 * @author geNAZt
 * @version 1.0
 */
public interface Transaction extends io.gomint.inventory.transaction.Transaction {

    /**
     * Called when the transaction has been a success
     */
    void commit();

    /**
     * Called when a transaction failed
     */
    void revert();

}
