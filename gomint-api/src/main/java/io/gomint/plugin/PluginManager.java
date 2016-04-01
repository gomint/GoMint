/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin;

import io.gomint.command.Command;
import io.gomint.command.CommandSender;
import io.gomint.event.Event;
import io.gomint.event.EventListener;

/**
 * Loads and manages all plugins that are compatible with the GoMint API.
 *
 * @author Fabian
 * @author Digot
 * @version 1.0
 */
public interface PluginManager {
    /**
     * Disable the given plugin. This is only valid to be called from {@link Plugin#disable()}
     * @param plugin which should be disabled
     * @throws SecurityException when somebody else as the Main Class tries to disable a plugin
     */
    void disablePlugin( Plugin plugin );

    /**
     * Loads and initializes the plugins of the GoMint server
     */
    void loadPlugins();

    /**
     * Absolute path of the plugin Directory. This is used to determinate where the data folders of the Plugins
     * should reside
     *
     * @return absolute Path of the plugin folder
     */
    String getBaseDirectory();

    /**
     * Checks if the specified plugin is currently enabled.
     *
     * @param pluginName The plugin
     * @return whether the plugin is enabled or not
     */
    boolean isPluginInstalled( String pluginName );

    /**
     * Wrapper to register an {@link EventListener}
     * @param eventListener to register
     */
    void registerListener( EventListener eventListener );

    /**
     * Wrapper to register a {@link Command}
     * @param command to register
     */
    void registerCommand( Command command );

    /**
     * Wrapper to call an {@link Event}
     */
    void callEvent( Event event );

    /**
     * Wrapper to invoke a command with arguments
     */
    void executeCommand ( CommandSender sender, String name, String... args );

    /**
     * Wrapper to invoke a command without arguments
     */
    void executeCommand ( CommandSender sender, String name);
}
