/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ObjectConstructionFactory<T> implements ConstructionFactory<T> {

    private final ConstructionFactory<T> factory;

    public ObjectConstructionFactory(Class<T> clazz) {
        this.factory = new ReflectionAccessFactory<T>(clazz);
    }

    @Override
    public T newInstance() {
        return this.factory.newInstance();
    }

}
