package io.gomint.server.registry;

/**
 * @param <T> type of generator
 * @author geNAZt
 * @version 1.0
 */
public interface GeneratorCallback<T, I> {

    /**
     * Generate a ASM generator for the given id and class
     *
     * @param clazz for which we need a ASM generator
     * @param id for which we generate
     * @return generator for the given class
     */
    Generator<T> generate( Class<? extends T> clazz, I id );

}
