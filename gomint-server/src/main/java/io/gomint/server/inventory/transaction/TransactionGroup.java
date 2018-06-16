package io.gomint.server.inventory.transaction;

import io.gomint.event.inventory.InventoryTransactionEvent;
import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class TransactionGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger( TransactionGroup.class );

    private final EntityPlayer player;
    private final List<Transaction> transactions = new ArrayList<>();

    // Need / have for this transactions
    @Getter
    private List<ItemStack> haveItems = new ArrayList<>();
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
                    this.matchItems = false;
                    return;
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

    private void mergeTransactions() {
        Map<Inventory, Map<Integer, List<Transaction>>> mergedTransactions = new HashMap<>();

        for ( Transaction transaction : this.transactions ) {
            if ( transaction.hasInventory() ) {
                Map<Integer, List<Transaction>> slotTransactions = mergedTransactions.computeIfAbsent( transaction.getInventory(), inventory -> new HashMap<>() );
                slotTransactions.computeIfAbsent( transaction.getSlot(), integer -> new ArrayList<>() ).add( transaction );
            }
        }

        for ( Map.Entry<Inventory, Map<Integer, List<Transaction>>> inventoryMapEntry : mergedTransactions.entrySet() ) {
            for ( Map.Entry<Integer, List<Transaction>> slotEntry : inventoryMapEntry.getValue().entrySet() ) {
                if ( slotEntry.getValue().size() > 1 ) {
                    LOGGER.debug( "Merging slot {} for inventory {}", slotEntry.getKey(), inventoryMapEntry.getKey() );

                    List<Transaction> transactions = slotEntry.getValue();
                    List<Transaction> original = new ArrayList<>( transactions );
                    ItemStack lastTargetItem = null;
                    InventoryTransaction startTransaction = null;

                    for ( int i = 0; i < transactions.size(); i++ ) {
                        Transaction ts = transactions.get( i );

                        ItemStack sourceItem = ts.getSourceItem() != null ? ( (io.gomint.server.inventory.item.ItemStack) ts.getSourceItem() ).clone() : null;
                        if ( ts.hasInventory() && sourceItem != null ) {
                            ItemStack checkSourceItem = ts.getInventory().getItem( ts.getSlot() );

                            // Check if source inventory changed during transaction
                            if ( checkSourceItem.equals( sourceItem ) && sourceItem.getAmount() == checkSourceItem.getAmount() ) {
                                transactions.remove( i );
                                startTransaction = (InventoryTransaction) ts;
                                lastTargetItem = ts.getTargetItem();
                                break;
                            }
                        }
                    }

                    if ( startTransaction == null ) {
                        return;
                    }

                    int sortedThisLoop;

                    do {
                        sortedThisLoop = 0;
                        for ( int i = 0; i < transactions.size(); i++ ) {
                            Transaction ts = transactions.get( i );

                            ItemStack actionSource = ts.getSourceItem();
                            if ( actionSource.equals( lastTargetItem ) && actionSource.getAmount() == lastTargetItem.getAmount() ) {
                                lastTargetItem = ts.getTargetItem();
                                transactions.remove( i );
                                sortedThisLoop++;
                            } else if ( actionSource.equals( lastTargetItem ) ) {
                                lastTargetItem.setAmount( lastTargetItem.getAmount() - actionSource.getAmount() );
                                transactions.remove( i );
                                if ( lastTargetItem.getAmount() == 0 ) {
                                    sortedThisLoop++;
                                }
                            }
                        }
                    } while ( sortedThisLoop > 0 );

                    if ( !transactions.isEmpty() ) {
                        LOGGER.debug( "Failed to compact {} actions", original.size() );
                        return;
                    }

                    for ( Transaction transaction : original ) {
                        this.transactions.remove( transaction );
                    }

                    this.transactions.add( new InventoryTransaction( startTransaction.getOwner(), startTransaction.getInventory(), startTransaction.getSlot(), startTransaction.getSourceItem(), lastTargetItem ) );
                    LOGGER.debug( "Successfully compacted {} actions", original.size() );
                }
            }
        }
    }

    /**
     * Check if transaction is complete and can be executed
     *
     * @return true if the transaction is complete and can be executed
     */
    private boolean canExecute() {
        this.mergeTransactions();
        this.calcMatchItems();

        boolean matched = this.matchItems && this.haveItems.isEmpty() && this.needItems.isEmpty() && !this.transactions.isEmpty();
        if ( matched ) {
            List<io.gomint.inventory.transaction.Transaction> transactionList = new ArrayList<>( this.transactions );
            InventoryTransactionEvent transactionEvent = new InventoryTransactionEvent( this.player, transactionList );
            this.player.getWorld().getServer().getPluginManager().callEvent( transactionEvent );
            return !transactionEvent.isCancelled();
        }

        return false;
    }

    /**
     * Try to execute the transaction
     *
     * @param forceExecute to force execution (like creative mode does)
     */
    public void execute( boolean forceExecute ) {
        if ( this.canExecute() || forceExecute ) {
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
