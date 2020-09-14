package io.gomint.server.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.util.Pair;
import io.gomint.taglib.NBTTagCompound;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Inventory implements io.gomint.inventory.Inventory {

    protected InventoryHolder owner;
    Set<PlayerConnection> viewer = new HashSet<>();

    protected int size;
    protected ItemStack[] contents;

    private Set<Consumer<Pair<Integer, ItemStack>>> changeObservers;
    private final Items items;

    public Inventory(Items items, InventoryHolder owner, int size) {
        this.owner = owner;
        this.size = size;
        this.items = items;

        this.clear();
    }

    public void addViewer(EntityPlayer player) {
        this.sendContents(player.getConnection());
        this.viewer.add(player.getConnection());
    }

    public void addViewerWithoutAction(EntityPlayer player) {
        this.viewer.add(player.getConnection());
    }

    public void removeViewerWithoutAction(EntityPlayer player) {
        this.viewer.remove(player.getConnection());
    }

    public void removeViewer(EntityPlayer player) {
        this.viewer.remove(player.getConnection());
    }

    @Override
    public void setItem(int index, ItemStack item) {
        // Prevent invalid null items
        if (item == null) {
            item = ItemAir.create(0);
        }

        // Get old item
        io.gomint.server.inventory.item.ItemStack oldItemStack = (io.gomint.server.inventory.item.ItemStack) this.contents[index];
        if (oldItemStack != null) {
            oldItemStack.removePlace();
        }

        // Set new item
        io.gomint.server.inventory.item.ItemStack newStack = (io.gomint.server.inventory.item.ItemStack) item.clone();

        if (this.changeObservers != null) {
            Pair<Integer, ItemStack> pair = new Pair<>(index, newStack);
            for (Consumer<Pair<Integer, ItemStack>> observer : this.changeObservers) {
                observer.accept(pair);
            }
        }

        this.contents[index] = newStack;
        newStack.addPlace(this, index);

        for (PlayerConnection playerConnection : this.viewer) {
            this.sendContents(index, playerConnection);
        }
    }

    @Override
    public ItemStack[] getContents() {
        if (this.contents == null) return null;
        return Arrays.copyOf(this.contents, this.contents.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.contents[slot];
    }

    /**
     * Send the whole inventory to the client, overwriting its current view
     *
     * @param playerConnection to send this inventory to
     */
    public abstract void sendContents(PlayerConnection playerConnection);

    /**
     * Send a specific slot to the client
     *
     * @param slot             to send
     * @param playerConnection which should get this slot
     */
    public abstract void sendContents(int slot, PlayerConnection playerConnection);

    /**
     * Checks if this inventory can store the given item stack without being full
     *
     * @param itemStack The item stack which may fit
     * @return true when the inventory has place for the item stack, false if not
     */
    public boolean hasPlaceFor(ItemStack itemStack) {
        io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;
        ItemStack clone = serverItemStack.clone();

        for (ItemStack content : this.getContents()) {
            if (content instanceof ItemAir) {
                return true;
            } else if (content.equals(clone) &&
                content.getAmount() <= content.getMaximumAmount()) {
                if (content.getAmount() + clone.getAmount() <= content.getMaximumAmount()) {
                    return true;
                } else {
                    int amountToDecrease = content.getMaximumAmount() - content.getAmount();
                    clone.setAmount(clone.getAmount() - amountToDecrease);
                }

                if (clone.getAmount() == 0) {
                    return true;
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
    public boolean addItem(ItemStack itemStack) {
        // Check if we have place for this item
        if (!this.hasPlaceFor(itemStack)) {
            return false;
        }

        io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;
        ItemStack clone = serverItemStack.clone();

        ItemStack[] invContents = this.getContents();

        // First try to merge
        for (int i = 0; i < invContents.length; i++) {
            if (invContents[i].equals(clone) &&
                invContents[i].getAmount() <= invContents[i].getMaximumAmount()) {
                if (invContents[i].getAmount() + clone.getAmount() <= invContents[i].getMaximumAmount()) {
                    invContents[i].setAmount(invContents[i].getAmount() + clone.getAmount());
                    clone.setAmount(0);
                } else {
                    int amountToDecrease = invContents[i].getMaximumAmount() - invContents[i].getAmount();
                    invContents[i].setAmount(invContents[i].getMaximumAmount());
                    clone.setAmount(clone.getAmount() - amountToDecrease);
                }

                // Send item to all viewers
                setItem(i, invContents[i]);

                // We added all of the stack to this inventory
                if (clone.getAmount() == 0) {
                    return true;
                }
            }
        }

        // Search for a free slot
        for (int i = 0; i < invContents.length; i++) {
            if (invContents[i] instanceof ItemAir) {
                setItem(i, clone);
                return true;
            }
        }

        return false;
    }

    @Override
    public void clear() {
        ItemStack[] contents = this.getContents();
        if (contents != null) {
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null) {
                    onRemove(i);
                }
            }
        }

        this.contents = new ItemStack[this.size];
        Arrays.fill(this.contents, ItemAir.create(0));

        // Inform all viewers
        for (PlayerConnection playerConnection : this.viewer) {
            sendContents(playerConnection);
        }
    }

    protected void onRemove(int slot) {
        io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) this.getItem(slot);
        itemStack.removePlace();
    }

    public void resizeAndClear(int newSize) {
        this.size = newSize;
        this.clear();
    }

    @Override
    public Collection<Entity> getViewers() {
        Set<Entity> viewers = new HashSet<>();

        for (PlayerConnection playerConnection : this.viewer) {
            viewers.add(playerConnection.getEntity());
        }

        return viewers;
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        for (ItemStack content : this.getContents()) {
            if (itemStack.equals(content)) {
                return true;
            }
        }

        return false;
    }

    public InventoryHolder getOwner() {
        return this.owner;
    }

    public void addObserver(Consumer<Pair<Integer, ItemStack>> consumer) {
        if (this.changeObservers == null) {
            this.changeObservers = new HashSet<>();
        }

        this.changeObservers.add(consumer);
    }

    public void clearViewers() {
        this.viewer.clear();
    }

    @Override
    public Stream<ItemStack> items() {
        return Stream.of(this.getContents());
    }

    protected ItemStack loadItem(NBTTagCompound compound) {
        short data = compound.getShort("Damage", (short) 0);
        byte amount = compound.getByte("Count", (byte) 0);
        String itemId = compound.getString("Name", "");
        if (itemId.isEmpty()) {
            return ItemAir.create(0);
        }

        return this.items.create(itemId, data, amount, null);
    }

    protected NBTTagCompound persistItem(io.gomint.server.inventory.item.ItemStack itemStack) {
        NBTTagCompound compound = new NBTTagCompound("");
        compound.addValue("Damage", itemStack.getData());
        compound.addValue("Count", itemStack.getAmount());
        compound.addValue("Name", itemStack.getMaterial());
        return compound;
    }

    public void initFromNBT(List<Object> compounds) {
        if (compounds != null) {
            byte slot = 0;
            for (Object compound : compounds) {
                NBTTagCompound itemCompound = (NBTTagCompound) compound;
                slot = itemCompound.getByte("Slot", slot);

                this.setItem(slot, this.loadItem(itemCompound));

                slot++;
            }
        }
    }

    public List<NBTTagCompound> persistToNBT() {
        List<NBTTagCompound> compounds = new ArrayList<>();

        byte slot = 0;
        for (ItemStack itemStack : getContents()) {
            NBTTagCompound itemCompound = this.persistItem((io.gomint.server.inventory.item.ItemStack) itemStack);
            itemCompound.addValue("Slot", slot);
            compounds.add(itemCompound);

            slot++;
        }

        return compounds;
    }

}
