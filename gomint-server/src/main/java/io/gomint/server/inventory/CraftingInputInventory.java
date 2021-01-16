package io.gomint.server.inventory;

import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CraftingInputInventory extends ContainerInventory<CraftingInputInventory> {

    private BlockPosition position;

    public CraftingInputInventory(Items items, InventoryHolder owner) {
        super(items, owner, 4);
    }

    @Override
    public WindowType getType() {
        return WindowType.WORKBENCH;
    }

    @Override
    public void onOpen(EntityPlayer player) {

    }

    @Override
    public BlockPosition containerPosition() {
        return this.position;
    }

    @Override
    public void onClose(EntityPlayer player) {
        WorldAdapter worldAdapter = player.world();
        Location location = player.location();

        // Push out all items in the crafting views
        for (ItemStack<?> stack : player.craftingInventory().contents()) {
            worldAdapter.dropItem(location, stack);
        }

        // Client closed its crafting view
        player.craftingInventory().resizeAndClear(4);
        player.craftingInputInventory().resizeAndClear(4);
    }

    @Override
    public void sendContents(PlayerConnection playerConnection) {

    }

    @Override
    public void sendContents(int slot, PlayerConnection playerConnection) {

    }

    @Override
    public InventoryType inventoryType() {
        return InventoryType.CRAFTING;
    }

    public void setPosition(BlockPosition position) {
        this.position = position;
    }

}
