/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import io.gomint.event.Event;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import lombok.EqualsAndHashCode;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false )
class EventHandlerMethod implements Comparable<EventHandlerMethod> {

	private final EventListener instance;
	private final MethodHandle  method;
	private final EventHandler  annotation;

    /**
     * Construct a new data holder for a EventHandler.
     *
     * @param instance The instance of the EventHandler which should be used to invoke the EventHandler Method
     * @param method The method which should be invoked when the event arrives
     * @param annotation The annotation which holds additional information about this EventHandler Method
     */
	public EventHandlerMethod( final EventListener instance, final MethodHandle method, final EventHandler annotation ) {
		this.instance = instance;
		this.method = method;
		this.annotation = annotation;
	}

    /**
     * Invoke this Eventhandler.
     *
     * @param event The event to invoke
     */
	public void invoke( Event event ) {
		try {
			this.method.invoke( this.instance, event );
		} catch ( Throwable cause ) {
			cause.printStackTrace();
		}
	}

    /**
     * Returns true when this EventHandler accepts cancelled events
     *
     * @return true when it wants to accept events when cancelled, false if not
     */
	public boolean ignoreCancelled() {
		return this.annotation.ignoreCancelled();
	}

	@Override
	public int compareTo( EventHandlerMethod o ) {
		return ( Byte.compare( this.annotation.priority().getValue(), o.annotation.priority().getValue() ) );
	}

}
