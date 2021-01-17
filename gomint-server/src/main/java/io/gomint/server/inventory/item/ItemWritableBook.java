/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.inventory.item;

import io.gomint.GoMint;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.inventory.item.data.BookPage;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:writable_book" )
public class ItemWritableBook extends ItemStack< io.gomint.inventory.item.ItemWritableBook> implements io.gomint.inventory.item.ItemWritableBook {

    private static final String PAGE_TAG = "pages";
    private static final String PAGE_CONTENT_TAG = "text";
    private static final String PHOTO_TAG = "photoname";
    private static final String XUID_TAG = "xuid";
    private static final String GENERATION_TAG = "generation";
    private static final String TITLE_TAG = "title";
    private static final String AUTHOR_TAG = "author";

    @Override
    public ItemType itemType() {
        return ItemType.WRITABLE_BOOK;
    }

    public BookPage getPage( int index ) {
        // Check if we have a NBT tag
        if ( this.nbtData() == null ) {
            return null;
        }

        // We have a NBT tag but no pages?
        List<Object> pages = this.nbtData().getList( PAGE_TAG, false );
        if ( pages == null ) {
            return null;
        }

        // Check if we have this page
        if ( index >= pages.size() ) {
            return null;
        }

        // A page is a NBT compound which contains PAGE_CONTENT_TAG and a photoname which is not used
        NBTTagCompound pageCompound = (NBTTagCompound) pages.get( index );
        String content = pageCompound.getString( PAGE_CONTENT_TAG, null );
        if ( content == null ) {
            // Page seems corrupted -> return null
            return null;
        }

        return new BookPage( this, index, content );
    }

    public void createPage( int index ) {
        // We ignore sanity checks since we only call this from
        List<Object> pages = this.nbt().getList( PAGE_TAG, true );
        if ( pages.size() <= index ) {
            for ( int i = pages.size(); i < index + 1; i++ ) {
                NBTTagCompound emptyPage = new NBTTagCompound( "" );
                emptyPage.addValue( PHOTO_TAG, "" );
                emptyPage.addValue( PAGE_CONTENT_TAG, "" );
                pages.add( emptyPage );
            }
        }
    }

    public void pageContent(int index, String content ) {
        // We ignore sanity checks since we only call this from
        List<Object> pages = this.nbtData().getList( PAGE_TAG, false );
        if ( pages == null ) {
            return;
        }

        NBTTagCompound pageCompound = (NBTTagCompound) pages.get( index );
        pageCompound.addValue( PAGE_CONTENT_TAG, content );
    }

    public void changeContent( int index, String content ) {
        // Check if we can simply use a old NBT compound
        BookPage page = this.getPage( index );
        if ( page != null ) {
            page.content( content );
        } else {
            this.createPage( index );

            page = this.getPage( index );
            page.content( content );
        }
    }

    /**
     * Add a new blank page
     *
     * @param index of the page we want to insert
     */
    public void addBlankPage( int index ) {
        List<Object> pages = this.nbt().getList( PAGE_TAG, true );
        NBTTagCompound emptyPage = new NBTTagCompound( "" );
        emptyPage.addValue( PHOTO_TAG, "" );
        emptyPage.addValue( PAGE_CONTENT_TAG, "" );
        pages.add( index, emptyPage );
    }

    public void deletePage( int index ) {
        if ( this.nbtData() != null ) {
            List<Object> pages = this.nbtData().getList( PAGE_TAG, false );
            if ( pages != null ) {
                if ( index > 0 && index < pages.size() ) {
                    pages.remove( index );
                }
            }
        }
    }

    public ItemWrittenBook sign( String title, String author, String xuid ) {
        this.nbt().addValue( TITLE_TAG, title );
        this.nbt().addValue( AUTHOR_TAG, author );
        this.nbt().addValue( XUID_TAG, xuid );
        this.nbt().addValue( GENERATION_TAG, 0 );

        // Create final version of the book
        ItemWrittenBook writtenBook = (ItemWrittenBook) GoMint.instance().createItemStack( io.gomint.inventory.item.ItemWrittenBook.class, 1 );
        writtenBook.nbtData( this.nbtData() );
        return writtenBook;
    }

}
