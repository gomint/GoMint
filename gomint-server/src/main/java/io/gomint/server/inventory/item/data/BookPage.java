/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.data;

import io.gomint.server.inventory.item.ItemWritableBook;
import lombok.AllArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
public class BookPage implements io.gomint.inventory.item.data.BookPage {

    private final ItemWritableBook book;
    private final int index;
    private String content;

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent( String content ) {
        this.book.setPageContent( this.index, content );
        this.content = content;
    }

}
