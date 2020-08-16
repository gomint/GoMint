package io.gomint.server.registry;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Generator<E> {

    E generate( Object ... init );

}
