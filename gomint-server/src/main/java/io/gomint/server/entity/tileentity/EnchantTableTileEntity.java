package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.event.enchant.EnchantmentSelectionEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.enchant.Enchantment;
import io.gomint.server.enchant.EnchantmentSelector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketPlayerEnchantmentOptions;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Pair;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "EnchantTable")
public class EnchantTableTileEntity extends ContainerTileEntity implements InventoryHolder {

    private static final String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghjijklmnopqrstuvwxyz0123456789";
    private static final char[] alphabetArray = alphabetString.toCharArray();

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public EnchantTableTileEntity(Block block, Items items) {
        super(block, items);
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        // Open the chest inventory for the entity
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            EnchantmentTableInventory inv = new EnchantmentTableInventory(this.items, this);
            if (player.openInventory(inv)) {
                // Select and send enchantment options
                inv.addObserver(slotItem -> {
                    if (slotItem.getFirst() == 0 && slotItem.getSecond() != null) {
                        this.selectAndSendEnchantments(player, slotItem.getSecond());
                    }
                });
            }
        }
    }

    private void selectAndSendEnchantments(EntityPlayer player, ItemStack<?> slotItem) {
        FastRandom random = new FastRandom(player.enchantmentSeed());
        Pair<int[], List<List<Enchantment>>> selectedEnchantments = EnchantmentSelector.determineAvailable(this.getBlock().world().server().enchantments(),
            random, this.getBlock().location(), (io.gomint.server.inventory.item.ItemStack<?>) slotItem);

        if (selectedEnchantments == null) {
            return;
        }

        List<EnchantmentSelectionEvent.Option> eventOptions = new ArrayList<>();
        eventOptions.add(new EnchantmentSelectionEvent.Option(selectedEnchantments.getSecond().get(0).stream().map(e -> (io.gomint.enchant.Enchantment) e).collect(Collectors.toList()), selectedEnchantments.getFirst()[0]));
        eventOptions.add(new EnchantmentSelectionEvent.Option(selectedEnchantments.getSecond().get(1).stream().map(e -> (io.gomint.enchant.Enchantment) e).collect(Collectors.toList()), selectedEnchantments.getFirst()[1]));
        eventOptions.add(new EnchantmentSelectionEvent.Option(selectedEnchantments.getSecond().get(2).stream().map(e -> (io.gomint.enchant.Enchantment) e).collect(Collectors.toList()), selectedEnchantments.getFirst()[2]));

        EnchantmentSelectionEvent event = this.getBlock().world().server().pluginManager().callEvent(new EnchantmentSelectionEvent(
            player,
            eventOptions
        ));

        List<PacketPlayerEnchantmentOptions.EnchantmentOption> options = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EnchantmentSelectionEvent.Option option = event.enchantmentOptions().get(i);
            int cost = option.minimumRequirement();
            List<io.gomint.enchant.Enchantment> enchantments = option.enchantments();
            List<PacketPlayerEnchantmentOptions.Enchantment> packetEnchantments = new ArrayList<>();

            for (io.gomint.enchant.Enchantment enchantment : enchantments) {
                short id = this.block.world().server().enchantments().idOf(enchantment.getClass());
                PacketPlayerEnchantmentOptions.Enchantment ench = new PacketPlayerEnchantmentOptions.Enchantment((byte) id, (byte) enchantment.level());
                packetEnchantments.add(ench);
            }

            var activation = new PacketPlayerEnchantmentOptions.EnchantmentActivation(packetEnchantments);
            var item = new PacketPlayerEnchantmentOptions.EnchantmentItem(i, Arrays.asList(activation, activation, activation));
            var packetOption = new PacketPlayerEnchantmentOptions.EnchantmentOption(cost, item, getRandomString(8, random), i);
            options.add(packetOption);
        }

        PacketPlayerEnchantmentOptions packetPlayerEnchantmentOptions = new PacketPlayerEnchantmentOptions();
        packetPlayerEnchantmentOptions.setOptions(options);

        player.connection().addToSendQueue(packetPlayerEnchantmentOptions);
    }

    private String getRandomString(int amountOfChars, FastRandom random) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amountOfChars; i++) {
            builder.append(alphabetArray[random.nextInt(alphabetArray.length - 1)]);
        }

        return builder.toString();
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "EnchantTable");
    }
}
