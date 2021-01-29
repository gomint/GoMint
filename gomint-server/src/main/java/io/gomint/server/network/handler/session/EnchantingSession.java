/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;

import io.gomint.event.enchant.ItemEnchantEvent;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.server.crafting.session.SessionInventory;
import io.gomint.server.enchant.Enchantment;
import io.gomint.server.enchant.EnchantmentSelector;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.util.Pair;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Gamemode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class EnchantingSession implements Session {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchantingSession.class);

    private final Inventory<?> inputInventory;
    private final Inventory<?> outputInventory;
    private final PlayerConnection connection;
    private int selectedEnchantment;

    public EnchantingSession(PlayerConnection connection) {
        this.connection = connection;
        this.inputInventory = new SessionInventory(connection.server().items(), connection.entity(), 2);
        this.outputInventory = new SessionInventory(connection.server().items(), connection.entity(), 1);
    }

    @Override
    public Inventory<?> getOutput() {
        return this.outputInventory;
    }

    @Override
    public boolean process() {
        // Sanity check
        if (this.selectedEnchantment < 0 ||
            this.selectedEnchantment > 2 ||
            this.connection.entity().gamemode() == Gamemode.SPECTATOR) {
            LOGGER.debug("Selected enchantment out of range or player is spectator");
            return false;
        }

        // Get enchantment table
        EnchantmentTableInventory inv = (EnchantmentTableInventory) this.connection.entity().currentOpenContainer();
        Location location = new Location(inv.world(), inv.containerPosition());

        // Generate enchantments from helper and get them
        Pair<int[], List<List<Enchantment>>> enchantments = EnchantmentSelector.determineAvailable(this.connection.server().enchantments(),
            new FastRandom(this.connection.entity().enchantmentSeed()), location,
            (ItemStack<?>) this.inputInventory.item(0));

        // Item is not enchantable => return
        if (enchantments == null) {
            LOGGER.warn("Got enchantment request from {} on a non enchantable item {}", this.connection.entity(),
                this.inputInventory.item(0));
            return false;
        }

        int cost = enchantments.getFirst()[this.selectedEnchantment];
        List<Enchantment> ench = enchantments.getSecond().get(this.selectedEnchantment);
        int pay = this.selectedEnchantment + 1;

        ItemEnchantEvent event = this.connection.entity().world().server().pluginManager().callEvent(new ItemEnchantEvent(
            this.connection.entity(),
            this.inputInventory.item(0),
            pay,
            pay,
            ench.stream().map(e -> (io.gomint.enchant.Enchantment) e).collect(Collectors.toList()),
            cost
        ));

        if (event.cancelled()) {
            return false;
        }

        // Player does not have enough levels to cover "costs"
        if (this.connection.entity().gamemode() != Gamemode.CREATIVE &&
            this.connection.entity().level() < event.levelRequirement()) {
            LOGGER.info("Got enchantment request from {} but has not enough levels, needs {} to cover requirements", this.connection.entity(),
                cost);
            return false;
        }

        // Check if the player has enough levels for paying
        if (this.connection.entity().gamemode() != Gamemode.CREATIVE &&
            this.connection.entity().level() < event.levelCost()) {
            LOGGER.info("Got enchantment request from {} but has not enough levels, needs {} to cover costs", this.connection.entity(),
                pay);
            return false;
        }

        // Check if the enchantment table contains enough lapis
        ItemStack<?> lapis = (ItemStack<?>) this.inputInventory.item(1);
        if (this.connection.entity().gamemode() != Gamemode.CREATIVE &&
            (lapis.itemType() != ItemType.LAPIS_LAZULI || lapis.amount() < event.materialCost())) {
            LOGGER.info("Got enchantment request from {} but has not enough lapis, needs {} to cover costs", this.connection.entity(),
                pay);
            return false;
        }

        // Modify player level and lapis amound if needed
        if (this.connection.entity().gamemode() != Gamemode.CREATIVE) {
            this.connection.entity().level(this.connection.entity().level() - event.levelCost());
            lapis.amount(lapis.amount() - event.materialCost());
        }

        // Now we can enchant the item in the output slot
        ItemStack<?> toEnchant = (ItemStack<?>) this.inputInventory.item(0);

        for (io.gomint.enchant.Enchantment enchantment : event.enchantments()) {
            toEnchant.enchant(enchantment.getClass(), enchantment.level());
        }

        this.outputInventory.item(0, toEnchant);

        // Generate new enchant seed
        this.connection.entity().generateNewEnchantmentSeed();

        return true;
    }

    @Override
    public void addInput(ItemStack<?> item, int slot) {
        LOGGER.debug("Got item for enchant: {} / {}", item, slot);
        this.inputInventory.item(slot, item);
    }

    @Override
    public void postProcess() {
        // Due to a bug in 1.16.200+ the client displays the enchantment one level to high (you tell it to enchant level
        // 1 and it displays level 2). To fix this we simply "correct" the client view by forcing the enchanted item
        // over after the transaction completes
        this.connection.entity().currentOpenContainer().sendContents(0, this.connection);
    }

    public Session selectOption(int selection) {
        this.selectedEnchantment = selection;
        return this;
    }

}
