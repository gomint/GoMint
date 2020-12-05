package io.gomint.server.entity.tileentity;

import io.gomint.enchant.EnchantmentFortune;
import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketPlayerEnchantmentOptions;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "EnchantTable")
public class EnchantTableTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public EnchantTableTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) entity;
            player.setEnchantmentInputInventory( new EnchantmentTableInventory( this.items, this ) );
            player.openInventory( player.getEnchantmentInputInventory() );

            // Select and send enchantment options
            this.selectAndSendEnchantments(player);
        }
    }

    private void selectAndSendEnchantments(EntityPlayer player) {
        short id = this.block.getWorld().getServer().getEnchantments().getId(EnchantmentFortune.class);

        // We want to send a range of 3 options for slot 0
        PacketPlayerEnchantmentOptions.Enchantment ench = new PacketPlayerEnchantmentOptions.Enchantment((byte) id, (byte)1);
        PacketPlayerEnchantmentOptions.EnchantmentActivation activation = new PacketPlayerEnchantmentOptions.EnchantmentActivation(Collections.singletonList(ench));
        PacketPlayerEnchantmentOptions.EnchantmentItem item = new PacketPlayerEnchantmentOptions.EnchantmentItem(0, Arrays.asList(activation, activation, activation));
        PacketPlayerEnchantmentOptions.EnchantmentOption option = new PacketPlayerEnchantmentOptions.EnchantmentOption(2, item, "TEST-1", 0);
        PacketPlayerEnchantmentOptions.EnchantmentOption option2 = new PacketPlayerEnchantmentOptions.EnchantmentOption(37, item, "TEST-2", 5);
        PacketPlayerEnchantmentOptions options = new PacketPlayerEnchantmentOptions();
        options.setOptions(Arrays.asList(option, option2));

        player.getConnection().addToSendQueue(options);
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "EnchantTable" );
    }
}
