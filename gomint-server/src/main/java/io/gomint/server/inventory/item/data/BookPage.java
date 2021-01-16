/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.data;

import io.gomint.server.inventory.item.ItemWritableBook;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BookPage implements io.gomint.inventory.item.data.BookPage {

    private final ItemWritableBook book;
    private final int index;
    private String content;

    public BookPage(ItemWritableBook book, int index, String content) {
        this.book = book;
        this.index = index;
        this.content = content;
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public BookPage content(String content ) {
        this.book.pageContent( this.index, content );
        this.content = content;
        return this;
    }

}
