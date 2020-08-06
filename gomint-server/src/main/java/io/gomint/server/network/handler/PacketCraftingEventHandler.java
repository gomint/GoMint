package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerCraftingEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.MathUtils;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.inventory.CraftingInputInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketCraftingEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCraftingEventHandler implements PacketHandler<PacketCraftingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketCraftingEventHandler.class);

    @Override
    public void handle(PacketCraftingEvent packet, long currentTimeMillis, PlayerConnection connection) {
        // Get the recipe based on its id
        Recipe recipe = connection.getEntity().getWorld().getServer().getRecipeManager().getRecipe(packet.getRecipeId());
        if (recipe == null) {
            // Resend inventory and call it a day
            for (ItemStack itemStack : connection.getEntity().getCraftingInputInventory().getContentsArray()) {
                connection.getEntity().getInventory().addItem(itemStack);
            }

            connection.getEntity().getInventory().sendContents(connection);
            return;
        }

        // Generate a output stack for compare
        Collection<ItemStack> output = recipe.createResult();

        // Crafting types:
        // 0 => Small crafting window inside of the player inventory

        // If the crafting window is small you can't craft bigger recipes
        if (packet.getRecipeType() == 0 && connection.getEntity().getCraftingInputInventory().size() > 4) {
            // Resend inventory and call it a day
            connection.getEntity().getInventory().sendContents(connection);
            LOGGER.debug("Did not craft due to wrong inventory size");
            return;
        }

        boolean hadCrafted = false;

        craft:
        while (true) {
            // Let the recipe check if it can complete
            int[] consumeSlots = recipe.isCraftable(connection.getEntity().getCraftingInputInventory());
            boolean craftable = consumeSlots != null;
            if (!craftable) {
                LOGGER.debug("Could not craft, recipe denied: {} -> {}", recipe.getClass().getName(), recipe.getIngredients());
                break;
            }

            hadCrafted = true;

            PlayerCraftingEvent event = new PlayerCraftingEvent(connection.getEntity(), recipe);
            connection.getEntity().getWorld().getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                break;
            }

            // We can craft this
            for (ItemStack itemStack : output) {
                if (!connection.getEntity().getCraftingResultInventory().hasPlaceFor(itemStack)) {
                    break craft;
                }
            }

            // Consume items
            Inventory inputInventory = connection.getEntity().getCraftingInputInventory();
            for (int slot : consumeSlots) {
                io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) inputInventory.getItem(slot);
                itemStack.afterPlacement();
            }

            // We can craft this
            for (ItemStack itemStack : output) {
                connection.getEntity().getCraftingResultInventory().addItem(itemStack);
            }
        }

        // Do we need to alter the output ?
        if (hadCrafted) {
            // Reset leftovers into players inventory
            for (ItemStack inputItem : connection.getEntity().getCraftingInputInventory().getContentsArray()) {
                if (inputItem instanceof ItemAir) {
                    continue;
                }

                connection.getEntity().getInventory().addItem(inputItem);
            }

            connection.getEntity().getCraftingInputInventory().clear();

            // When shift clicked there is no transaction for the output afterwards => move all results into the players inventory
            connection.getServer().addToMainThread(() -> {
                for (ItemStack inputItem : connection.getEntity().getCraftingResultInventory().getContentsArray()) {
                    if (inputItem instanceof ItemAir) {
                        continue;
                    }

                    connection.getEntity().getInventory().addItem(inputItem);
                }

                connection.getEntity().getCraftingResultInventory().clear();
            });
        } else {
            // We can't craft => reset inventory
            for (ItemStack inputItem : connection.getEntity().getCraftingInputInventory().getContentsArray()) {
                if (inputItem instanceof ItemAir) {
                    continue;
                }

                connection.getEntity().getInventory().addItem(inputItem);
            }

            connection.getEntity().getCraftingInputInventory().clear();
        }
    }

}
