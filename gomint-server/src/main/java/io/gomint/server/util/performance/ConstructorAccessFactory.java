/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.ConstructorAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ConstructorAccessFactory implements ConstructionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger( ConstructorAccessFactory.class );

    private ConstructorAccessor constructorAccessor;

    public ConstructorAccessFactory( Class<?> clazz ) {
        try {
            this.constructorAccessor = ReflectionFactory.getReflectionFactory().newConstructorAccessor( clazz.getConstructor() );
        } catch ( NoSuchMethodException e ) {
            LOGGER.error( "Can't construct access factory for {}", clazz.getName(), e );
        }
    }

    @Override
    public Object newInstance() {
        try {
            return this.constructorAccessor.newInstance( null );
        } catch ( InstantiationException | InvocationTargetException e ) {
            LOGGER.error( "Can't construct new object", e );
        }

        return null;
    }

}
