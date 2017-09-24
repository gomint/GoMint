package io.gomint.server.inventory.transaction;

import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.Inventory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TransactionGroup {

    private final List<Transaction> transactions = new ArrayList<>();

    // Need / have for this transactions
    @Getter private List<ItemStack> haveItems = new ArrayList<>();
    private List<ItemStack> needItems = new ArrayList<>();

    // Matched
    private boolean matchItems;

    /**
     * Add a new transaction to this group
     *
     * @param transaction The transaction which should be added
     */
    public void addTransaction( Transaction transaction ) {
        // Check if not already added
        if ( this.transactions.contains( transaction ) ) {
            return;
        }

        // Add this transaction and also the inventory
        this.transactions.add( transaction );
    }

    private void calcMatchItems() {
        // Clear both sides for a fresh compare
        this.haveItems.clear();
        this.needItems.clear();

        // Check all transactions for needed and having items
        for ( Transaction ts : this.transactions ) {
            if ( !( ts.getTargetItem() instanceof ItemAir ) ) {
                this.needItems.add( ( (io.gomint.server.inventory.item.ItemStack) ts.getTargetItem() ).clone() );
            }

            ItemStack sourceItem = ts.getSourceItem() != null ? ( (io.gomint.server.inventory.item.ItemStack) ts.getSourceItem() ).clone() : null;
            if ( ts.hasInventory() && sourceItem != null ) {
                ItemStack checkSourceItem = ts.getInventory().getItem( ts.getSlot() );

                // Check if source inventory changed during transaction
                if ( !checkSourceItem.equals( sourceItem ) || sourceItem.getAmount() != checkSourceItem.getAmount() ) {
                    // Check if there was a transaction before which changed this slot
                    boolean foundOtherTransaction = false;
                    for ( Transaction transaction : this.transactions ) {
                        if ( transaction.hasInventory()
                                && transaction.getTargetItem().equals( checkSourceItem )
                                && transaction.getSlot() == ts.getSlot() ) {
                            // This is ok, another transaction in this group changed the item
                            foundOtherTransaction = true;
                            break;
                        }
                    }

                    if ( !foundOtherTransaction ) {
                        this.matchItems = false;
                        return;
                    }
                }
            }

            if ( sourceItem != null && !( sourceItem instanceof ItemAir ) ) {
                this.haveItems.add( sourceItem );
            }
        }

        // Now check if we have items left which are needed
        for ( ItemStack needItem : new ArrayList<>( this.needItems ) ) {
            for ( ItemStack haveItem : new ArrayList<>( this.haveItems ) ) {
                if ( needItem.equals( haveItem ) ) {
                    int amount = Math.min( haveItem.getAmount(), needItem.getAmount() );
                    needItem.setAmount( needItem.getAmount() - amount );
                    haveItem.setAmount( haveItem.getAmount() - amount );

                    if ( haveItem.getAmount() == 0 ) {
                        this.haveItems.remove( haveItem );
                    }

                    if ( needItem.getAmount() == 0 ) {
                        this.needItems.remove( needItem );
                        break;
                    }
                }
            }
        }

        this.matchItems = true;
    }

    /**
     * Check if transaction is complete and can be executed
     *
     * @return true if the transaction is complete and can be executed
     */
    private boolean canExecute() {
        this.calcMatchItems();
        return this.matchItems && this.haveItems.isEmpty() && this.needItems.isEmpty() && !this.transactions.isEmpty();
    }

    /**
     * Try to execute the transaction
     *
     * If it fails
     */
    public void execute() {
        if ( this.canExecute() ) {
            for ( Transaction transaction : this.transactions ) {
                transaction.commit();
            }
        } else {
            for ( Transaction transaction : this.transactions ) {
                transaction.revert();
            }
        }
    }

}
