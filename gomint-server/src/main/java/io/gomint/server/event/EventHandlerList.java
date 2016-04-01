/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import com.google.common.base.Preconditions;
import io.gomint.event.CancelableEvent;
import io.gomint.event.Event;
import io.gomint.event.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This list sorts and triggers all EventHandlerMethods which have been registered for a event.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EventHandlerList {

	// If the handler list is dirty we need to sort it by the event handler priorities:
	private boolean dirty;
	private List<EventHandlerMethod> handlers = new ArrayList<>();

    /**
     * Construct a new EventHandlerList
     */
	public EventHandlerList() {

	}

    /**
     * Add a new handler. This marks the whole list dirty and it gets sorted when the next event arrives.
     *
     * @param handler The handler which should be added
     */
	public void addHandler( EventHandlerMethod handler ) {
        Preconditions.checkArgument( !this.handlers.contains( handler ), "EventHandler can't be registered twice" );

		this.handlers.add( handler );
		this.dirty = true;
	}

    /**
     * Remove a handler from the list. This does not dirty the list.
     *
     * @param handler The handler which should be remove from the list
     */
	public void removeHandler( EventHandlerMethod handler ) {
		this.handlers.remove( handler );
	}

    /**
     * Iterate over all EventHandler Methods and sort them if needed. This also controls when a Event got cancelled
     * that it does not get fired for Handlers which does not want it.
     *
     * @param event The event which gets passed to all handlers
     */
	public void triggerEvent( Event event ) {
		if ( this.dirty ) {
			Collections.sort( this.handlers );
		}

		if ( event instanceof CancelableEvent ) {
			CancelableEvent cancelableEvent = (CancelableEvent) event;
			for ( EventHandlerMethod handler : this.handlers ) {
				if ( cancelableEvent.isCancelled() && handler.ignoreCancelled() ) {
					continue;
				}

				handler.invoke( event );
			}
		} else {
			for ( EventHandlerMethod handler : this.handlers ) {
				handler.invoke( event );
			}
		}
	}

}
