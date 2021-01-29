/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.ItemWritableBook;
import io.gomint.server.inventory.item.ItemWrittenBook;
import io.gomint.server.inventory.item.data.BookPage;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBookEdit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketBookEditHandler implements PacketHandler<PacketBookEdit> {

    @Override
    public void handle( PacketBookEdit packet, long currentTimeMillis, PlayerConnection connection ) {
        // Get the item in hand and check if its the correct item
        ItemStack<?> stack = (ItemStack<?>) connection.entity().inventory().itemInHand();
        if ( stack.itemType() != ItemType.WRITABLE_BOOK ) {
            return;
        }

        ItemWritableBook writableBook = (ItemWritableBook) stack;

        // Check mode
        switch ( packet.getType() ) {
            case 0:
                writableBook.changeContent( packet.getPageNumber(), packet.getText() );
                break;
            case 1:
                writableBook.addBlankPage( packet.getPageNumber() );
                break;
            case 2:
                writableBook.deletePage( packet.getPageNumber() );
                break;
            case 3:
                BookPage old = writableBook.getPage( packet.getPageNumber() );
                if ( old != null ) {
                    BookPage swapWith = writableBook.getPage( packet.getSwapWithPageNumber() );
                    if ( swapWith != null ) {
                        writableBook.changeContent( packet.getPageNumber(), swapWith.content() );
                        writableBook.changeContent( packet.getSwapWithPageNumber(), old.content() );
                    }
                }

                break;
            case 4:
                ItemWrittenBook writtenBook = writableBook.sign( packet.getTitle(), packet.getAuthor(), packet.getXuid() );
                connection.entity().inventory().item( connection.entity().inventory().itemInHandSlot(), writtenBook );
                break;
        }
    }

}
