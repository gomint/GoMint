/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.network.packet.Packet;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Resembles a smelting recipe which may be used in conjunction with furnaces.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class SmeltingRecipe extends Recipe {

    private final String block;

    private final ItemStack input;
    private final ItemStack outcome;

    /**
     * Create new smelting recipe
     *
     * @param input   for this recipe
     * @param outcome of this recipe
     * @param uuid    of the recipe
     */
    public SmeltingRecipe(String block, ItemStack input, ItemStack outcome, UUID uuid, int priority) {
        super(uuid, priority);

        this.block = block;

        this.input = input;
        this.outcome = outcome;
    }

    @Override
    public ItemStack[] getIngredients() {
        return new ItemStack[]{this.input};
    }

    @Override
    public Collection<ItemStack> createResult() {
        return Collections.singletonList(((io.gomint.server.inventory.item.ItemStack) this.outcome).clone());
    }

    @Override
    public void serialize(PacketBuffer buffer) {
        // The type of this recipe is defined after the input metadata
        buffer.writeSignedVarInt(this.input.getData() == 0 ? 2 : 3);

        // We need to custom write items
        buffer.writeSignedVarInt(((io.gomint.server.inventory.item.ItemStack) this.input).getMaterial());
        if (this.input.getData() != 0) buffer.writeSignedVarInt(this.input.getData());

        Packet.writeItemStack(this.outcome, buffer);
        buffer.writeString(this.block);
    }

    @Override
    public int[] isCraftable(Inventory inputInventory) {
        return new int[0];
    }

}
