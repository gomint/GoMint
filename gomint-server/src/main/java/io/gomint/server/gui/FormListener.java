package io.gomint.server.gui;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FormListener<R> implements io.gomint.gui.FormListener<R> {

    private Consumer<R> responseConsumer = r -> { };
    private Consumer<Void> closeConsumer = aVoid -> { };

    @Override
    public FormListener<R> onResponse( Consumer<R> consumer ) {
        this.responseConsumer = consumer;
        return this;
    }

    @Override
    public FormListener<R> onClose( Consumer<Void> consumer ) {
        this.closeConsumer = consumer;
        return this;
    }

    public Consumer<R> getResponseConsumer() {
        return responseConsumer;
    }

    public Consumer<Void> getCloseConsumer() {
        return closeConsumer;
    }

}
