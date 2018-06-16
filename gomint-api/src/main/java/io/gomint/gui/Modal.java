package io.gomint.gui;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Modal extends Form {

    /**
     * Create a new Modal
     *
     * @param title    of the Modal
     * @param question to ask
     * @return fresh modal
     */
    static Modal create( String title, String question ) {
        return GoMint.instance().createModal( title, question );
    }

    /**
     * Set the button text for the true button
     *
     * @param text which should be used for button labeling
     */
    void setTrueButtonText( String text );

    /**
     * Set the button text for the false button
     *
     * @param text which should be used for button labeling
     */
    void setFalseButtonText( String text );

}
