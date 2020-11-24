/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.network.packet.Packet;

import java.util.Arrays;
import java.util.UUID;

/**
 * Resembles a shapeless crafting recipe, i.e. a recipe for which the
 * arrangement of its ingredients does not matter. All that counts is
 * that all ingredients and no more items are put into the crafting
 * container.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class ShapelessRecipe extends CraftingRecipe {

    private final int id;
    private final String name;
    private final String block;
    private final ItemStack[] ingredients;
    private final ItemStack[] outcome;

    public ShapelessRecipe(String name, String block, ItemStack[] ingredients, ItemStack[] outcome, UUID uuid, int priority) {
        super(outcome, uuid, priority);
        this.name = name;
        this.block = block;
        this.ingredients = ingredients;
        this.outcome = outcome;

        this.id = this.getNewID();
    }

    @Override
    public ItemStack[] getIngredients() {
        return this.ingredients;
    }

    @Override
    public void serialize(PacketBuffer buffer) {
        buffer.writeSignedVarInt(0);

        buffer.writeString(this.name);
        buffer.writeUnsignedVarInt(this.ingredients.length);
        for (ItemStack ingredient : this.ingredients) {
            Packet.writeRecipeInput(ingredient, buffer);
        }

        buffer.writeUnsignedVarInt(this.outcome.length);
        for (ItemStack itemStack : this.outcome) {
            Packet.writeItemStack(itemStack, buffer);
        }

        buffer.writeUUID(this.getUUID());
        buffer.writeString(this.block);
        buffer.writeSignedVarInt(this.getPriority());
        buffer.writeUnsignedVarInt(this.id);
    }

    @Override
    public int[] isCraftable(Inventory inputInventory) {
        ItemStack[] inputItems = inputInventory.getContents();
        ItemStack[] ingredients = getIngredients();
        int[] consumeSlots = new int[ingredients.length];
        Arrays.fill(consumeSlots, -1);

        for (int rI = 0; rI < ingredients.length; rI++) {
            ItemStack recipeWanted = ingredients[rI];
            boolean found = false;

            for (int i = 0; i < inputItems.length; i++) {
                ItemStack input = inputItems[i];

                if (canBeUsedForCrafting(recipeWanted, input)) {
                    // Check if we already consumed this
                    int alreadyConsumed = 0;
                    for (int consumeSlot : consumeSlots) {
                        if (consumeSlot == i) {
                            alreadyConsumed++;
                        }
                    }

                    if (input.getAmount() >= alreadyConsumed + 1) {
                        consumeSlots[rI] = i;
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                return null;
            }
        }

        return consumeSlots;
    }

}
