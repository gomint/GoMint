package io.gomint.server.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Inventory implements io.gomint.inventory.Inventory {

    protected InventoryHolder owner;
    protected Set<PlayerConnection> viewer = new HashSet<>();

    protected int size;
    protected ItemStack[] contents;

    public Inventory( InventoryHolder owner, int size ) {
        this.owner = owner;
        this.size = size;

        this.clear();

        // Add owner to viewers if needed
        if ( this.owner instanceof EntityPlayer ) {
            addViewer( (EntityPlayer) this.owner );
        }
    }

    public void addViewer( EntityPlayer player ) {
        this.sendContents( player.getConnection() );
        this.viewer.add( player.getConnection() );
    }

    public void addViewerWithoutSend( EntityPlayer player ) {
        this.viewer.add( player.getConnection() );
    }

    public void removeViewer( EntityPlayer player ) {
        this.viewer.remove( player.getConnection() );
    }

    @Override
    public void setItem( int index, ItemStack item ) {
        // Prevent invalid null items
        if ( item == null ) {
            item = ItemAir.create( 0 );
        }

        this.contents[index] = item;

        for ( PlayerConnection playerConnection : this.viewer ) {
            this.sendContents( index, playerConnection );
        }
    }

    public ItemStack[] getContents() {
        return this.contents;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ItemStack getItem( int slot ) {
        return this.contents[slot];
    }

    /**
     * Send the whole inventory to the client, overwriting its current view
     *
     * @param playerConnection to send this inventory to
     */
    public abstract void sendContents( PlayerConnection playerConnection );

    /**
     * Send a specific slot to the client
     *
     * @param slot             to send
     * @param playerConnection which should get this slot
     */
    public abstract void sendContents( int slot, PlayerConnection playerConnection );

    /**
     * Checks if this inventory can store the given item stack without being full
     *
     * @param itemStack The item stack which may fit
     * @return true when the inventory has place for the item stack, false if not
     */
    public boolean hasPlaceFor( ItemStack itemStack ) {
        if ( itemStack instanceof io.gomint.server.inventory.item.ItemStack ) {
            io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;
            ItemStack clone = serverItemStack.clone();

            for ( ItemStack content : this.contents ) {
                if ( content instanceof ItemAir ) {
                    return true;
                } else if ( content.equals( clone ) &&
                    content.getAmount() <= content.getMaximumAmount() ) {
                    if ( content.getAmount() + clone.getAmount() <= content.getMaximumAmount() ) {
                        return true;
                    } else {
                        int amountToDecrease = content.getMaximumAmount() - content.getAmount();
                        clone.setAmount( clone.getAmount() - amountToDecrease );
                    }

                    if ( clone.getAmount() == 0 ) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    /**
     * Add a item into the inventory. Try to merge existing stacks or use the next free slot.
     *
     * @param itemStack the item stack which should be added
     * @return true when it got added, false if not
     */
    public boolean addItem( ItemStack itemStack ) {
        // Check if we have place for this item
        if ( !this.hasPlaceFor( itemStack ) ) {
            return false;
        }

        if ( itemStack instanceof io.gomint.server.inventory.item.ItemStack ) {
            io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;
            ItemStack clone = serverItemStack.clone();

            // First try to merge
            for ( int i = 0; i < this.contents.length; i++ ) {
                if ( this.contents[i].equals( clone ) &&
                    this.contents[i].getAmount() <= this.contents[i].getMaximumAmount() ) {
                    if ( this.contents[i].getAmount() + clone.getAmount() <= this.contents[i].getMaximumAmount() ) {
                        this.contents[i].setAmount( this.contents[i].getAmount() + clone.getAmount() );
                        clone.setAmount( 0 );
                    } else {
                        int amountToDecrease = this.contents[i].getMaximumAmount() - this.contents[i].getAmount();
                        this.contents[i].setAmount( this.contents[i].getMaximumAmount() );
                        clone.setAmount( clone.getAmount() - amountToDecrease );
                    }

                    // Send item to all viewers
                    setItem( i, this.contents[i] );

                    // We added all of the stack to this inventory
                    if ( clone.getAmount() == 0 ) {
                        return true;
                    }
                }
            }

            // Search for a free slot
            for ( int i = 0; i < this.contents.length; i++ ) {
                if ( this.contents[i] instanceof ItemAir ) {
                    setItem( i, clone );
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clear() {
        if ( this.contents != null ) {
            for ( int i = 0; i < this.contents.length; i++ ) {
                if ( this.contents[i] != null ) {
                     onRemove( i );
                }
            }
        }

        this.contents = new ItemStack[this.size];
        Arrays.fill( this.contents, ItemAir.create( 0 ) );

        // Inform all viewers
        for ( PlayerConnection playerConnection : this.viewer ) {
            sendContents( playerConnection );
        }
    }

    protected void onRemove( int slot ) {

    }

    public void resizeAndClear( int newSize ) {
        this.size = newSize;
        this.clear();
    }

    @Override
    public Collection<Entity> getViewers() {
        Set<Entity> viewers = new HashSet<>();

        for ( PlayerConnection playerConnection : this.viewer ) {
            viewers.add( playerConnection.getEntity() );
        }

        return viewers;
    }

    public abstract InventoryType getInventoryType();

}
