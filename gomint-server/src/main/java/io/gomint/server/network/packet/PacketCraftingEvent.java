package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public byte getWindowId() {
        return windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }

    public int getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(int recipeType) {
        this.recipeType = recipeType;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public ItemStack[] getInput() {
        return input;
    }

    public void setInput(ItemStack[] input) {
        this.input = input;
    }

    public ItemStack[] getOutput() {
        return output;
    }

    public void setOutput(ItemStack[] output) {
        this.output = output;
    }
}
