package io.gomint.server.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.item.ItemArmor;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.packet.PacketMobArmorEquipment;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ArmorInventory extends Inventory<io.gomint.inventory.ArmorInventory> implements io.gomint.inventory.ArmorInventory {

    /**
     * Construct a inventory for holding armor items
     *
     * @param owner of this inventory
     * @param items factory
     */
    public ArmorInventory(Items items, InventoryHolder owner ) {
        super( items, owner, 4 );
    }

    @Override
    public io.gomint.inventory.ArmorInventory helmet(ItemStack<?> itemStack ) {
        this.item( 0, itemStack );
        return this;
    }

    @Override
    public io.gomint.inventory.ArmorInventory chestplate(ItemStack<?> itemStack ) {
        this.item( 1, itemStack );
        return this;
    }

    @Override
    public io.gomint.inventory.ArmorInventory leggings(ItemStack<?> itemStack ) {
        this.item( 2, itemStack );
        return this;
    }

    @Override
    public io.gomint.inventory.ArmorInventory boots(ItemStack<?> itemStack ) {
        this.item( 3, itemStack );
        return this;
    }

    @Override
    public ItemStack<?> helmet() {
        return this.contents[0];
    }

    @Override
    public ItemStack<?> chestplate() {
        return this.contents[1];
    }

    @Override
    public ItemStack<?> leggings() {
        return this.contents[2];
    }

    @Override
    public ItemStack<?> boots() {
        return this.contents[3];
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        if ( playerConnection.getEntity().equals( this.owner ) ) {
            PacketInventoryContent inventory = new PacketInventoryContent();
            inventory.setWindowId( WindowMagicNumbers.ARMOR_DEPRECATED );
            inventory.setItems( contents() );
            playerConnection.addToSendQueue( inventory );
        } else {
            this.sendMobArmor( playerConnection );
        }
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        if ( playerConnection.getEntity().equals( this.owner ) ) {
            PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
            setSlot.setSlot( slot );
            setSlot.setWindowId( WindowMagicNumbers.ARMOR_DEPRECATED );
            setSlot.setItemStack( this.contents[slot] );
            playerConnection.addToSendQueue( setSlot );
        } else {
            this.sendMobArmor( playerConnection );
        }
    }

    @Override
    public InventoryType inventoryType() {
        return InventoryType.ARMOR;
    }

    private void sendMobArmor( PlayerConnection playerConnection ) {
        PacketMobArmorEquipment mobArmorEquipment = new PacketMobArmorEquipment();
        mobArmorEquipment.setBoots( this.contents[3] );
        mobArmorEquipment.setLeggings( this.contents[2] );
        mobArmorEquipment.setChestplate( this.contents[1] );
        mobArmorEquipment.setHelmet( this.contents[0] );
        mobArmorEquipment.setEntityId( ( (Entity<?>) this.owner ).id() );
        playerConnection.addToSendQueue( mobArmorEquipment );
    }

    public float getTotalArmorValue() {
        float armorValue = 0;

        for ( ItemStack<?> itemStack : this.contents ) {
            if ( itemStack instanceof ItemArmor ) {
                armorValue += ( (ItemArmor<?>) itemStack ).getReductionValue();
            }
        }

        return armorValue;
    }

    public void damageEvenly( float damage ) {
        // Only damage for 1/4th of the total damage dealt
        damage = damage / 4.0F;

        // At least damage them for one damage
        if ( damage < 1.0F ) {
            damage = 1.0F;
        }

        // TODO: Modifier for shields?

        // Apply damage to all items
        for (ItemStack<?> itemStack : this.contents) {
            io.gomint.server.inventory.item.ItemStack<?> implItem = (io.gomint.server.inventory.item.ItemStack<?>) itemStack;
            implItem.calculateUsageAndUpdate((int) damage);
        }
    }

}
