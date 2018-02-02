/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import io.gomint.event.Event;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import javassist.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false )
@ToString( of = { "instance" } )
class EventHandlerMethod implements Comparable<EventHandlerMethod> {

    private static final Logger LOGGER = LoggerFactory.getLogger( EventHandlerMethod.class );
    private static final AtomicLong PROXY_COUNT = new AtomicLong( 0 );

    private final EventHandler annotation;
    private EventProxy proxy;

    // For toString reference
    private final EventListener instance;

    /**
     * Construct a new data holder for a EventHandler.
     *
     * @param instance   The instance of the EventHandler which should be used to invoke the EventHandler Method
     * @param method     The method which should be invoked when the event arrives
     * @param annotation The annotation which holds additional information about this EventHandler Method
     */
    EventHandlerMethod( final EventListener instance, final Method method, final EventHandler annotation ) {
        this.annotation = annotation;
        this.instance = instance;

        // Build up proxy
        try {
            // Prepare class pool for this plugin
            ClassPool pool = new ClassPool( ClassPool.getDefault() );
            pool.appendClassPath( new LoaderClassPath( instance.getClass().getClassLoader() ) );
            pool.appendClassPath( new LoaderClassPath( method.getParameterTypes()[0].getClassLoader() ) );

            CtClass ctClass = pool.makeClass( "io.gomint.server.event.Proxy" + PROXY_COUNT.incrementAndGet() );
            ctClass.addInterface( pool.get( "io.gomint.server.event.EventProxy" ) );
            ctClass.addField( CtField.make( "public " + instance.getClass().getName() + " obj;", ctClass ) );
            ctClass.addMethod( CtMethod.make( "public void call( io.gomint.event.Event e ) { obj." + method.getName() + "( (" + method.getParameterTypes()[0].getName() + ") e ); }", ctClass ) );

            this.proxy = (EventProxy) ctClass.toClass( instance.getClass().getClassLoader(), null ).newInstance();
            this.proxy.getClass().getDeclaredField( "obj" ).set( this.proxy, instance );
        } catch ( Exception e ) {
            LOGGER.error( "Could not construct new proxy for " + method.toString(), e );
        }
    }

    /**
     * Invoke this Event handler.
     *
     * @param event Event which should be handled in this handler
     */
    public void invoke( Event event ) {
        try {
            this.proxy.call( event );
        } catch ( Throwable cause ) {
            LOGGER.warn( "Event handler has thrown a exception: ", cause );
        }
    }

    /**
     * Returns true when this EventHandler accepts cancelled events
     *
     * @return true when it wants to accept events when cancelled, false if not
     */
    boolean ignoreCancelled() {
        return this.annotation.ignoreCancelled();
    }

    @Override
    public int compareTo( EventHandlerMethod o ) {
        return ( Byte.compare( this.annotation.priority().getValue(), o.annotation.priority().getValue() ) );
    }

}
