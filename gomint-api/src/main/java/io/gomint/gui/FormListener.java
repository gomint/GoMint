package io.gomint.gui;

import java.util.function.Consumer;

/**
 * @param <R> type of return value from the response
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface FormListener<R> {

    /**
     * Attach a consumer for the response
     *
     * @param consumer which consumes the form response
     * @return this object for chaining
     */
    FormListener<R> onResponse( Consumer<R> consumer );

    /**
     * Attach a consumer which gets called when the client closes the form
     * without a response
     *
     * @param consumer which consumes closing without response
     * @return this object for chaining
     */
    FormListener<R>  onClose( Consumer<Void> consumer );

}
