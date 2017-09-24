/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import com.google.common.base.Preconditions;
import io.gomint.event.CancellableEvent;
import io.gomint.event.Event;

import java.util.*;

/**
 * This list sorts and triggers all EventHandlerMethods which have been registered for a event.
 *
 * @author BlackyPaw
 * @version 1.0
 */
class EventHandlerList {

    // If the handler list is dirty we need to sort it by the event handler priorities:
    private boolean dirty;
    private Map<String, EventHandlerMethod> handlers = new HashMap<>();
    private List<EventHandlerMethod> sortedHandlerList = new ArrayList<>();

    /**
     * Construct a new EventHandlerList
     */
    EventHandlerList() {

    }

    /**
     * Add a new handler. This marks the whole list dirty and it gets sorted when the next event arrives.
     *
     * @param key     The key which should be used to store this handler
     * @param handler The handler which should be added
     */
    void addHandler( String key, EventHandlerMethod handler ) {
        Preconditions.checkArgument( !this.handlers.containsKey( key ), "EventHandler can't be registered twice" );

        this.handlers.put( key, handler );
        this.sortedHandlerList.add( handler );
        this.dirty = true;
    }

    /**
     * Remove a handler from the list. This does not dirty the list.
     *
     * @param key The key which has been used to store this handler
     */
    void removeHandler( String key ) {
        EventHandlerMethod method = this.handlers.remove( key );
        if ( method != null ) {
            this.sortedHandlerList.remove( method );
        }
    }

    /**
     * Iterate over all EventHandler Methods and sort them if needed. This also controls when a Event got cancelled
     * that it does not get fired for Handlers which does not want it.
     *
     * @param event The event which gets passed to all handlers
     */
    void triggerEvent( Event event ) {
        if ( this.dirty ) {
            Collections.sort( this.sortedHandlerList );
        }

        if ( event instanceof CancellableEvent ) {
            CancellableEvent cancelableEvent = (CancellableEvent) event;
            for ( EventHandlerMethod handler : this.sortedHandlerList ) {
                if ( cancelableEvent.isCancelled() && handler.ignoreCancelled() ) {
                    continue;
                }

                handler.invoke( event );
            }
        } else {
            for ( EventHandlerMethod handler : this.sortedHandlerList ) {
                handler.invoke( event );
            }
        }
    }

}
