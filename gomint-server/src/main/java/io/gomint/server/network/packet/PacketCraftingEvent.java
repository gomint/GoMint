package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.Protocol;
import lombok.Data;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketCraftingEvent extends Packet {

    private byte windowId;
    private int recipeType;
    private UUID recipeId;
    private ItemStack[] input;
    private ItemStack[] output;

    public PacketCraftingEvent() {
        super( Protocol.PACKET_CRAFTING_EVENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readByte();
        this.recipeType = buffer.readSignedVarInt();
        this.recipeId = buffer.readUUID();
        this.input = readItemStacks( buffer );
        this.output = readItemStacks( buffer );
    }

}
