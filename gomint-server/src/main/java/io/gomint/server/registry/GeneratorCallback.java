package io.gomint.server.registry;

/**
 * @param <T> type of generator
 * @author geNAZt
 * @version 1.0
 */
public interface GeneratorCallback<T> {

    /**
     * Generate a ASM generator for the given id and class
     *
     * @param clazz for which we need a ASM generator
     * @return generator for the given class
     */
    T generate( Class<?> clazz );

}
