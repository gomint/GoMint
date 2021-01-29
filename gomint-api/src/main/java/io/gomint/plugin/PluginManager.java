/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin;

import io.gomint.command.Command;
import io.gomint.event.Event;
import io.gomint.event.EventListener;
import io.gomint.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface PluginManager {

    /**
     * Disable the given plugin. This is only valid to be called from {@link Plugin#uninstall()}
     *
     * @param plugin which should be disabled
     * @throws SecurityException when somebody else as the Main Class tries to disable a plugin
     */
    PluginManager uninstallPlugin(Plugin plugin);

    /**
     * Absolute path of the plugin Directory. This is used to determinate where the data folders of the Plugins
     * should reside
     *
     * @return absolute Path of the plugin folder
     */
    String baseDirectory();

    /**
     * Get a plugin given by its name. The plugin needs to be loaded or enabled to return in here.
     *
     * @param name The name of the plugin
     * @param <T>  Type of the plugin
     * @return loaded or enabled plugin or null when the plugin was not found
     */
    <T extends Plugin> T plugin(String name);

    /**
     * Get the a map containing the plugins.
     *
     * @return loaded or enabled plugin or null when the plugin was not found
     */
    Map<String, Plugin> plugins();

    /**
     * Check plugin installation status
     *
     * @param name Plugin to check
     * @return Plugin installation status
     */
    boolean isPluginInstalled(String name);

    /**
     * Call out a event. This will give it to all handlers attached and return it once its done.
     *
     * @param event The event which should be handled
     * @param <T>   The type of event which we handle
     * @return the handled and changed event
     */
    <T extends Event> T callEvent(T event);

    /**
     * Register a new event listener for the given plugin. This only works when you call it from a plugin class.
     *
     * The listener will be informaed of all events regardless of the world they take place in.
     *
     * @param plugin   The plugin which wants to register this listener
     * @param listener The listener which we want to register
     * @throws SecurityException when somebody else except the plugin registers the listener
     * @see #registerListener(Plugin, EventListener, ConcurrentHashMap.KeySetView)
     * @since 2.0
     */
    PluginManager registerListener(Plugin plugin, EventListener listener);

    /**
     * Register a new event listener for the given plugin. This only works when you call it from a plugin class.
     *
     * Events implementing {@linkplain io.gomint.event.interfaces.WorldEvent WorldEvent} will be filtered for this
     * listener, it will only be called for events taking place in worlds which {@linkplain World#folder() folder name}
     * is present in the {@code worlds} collection.
     *
     * @param plugin   The plugin which wants to register this listener
     * @param listener The listener which we want to register
     * @param worlds   A whitelist of world {@linkplain World#folder() folder name}. Empty collection or {@code null}
     *                 means all worlds.
     * @throws SecurityException when somebody else except the plugin registers the listener
     * @see #registerListener(Plugin, EventListener)
     * @since 2.0
     */
    PluginManager registerListener(Plugin plugin, EventListener listener, ConcurrentHashMap.KeySetView<String, Boolean> worlds);

    /**
     * Unregister a listener. This listener does not get any more events after this
     *
     * @param plugin   The plugin which owns the listener
     * @param listener The listener which we want to unregister
     * @throws SecurityException when somebody else except the plugin unregisters the listener
     */
    PluginManager unregisterListener(Plugin plugin, EventListener listener);

    /**
     * Register a new command for the given plugin. This only works when you call it from a plugin class.
     *
     * @param plugin  The plugin which wants to register this command
     * @param command The command to register
     */
    PluginManager registerCommand(Plugin plugin, Command command);

}
