package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
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
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        // Open the chest inventory for the entity
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            EnchantmentTableInventory inv = new EnchantmentTableInventory(this.items, this);
            player.openInventory(inv);

            // Select and send enchantment options
            inv.addObserver(slotItem -> {
                if (slotItem.getFirst() == 0 && slotItem.getSecond() != null) {
                    this.selectAndSendEnchantments(player, slotItem.getSecond());
                }
            });
        }
    }

    private void selectAndSendEnchantments(EntityPlayer player, ItemStack slotItem) {
        FastRandom random = new FastRandom(player.getEnchantmentSeed());
        Pair<int[], List<List<Enchantment>>> selectedEnchantments = EnchantmentSelector.getEnchantments(this.getBlock().getWorld().getServer().getEnchantments(),
            random, this.getBlock().getLocation(), (io.gomint.server.inventory.item.ItemStack) slotItem);

        if (selectedEnchantments == null) {
            return;
        }

        List<PacketPlayerEnchantmentOptions.EnchantmentOption> options = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int cost = selectedEnchantments.getFirst()[i];
            List<Enchantment> enchantments = selectedEnchantments.getSecond().get(i);
            List<PacketPlayerEnchantmentOptions.Enchantment> packetEnchantments = new ArrayList<>();

            for (Enchantment enchantment : enchantments) {
                short id = this.block.getWorld().getServer().getEnchantments().getId(enchantment.getClass());
                PacketPlayerEnchantmentOptions.Enchantment ench = new PacketPlayerEnchantmentOptions.Enchantment((byte) id, (byte) enchantment.getLevel());
                packetEnchantments.add(ench);
            }

            PacketPlayerEnchantmentOptions.EnchantmentActivation activation = new PacketPlayerEnchantmentOptions.EnchantmentActivation(packetEnchantments);
            PacketPlayerEnchantmentOptions.EnchantmentItem item = new PacketPlayerEnchantmentOptions.EnchantmentItem(i, Arrays.asList(activation, activation, activation));
            PacketPlayerEnchantmentOptions.EnchantmentOption option = new PacketPlayerEnchantmentOptions.EnchantmentOption(cost, item, getRandomString(8, random), i);
            options.add(option);
        }

        PacketPlayerEnchantmentOptions packetPlayerEnchantmentOptions = new PacketPlayerEnchantmentOptions();
        packetPlayerEnchantmentOptions.setOptions(options);

        player.getConnection().addToSendQueue(packetPlayerEnchantmentOptions);
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
