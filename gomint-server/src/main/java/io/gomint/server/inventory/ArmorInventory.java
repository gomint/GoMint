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
public class ArmorInventory extends Inventory implements io.gomint.inventory.ArmorInventory {

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
    public void setHelmet( ItemStack itemStack ) {
        this.setItem( 0, itemStack );
    }

    @Override
    public void setChestplate( ItemStack itemStack ) {
        this.setItem( 1, itemStack );
    }

    @Override
    public void setLeggings( ItemStack itemStack ) {
        this.setItem( 2, itemStack );
    }

    @Override
    public void setBoots( ItemStack itemStack ) {
        this.setItem( 3, itemStack );
    }

    @Override
    public ItemStack getHelmet() {
        return this.contents[0];
    }

    @Override
    public ItemStack getChestplate() {
        return this.contents[1];
    }

    @Override
    public ItemStack getLeggings() {
        return this.contents[2];
    }

    @Override
    public ItemStack getBoots() {
        return this.contents[3];
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        if ( playerConnection.getEntity().equals( this.owner ) ) {
            PacketInventoryContent inventory = new PacketInventoryContent();
            inventory.setWindowId( WindowMagicNumbers.ARMOR_DEPRECATED );
            inventory.setItems( getContents() );
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
    public InventoryType getInventoryType() {
        return InventoryType.ARMOR;
    }

    private void sendMobArmor( PlayerConnection playerConnection ) {
        PacketMobArmorEquipment mobArmorEquipment = new PacketMobArmorEquipment();
        mobArmorEquipment.setBoots( this.contents[3] );
        mobArmorEquipment.setLeggings( this.contents[2] );
        mobArmorEquipment.setChestplate( this.contents[1] );
        mobArmorEquipment.setHelmet( this.contents[0] );
        mobArmorEquipment.setEntityId( ( (Entity) this.owner ).getEntityId() );
        playerConnection.addToSendQueue( mobArmorEquipment );
    }

    public float getTotalArmorValue() {
        float armorValue = 0;

        for ( ItemStack itemStack : this.contents ) {
            if ( itemStack instanceof ItemArmor ) {
                armorValue += ( (ItemArmor) itemStack ).getReductionValue();
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
        for (ItemStack itemStack : this.contents) {
            io.gomint.server.inventory.item.ItemStack implItem = (io.gomint.server.inventory.item.ItemStack) itemStack;
            implItem.calculateUsageAndUpdate((int) damage);
        }
    }

}
