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
public abstract class Inventory<I> implements io.gomint.inventory.Inventory<I> {

    protected InventoryHolder owner;
    Set<PlayerConnection> viewer = new HashSet<>();

    protected int size;
    protected ItemStack<?>[] contents;

    private Set<Consumer<Pair<Integer, ItemStack<?>>>> changeObservers;
    private final Items items;

    public Inventory(Items items, InventoryHolder owner, int size) {
        this.owner = owner;
        this.size = size;
        this.items = items;

        this.clear();
    }

    public void addViewer(EntityPlayer player) {
        this.sendContents(player.connection());
        this.viewer.add(player.connection());
    }

    public void addViewerWithoutAction(EntityPlayer player) {
        this.viewer.add(player.connection());
    }

    public void removeViewerWithoutAction(EntityPlayer player) {
        this.viewer.remove(player.connection());
    }

    public void removeViewer(EntityPlayer player) {
        this.viewer.remove(player.connection());
    }

    public void setItemWithoutClone(int index, ItemStack<?> item) {
        this.contents[index] = item;
    }

    @Override
    public I item(int index, ItemStack<?> item) {
        // Prevent invalid null items
        if (item == null) {
            item = ItemAir.create(0);
        }

        // Get old item
        io.gomint.server.inventory.item.ItemStack<?> oldItemStack = (io.gomint.server.inventory.item.ItemStack<?>) this.contents[index];
        if (oldItemStack != null) {
            oldItemStack.removePlace();
        }

        // Set new item
        ItemStack<?> newStack = item.clone();

        if (this.changeObservers != null) {
            Pair<Integer, ItemStack<?>> pair = new Pair<>(index, newStack);
            for (Consumer<Pair<Integer, ItemStack<?>>> observer : this.changeObservers) {
                observer.accept(pair);
            }
        }

        this.contents[index] = newStack;
        ((io.gomint.server.inventory.item.ItemStack<?>) newStack).place(this, index);

        for (PlayerConnection playerConnection : this.viewer) {
            this.sendContents(index, playerConnection);
        }

        return (I) this;
    }

    @Override
    public ItemStack<?>[] contents() {
        if (this.contents == null) return null;
        return Arrays.copyOf(this.contents, this.contents.length);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ItemStack<?> item(int slot) {
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
    public boolean hasPlaceFor(ItemStack<?> itemStack) {
        io.gomint.server.inventory.item.ItemStack<?> serverItemStack = (io.gomint.server.inventory.item.ItemStack<?>) itemStack;
        ItemStack<?> clone = serverItemStack.clone();

        for (ItemStack<?> content : this.contents()) {
            if (content instanceof ItemAir) {
                return true;
            } else if (content.equals(clone) &&
                content.amount() <= content.maximumAmount()) {
                if (content.amount() + clone.amount() <= content.maximumAmount()) {
                    return true;
                } else {
                    int amountToDecrease = content.maximumAmount() - content.amount();
                    clone.amount(clone.amount() - amountToDecrease);
                }

                if (clone.amount() == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean addItem(ItemStack<?> itemStack) {
        return this.addItemWithSlot(itemStack) != -1;
    }

    @Override
    public int addItemWithSlot(ItemStack<?> itemStack) {
        // Check if we have place for this item
        if (!this.hasPlaceFor(itemStack)) {
            return -1;
        }

        io.gomint.server.inventory.item.ItemStack<?> serverItemStack = (io.gomint.server.inventory.item.ItemStack<?>) itemStack;
        ItemStack<?> clone = serverItemStack.clone();

        ItemStack<?>[] invContents = this.contents();

        // First try to merge
        for (int i = 0; i < invContents.length; i++) {
            if (invContents[i].equals(clone) &&
                invContents[i].amount() <= invContents[i].maximumAmount()) {
                if (invContents[i].amount() + clone.amount() <= invContents[i].maximumAmount()) {
                    invContents[i].amount(invContents[i].amount() + clone.amount());
                    clone.amount(0);
                } else {
                    int amountToDecrease = invContents[i].maximumAmount() - invContents[i].amount();
                    invContents[i].amount(invContents[i].maximumAmount());
                    clone.amount(clone.amount() - amountToDecrease);
                }

                // Send item to all viewers
                item(i, invContents[i]);

                // We added all of the stack to this inventory
                if (clone.amount() == 0) {
                    return i;
                }
            }
        }

        // Search for a free slot
        for (int i = 0; i < invContents.length; i++) {
            if (invContents[i] instanceof ItemAir) {
                item(i, clone);
                return i;
            }
        }

        return -1;
    }

    @Override
    public I clear() {
        ItemStack<?>[] contents = this.contents();
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

        return (I) this;
    }

    protected void onRemove(int slot) {
        io.gomint.server.inventory.item.ItemStack<?> itemStack = (io.gomint.server.inventory.item.ItemStack<?>) this.item(slot);
        itemStack.removePlace();
    }

    public void resizeAndClear(int newSize) {
        this.size = newSize;
        this.clear();
    }

    @Override
    public Collection<Entity<?>> viewers() {
        Set<Entity<?>> viewers = new HashSet<>();

        for (PlayerConnection playerConnection : this.viewer) {
            viewers.add(playerConnection.entity());
        }

        return viewers;
    }

    @Override
    public boolean contains(ItemStack<?> itemStack) {
        if (itemStack == null) {
            return false;
        }

        for (ItemStack<?> content : this.contents()) {
            if (itemStack.equals(content)) {
                return true;
            }
        }

        return false;
    }

    public InventoryHolder getOwner() {
        return this.owner;
    }

    public void addObserver(Consumer<Pair<Integer, ItemStack<?>>> consumer) {
        if (this.changeObservers == null) {
            this.changeObservers = new HashSet<>();
        }

        this.changeObservers.add(consumer);

        // We trigger the new consumer once per item
        for (int i = 0; i < this.contents.length; i++) {
            ItemStack<?> item = this.contents[i];
            consumer.accept(Pair.of(i, item));
        }
    }

    public void clearViewers() {
        this.viewer.clear();
    }

    @Override
    public Stream<ItemStack<?>> items() {
        return Stream.<ItemStack<?>>of(this.contents());
    }

    protected ItemStack<?> loadItem(NBTTagCompound compound) {
        short data = compound.getShort("Damage", (short) 0);
        byte amount = compound.getByte("Count", (byte) 0);
        String itemId = compound.getString("Name", "");
        if (itemId.isEmpty()) {
            return ItemAir.create(0);
        }

        return this.items.create(itemId, data, amount, compound.getCompound("tag", false));
    }

    protected NBTTagCompound persistItem(io.gomint.server.inventory.item.ItemStack<?> itemStack) {
        NBTTagCompound compound = new NBTTagCompound("");
        compound.addValue("Damage", itemStack.data());
        compound.addValue("Count", itemStack.amount());
        compound.addValue("Name", itemStack.material());

        if (itemStack.nbtData() != null) {
            compound.addValue("tag", itemStack.nbtData());
        }

        return compound;
    }

    public void initFromNBT(List<Object> compounds) {
        if (compounds != null) {
            byte slot = 0;
            for (Object compound : compounds) {
                NBTTagCompound itemCompound = (NBTTagCompound) compound;
                slot = itemCompound.getByte("Slot", slot);

                this.item(slot, this.loadItem(itemCompound));

                slot++;
            }
        }
    }

    public List<NBTTagCompound> persistToNBT() {
        List<NBTTagCompound> compounds = new ArrayList<>();

        byte slot = 0;
        for (ItemStack<?> itemStack : contents()) {
            NBTTagCompound itemCompound = this.persistItem((io.gomint.server.inventory.item.ItemStack<?>) itemStack);
            itemCompound.addValue("Slot", slot);
            compounds.add(itemCompound);

            slot++;
        }

        return compounds;
    }

}
