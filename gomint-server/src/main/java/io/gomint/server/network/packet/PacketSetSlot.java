package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import lombok.Data;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketSetSlot extends Packet {

    private SlotMode mode;
    private short slot;
    private short hotbarSlot;
    private ItemStack stack;

    public PacketSetSlot() {
        super( (byte) 0xFF );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.mode = SlotMode.getSlotMode( buffer.readByte() );
        this.slot = buffer.readShort();
        this.hotbarSlot = buffer.readShort();
        this.stack = readItemStack( buffer );
    }

    public enum SlotMode {
        CREATIVE_SET_HOTBAR( (byte) 0x7a );

        @Getter
        private final byte mode;

        SlotMode( byte mode ) {
            this.mode = mode;
        }

        public static SlotMode getSlotMode( byte mode ) {
            for ( SlotMode slotMode : values() ) {
                if ( slotMode.getMode() == mode ) {
                    return slotMode;
                }
            }

            System.out.println( "Unknown slot mode: " + Integer.toHexString( mode & 0xFF ) );
            return null;
        }
    }

}
