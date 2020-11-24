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

import java.util.Map;

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
    void uninstallPlugin( Plugin plugin );

    /**
     * Absolute path of the plugin Directory. This is used to determinate where the data folders of the Plugins
     * should reside
     *
     * @return absolute Path of the plugin folder
     */
    String getBaseDirectory();

    /**
     * Get a plugin given by its name. The plugin needs to be loaded or enabled to return in here.
     *
     * @param name The name of the plugin
     * @param <T>  Type of the plugin
     * @return loaded or enabled plugin or null when the plugin was not found
     */
    <T extends Plugin> T getPlugin( String name );

    /**
     * Get the a map containing the plugins.
     *
     * @return loaded or enabled plugin or null when the plugin was not found
     */
    Map< String, Plugin > getPlugins();

    /**
     * Check plugin installation status
     *
     * @param name Plugin to check
     * @return Plugin installation status
     */
    boolean isPluginInstalled( String name );

    /**
     * Call out a event. This will give it to all handlers attached and return it once its done.
     *
     * @param event The event which should be handled
     * @param <T>   The type of event which we handle
     * @return the handled and changed event
     */
    <T extends Event> T callEvent( T event );

    /**
     * Register a new event listener for the given plugin. This only works when you call it from a plugin class.
     *
     * @param plugin   The plugin which wants to register this listener
     * @param listener The listener which we want to register
     * @throws SecurityException when somebody else except the plugin registers the listener
     */
    void registerListener( Plugin plugin, EventListener listener );

    /**
     * Unregister a listener. This listener does not get any more events after this
     *
     * @param plugin   The plugin which owns the listener
     * @param listener The listener which we want to unregister
     * @throws SecurityException when somebody else except the plugin unregisters the listener
     */
    void unregisterListener( Plugin plugin, EventListener listener );

    /**
     * Register a new command for the given plugin. This only works when you call it from a plugin class.
     *
     * @param plugin
     * @param command
     */
    void registerCommand( Plugin plugin, Command command );

}
