package io.gomint.gui;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface FormListener<T> {

    /**
     * Attach a consumer for the response
     *
     * @param consumer which consumes the form response
     */
    void onResponse( Consumer<T> consumer );

    /**
     * Attach a consumer which gets called when the client closes the form
     * without a response
     *
     * @param consumer which consumes closing without response
     */
    void onClose( Consumer<Void> consumer );

}
