package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobArmorEquipment extends Packet {

    private long entityId;
    private ItemStack<?> helmet;
    private ItemStack<?> chestplate;
    private ItemStack<?> leggings;
    private ItemStack<?> boots;

    public PacketMobArmorEquipment() {
        super( Protocol.PACKET_MOB_ARMOR_EQUIPMENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        writeItemStack( this.helmet, buffer );
        writeItemStack( this.chestplate, buffer );
        writeItemStack( this.leggings, buffer );
        writeItemStack( this.boots, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.helmet = readItemStack( buffer );
        this.chestplate = readItemStack( buffer );
        this.leggings = readItemStack( buffer );
        this.boots = readItemStack( buffer );
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public ItemStack<?> getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack<?> helmet) {
        this.helmet = helmet;
    }

    public ItemStack<?> getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack<?> chestplate) {
        this.chestplate = chestplate;
    }

    public ItemStack<?> getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack<?> leggings) {
        this.leggings = leggings;
    }

    public ItemStack<?> getBoots() {
        return boots;
    }

    public void setBoots(ItemStack<?> boots) {
        this.boots = boots;
    }

}
