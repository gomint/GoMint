/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;

import io.gomint.event.player.PlayerCraftingEvent;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.session.SessionInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.OneSlotInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;

import java.util.Collection;

public class CraftingSession implements Session {

    private final PlayerConnection connection;
    private final Inventory inputInventory;
    private final Inventory outputInventory;

    private Recipe recipe;
    private byte amount;

    public CraftingSession(PlayerConnection connection) {
        this.connection = connection;

        // Check which input size we currently have
        this.inputInventory = new SessionInventory(connection.getServer().getItems(),
            connection.getEntity(),
            connection.getEntity().getCraftingInputInventory().size());
        this.outputInventory = new OneSlotInventory(connection.getServer().getItems(),
            connection.getEntity());
    }

    public CraftingSession findRecipe(int recipeId) {
        this.recipe = this.connection.getServer().getRecipeManager().getRecipe(recipeId);
        return this;
    }

    public void setAmountOfCrafts(byte amount) {
        this.amount = amount;
    }

    @Override
    public void addInput(ItemStack item) {
        this.inputInventory.addItem(item);
    }

    @Override
    public boolean process() {
        // Generate a output stack for compare
        Collection<io.gomint.inventory.item.ItemStack> output = this.recipe.createResult();

        // Craft the amount wanted
        for (byte i = 0; i < this.amount; i++) {
            // Let the recipe check if it can complete
            int[] consumeSlots = this.recipe.isCraftable(this.inputInventory);
            boolean craftable = consumeSlots != null;
            if (!craftable) {
                return false;
            }

            PlayerCraftingEvent event = new PlayerCraftingEvent(this.connection.getEntity(), this.recipe);
            this.connection.getEntity().getWorld().getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }

            // We can craft this
            for (io.gomint.inventory.item.ItemStack itemStack : output) {
                if (!this.outputInventory.hasPlaceFor(itemStack)) {
                    return false;
                }
            }

            // Consume items
            for (int slot : consumeSlots) {
                io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) this.inputInventory.getItem(slot);
                itemStack.afterPlacement();
            }

            // We can craft this
            for (io.gomint.inventory.item.ItemStack itemStack : output) {
                this.outputInventory.addItem(itemStack);
            }
        }

        return true;
    }

    @Override
    public Inventory getOutput() {
        return this.outputInventory;
    }

}
