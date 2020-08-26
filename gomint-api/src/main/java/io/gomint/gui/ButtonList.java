package io.gomint.gui;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ButtonList extends Form<String> {

    /**
     * Create a new button list
     *
     * @param title of the button list
     * @return fresh button list
     */
    static ButtonList create( String title ) {
        return GoMint.instance().createButtonList( title );
    }

    /**
     * Set the text content which will be displayed above the button list
     *
     * @param content for display
     * @return instance for chaining
     */
    ButtonList setContent( String content );

    /**
     * Add a new simple button
     *
     * @param id   of the button ( important for the response )
     * @param text for display
     */
    ButtonList addButton( String id, String text );

    /**
     * Add a new image button
     *
     * @param id        of the button ( important for the response )
     * @param text      for display
     * @param imagePath from where the client should load the image (can be http / https)
     */
    ButtonList addImageButton( String id, String text, String imagePath );




}
