/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import io.gomint.server.util.PerformanceHacks;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ObjectConstructionFactory implements ConstructionFactory {

    private final ConstructionFactory factory;

    public ObjectConstructionFactory( Class<?> clazz ) {
        if ( PerformanceHacks.isUnsafeEnabled() ) {
            // I simply assume that when we have sun unsafe we have the rest of the sun "API"
            this.factory = new ConstructorAccessFactory( clazz );
        } else {
            this.factory = new ReflectionAccessFactory( clazz );
        }
    }

    @Override
    public Object newInstance() {
        return this.factory.newInstance();
    }

}
