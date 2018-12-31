/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ObjectConstructionFactory implements ConstructionFactory {

    private final ConstructionFactory factory;

    public ObjectConstructionFactory(Class<?> clazz, Class... arguments) {
        this.factory = new ReflectionAccessFactory(clazz, arguments);
    }

    @Override
    public Object newInstance(Object... init) {
        return this.factory.newInstance(init);
    }

}
