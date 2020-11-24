/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ReflectionAccessFactory<T> implements ConstructionFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger( ReflectionAccessFactory.class );

    private Constructor<T> constructor;

    public ReflectionAccessFactory( Class<T> clazz ) {
        try {
            this.constructor = clazz.getConstructor();
            this.constructor.setAccessible( true );
        } catch ( NoSuchMethodException e ) {
            LOGGER.error( "Can't construct access factory for {}", clazz.getName(), e );
        }
    }

    @Override
    public T newInstance() {
        try {
            return this.constructor.newInstance();
        } catch ( InstantiationException | InvocationTargetException | IllegalAccessException e ) {
            LOGGER.error( "Can't construct new object", e );
        }

        return null;
    }

}
