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
import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class EventManager {

    // Fair-Mode reentrant R/W lock used for synchronizing access to the event handler map:
    private final ReentrantReadWriteLock collectionLock = new ReentrantReadWriteLock( true );
    private final ReentrantLock queueLock = new ReentrantLock( true );

    // All event handlers that have been registered
    private final IntObjMap<EventHandlerList> eventHandlers = HashIntObjMaps.newMutableMap();

    // Not actually a queue, but used for the sake of cache efficiency provided by array lists over
    // actual queue implementations such as LinkedList
    private final List<Event> eventQueue = new ArrayList<>();

    /**
     * Constructs a new EventManager.
     */
    public EventManager() {

    }

    /**
     * Queues the event. It will be triggered as soon as the event manager flushes its internal event queue.
     *
     * @param event The event to be enqueued
     */
    public void queueEvent( Event event ) {
        this.queueLock.lock();
        try {
            this.eventQueue.add( event );
        } finally {
            this.queueLock.unlock();
        }
    }

    /**
     * Triggers the event. It will be dispatched to all interested listeners immediately.
     *
     * @param event The event to be triggered
     */
    public void triggerEvent( Event event ) {
        this.collectionLock.readLock().lock();
        try {
            this.triggerEvent0( event );
        } finally {
            this.collectionLock.readLock().unlock();
        }
    }

    /**
     * Flushes the internal event queue of the EventManager by triggering all enqueued events at once.
     */
    public void flush() {
        this.collectionLock.readLock().lock();
        this.queueLock.lock();
        try {
            for ( Event event : this.eventQueue ) {
                this.triggerEvent0( event );
            }

            this.eventQueue.clear();
        } finally {
            this.queueLock.unlock();
            this.collectionLock.writeLock().unlock();
        }
    }

    /**
     * Registers all event handler methods found on the specified listener.
     *
     * @param listener The listener to register
     * @param <T>      The generic type of the listener
     */
    public <T extends EventListener> void registerListener( T listener ) {
        Class<? extends EventListener> listenerClass = listener.getClass();
        for ( Method listenerMethod : listenerClass.getDeclaredMethods() ) {
            if ( !listenerMethod.isAnnotationPresent( EventHandler.class ) ||
                    listenerMethod.getParameterCount() != 1 ||
                    !Event.class.isAssignableFrom( listenerMethod.getParameterTypes()[0] ) ||
                    Modifier.isStatic( listenerMethod.getModifiers() ) ) {
                continue;
            }

            listenerMethod.setAccessible( true );
            this.registerListener0( listener, listenerMethod );
        }
    }

    /**
     * Registers all event handler methods found on the specified listener.
     *
     * @param listener The listener to register
     * @param <T>      The generic type of the listener
     */
    public <T extends EventListener> void unregisterListener( T listener ) {
        Class<? extends EventListener> listenerClass = listener.getClass();
        for ( Method listenerMethod : listenerClass.getDeclaredMethods() ) {
            if ( !listenerMethod.isAnnotationPresent( EventHandler.class ) ||
                    listenerMethod.getParameterCount() != 1 ||
                    !Event.class.isAssignableFrom( listenerMethod.getParameterTypes()[0] ) ||
                    Modifier.isStatic( listenerMethod.getModifiers() ) ) {
                continue;
            }

            this.unregisterListener0( listener, listenerMethod );
        }
    }

    private void triggerEvent0( Event event ) {
        // Assume we already acquired a readLock:
        int eventHash = event.getClass().hashCode();
        EventHandlerList eventHandlerList = this.eventHandlers.get( eventHash );
        if ( eventHandlerList == null ) {
            return;
        }

        eventHandlerList.triggerEvent( event );
    }

    private <T extends EventListener> void registerListener0( T listener, Method listenerMethod ) {
        this.collectionLock.writeLock().lock();
        try {
            int eventHash = listenerMethod.getParameterTypes()[0].hashCode();
            EventHandler annotation = listenerMethod.getAnnotation( EventHandler.class );
            EventHandlerList eventHandlerList = this.eventHandlers.get( eventHash );
            if ( eventHandlerList == null ) {
                eventHandlerList = new EventHandlerList();
                this.eventHandlers.put( eventHash, eventHandlerList );
            }

            eventHandlerList.addHandler( listener.getClass().getName() + "#" + listenerMethod.getName() + "_" + eventHash, new EventHandlerMethod( listener, listenerMethod, annotation ) );
        } finally {
            this.collectionLock.writeLock().unlock();
        }
    }

    private <T extends EventListener> void unregisterListener0( T listener, Method listenerMethod ) {
        this.collectionLock.writeLock().lock();
        try {
            int eventHash = listenerMethod.getParameterTypes()[0].hashCode();
            EventHandlerList eventHandlerList = this.eventHandlers.get( eventHash );
            if ( eventHandlerList == null ) {
                return;
            }

            eventHandlerList.removeHandler( listener.getClass().getName() + "#" + listenerMethod.getName() + "_" + eventHash );
        } finally {
            this.collectionLock.writeLock().unlock();
        }
    }

}
