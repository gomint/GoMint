/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketBookEdit extends Packet {

    private byte type;
    private byte inventorySlot;

    // Used for all types except 4
    private byte pageNumber;

    private String text;
    private String photoName;

    private byte swapWithPageNumber;

    private String title;
    private String author;
    private String xuid;

    /**
     * Construct a new packet
     */
    public PacketBookEdit() {
        super( Protocol.PACKET_BOOK_EDIT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.type = buffer.readByte();
        this.inventorySlot = buffer.readByte();

        switch ( this.type ) {
            case 0: // Replace content
            case 1: // Add page
                this.pageNumber = buffer.readByte();
                this.text = buffer.readString();
                this.photoName = buffer.readString();
                break;
            case 2: // Delete page
                this.pageNumber = buffer.readByte();
                break;

            case 3: // Swap pages
                this.pageNumber = buffer.readByte();
                this.swapWithPageNumber = buffer.readByte();
                break;

            case 4: // Sign book
                this.title = buffer.readString();
                this.author = buffer.readString();
                this.xuid = buffer.readString();
                break;

        }
    }

}
