/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.server.enchant.Enchantment;
import io.gomint.server.enchant.EnchantmentHelper;
import io.gomint.server.enchant.EnchantmentSelector;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.OneSlotInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.util.Pair;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Gamemode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnchantingSession implements Session {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchantingSession.class);

    private final Inventory inputInventory;
    private final Inventory outputInventory;
    private final PlayerConnection connection;
    private int selectedEnchantment;

    public EnchantingSession(PlayerConnection connection) {
        this.connection = connection;
        this.inputInventory = new OneSlotInventory(connection.getServer().getItems(),
            connection.getEntity());
        this.outputInventory = new OneSlotInventory(connection.getServer().getItems(),
            connection.getEntity());
    }

    @Override
    public Inventory getOutput() {
        return this.outputInventory;
    }

    @Override
    public boolean process() {
        // Sanity check
        if (this.selectedEnchantment < 0 ||
            this.selectedEnchantment > 2 ||
            this.connection.getEntity().getGamemode() == Gamemode.SPECTATOR) {
            return false;
        }

        // Get enchantment table
        EnchantmentTableInventory inv = (EnchantmentTableInventory) this.connection.getEntity().getCurrentOpenContainer();
        Location location = new Location(inv.getWorld(), inv.getContainerPosition());

        // Generate enchantments from helper and get them
        Pair<int[], List<List<Enchantment>>> enchantments = EnchantmentSelector.getEnchantments(this.connection.getServer().getEnchantments(),
            new FastRandom(this.connection.getEntity().getEnchantmentSeed()), location,
            (ItemStack) this.inputInventory.getItem(0));

        // Item is not enchantable => return
        if (enchantments == null) {
            LOGGER.warn("Got enchantment request from {} on a non enchantable item {}", this.connection.getEntity(),
                this.inputInventory.getItem(0));
            return false;
        }

        int cost = enchantments.getFirst()[this.selectedEnchantment];
        List<Enchantment> ench = enchantments.getSecond().get(this.selectedEnchantment);

        // Player does not have enough levels to cover "costs"
        if (this.connection.getEntity().getGamemode() != Gamemode.CREATIVE &&
            this.connection.getEntity().getLevel() < cost) {
            LOGGER.info("Got enchantment request from {} but has not enough levels, needs {} to cover requirements", this.connection.getEntity(),
                cost);
            return false;
        }

        // Check if the player has enough levels for paying
        int pay = this.selectedEnchantment + 1;
        if (this.connection.getEntity().getGamemode() != Gamemode.CREATIVE &&
            this.connection.getEntity().getLevel() < pay) {
            LOGGER.info("Got enchantment request from {} but has not enough levels, needs {} to cover costs", this.connection.getEntity(),
                pay);
            return false;
        }

        // Check if the enchantment table contains enough lapis
        ItemStack lapis = (ItemStack) this.connection.getEntity().getCurrentOpenContainer().getItem(1);
        if (this.connection.getEntity().getGamemode() != Gamemode.CREATIVE &&
            (lapis.getItemType() != ItemType.LAPIS_LAZULI || lapis.getAmount() < pay)) {
            LOGGER.info("Got enchantment request from {} but has not enough lapis, needs {} to cover costs", this.connection.getEntity(),
                pay);
            return false;
        }

        // Modify player level and lapis amound if needed
        if (this.connection.getEntity().getGamemode() != Gamemode.CREATIVE) {
            this.connection.getEntity().setLevel(this.connection.getEntity().getLevel() - pay);
            lapis.setAmount(lapis.getAmount() - pay);
        }

        // Now we can enchant the item in the output slot
        ItemStack toEnchant = (ItemStack) this.inputInventory.getItem(0);

        for (Enchantment enchantment : ench) {
            toEnchant.addEnchantment(enchantment.getClass(), enchantment.getLevel());
        }

        this.outputInventory.setItem(0, toEnchant);

        // Generate new enchant seed
        this.connection.getEntity().generateNewEnchantmentSeed();

        return true;
    }

    @Override
    public void addInput(ItemStack item) {
        LOGGER.debug("Got item for enchant: {}", item);
        this.inputInventory.setItem(0, item);
    }

    public Session selectOption(int selection) {
        this.selectedEnchantment = selection;
        return this;
    }

}
