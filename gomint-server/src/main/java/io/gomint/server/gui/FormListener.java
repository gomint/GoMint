package io.gomint.server.gui;

import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class FormListener<T> implements io.gomint.gui.FormListener<T> {

    private Consumer<T> responseConsumer = t -> { };
    private Consumer<Void> closeConsumer = aVoid -> { };

    @Override
    public void onResponse( Consumer<T> consumer ) {
        this.responseConsumer = consumer;
    }

    @Override
    public void onClose( Consumer<Void> consumer ) {
        this.closeConsumer = consumer;
    }

}
