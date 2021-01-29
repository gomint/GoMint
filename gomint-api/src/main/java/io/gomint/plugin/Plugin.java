/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.event.EventListener;
import io.gomint.scheduler.Scheduler;
import io.gomint.world.World;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;

/**
 * Base class for any plugin to be created for use with the GoMint system. Below you will find an in-depth
 * explanation of the GoMint plugin system.
 *
 * When creating a plugin you should take care that the plugin implementation class directly inherits from
 * the base Plugin class. It is important that the implementation will not be considered a valid plugin
 * by GoNets plugin loader in case it only indirectly inherits from plugin. There are also several
 * annotation which are required on any plugin and which you need to specify under all circumstances so that
 * your plugin will actually be loaded. Namely, these are:
 * <ul>
 * <li>@Name( "NameOfPlugin" ) - The name of your plugin; for internal use only.</li>
 * <li>@Version( major = 1, minor = 0 ) - The current version of your plugin; for incremental updates.</li>
 * </ul>
 * Furthermore you may specify additional annotation as needed, for example a @Startup annotation to specify
 * upmost priority in loading the specified plugin.
 *
 * Any plugin will live through up to 4 different stages which will be explained in detail below. Please note
 * that only the very first stage is obligatory (assumed you have been able to put your - correct - implementation,
 * as shown above, into the right folder of the application), that is the DETECTION stage.
 * <ol>
 * <li>DETECTION - In this stage only the bare meta-information provided by annotations is actually loaded.</li>
 * <li>INSTALLATION - In this stage the classes of the plugin are loaded and the startup hook will be invoked.</li>
 * <li>RUNTIME - The main stage your plugin will (probably) be in most of the time (expect your plugin is full of shit nobody wants to install).</li>
 * <li>UNINSTALLATION - In this stage your plugin will be removed and - as the name obviously indicates - uninstalled.</li>
 * </ol>
 *
 * @author BlackyPaw
 * @version 1.0
 * @stability 3
 */
public class Plugin {

    /**
     * The plugin manager which controls the plugin's lifecycle. This is used to allow
     * plugins to determine their end on their own by disabling them. As this operation
     * needs to be done by the plugin manager each plugin gets a reference to it.
     */
    PluginManager pluginManager;

    /**
     * The name of the plugin as provided by annotation data.
     */
    String name;

    /**
     * The version of the plugin as provided by annotation data.
     */
    PluginVersion version;

    /**
     * Logger for this Plugin
     */
    Logger logger;

    /**
     * Scheduler for this Plugin
     */
    Scheduler scheduler;

    /**
     * Server which is running
     */
    GoMint server;

    /**
     * Configured active worlds
     */
    @Nullable
    ConcurrentHashMap.KeySetView<String, Boolean> activeWorlds;

    /**
     * List which contains all listeners this plugin has registered
     */
    private final List<EventListener> listeners = new ArrayList<>();

    /**
     * List which contains all registered commands for this plugin
     */
    private final List<Command> commands = new ArrayList<>();

    /**
     * Implementation hook. This hook is invoked once the plugin is being installed.
     */
    public void onStartup() {
    }

    /**
     * Implementation hook. This hook is invoked once the plugin enters the runtime stage.
     */
    public void onInstall() {
    }

    /**
     * Implementation hook. This hook is invoked once the plugin is being uninstalled.
     */
    public void onUninstall() {
    }

    /**
     * Check plugin installation status.
     *
     * @return Plugin installation status
     */
    public boolean isInstalled() {
        return this.pluginManager.isPluginInstalled( this.name );
    }

    /**
     * Register a new command to this plugin
     *
     * @param command which should be registered
     */
    public Plugin registerCommand( Command command ) {
        this.pluginManager.registerCommand( this, command );
        return this;
    }

    /**
     * Register a new listener to this plugin
     *
     * @param listener The listener which should be registered
     */
    public Plugin registerListener( EventListener listener ) {
        this.pluginManager.registerListener( this, listener );
        this.listeners.add( listener );
        return this;
    }

    /**
     * Unregister a listener
     *
     * @param listener which should be unregistered
     */
    public Plugin unregisterListener( EventListener listener ) {
        if ( this.listeners.remove( listener ) ) {
            this.pluginManager.unregisterListener( this, listener );
        }

        return this;
    }

    /**
     * Disables the plugin if - and only if - the plugin is currently in the runtime stage.
     * Under all other circumstances invocation of this method will show no effect.
     */
    protected void uninstall() {
        this.pluginManager.uninstallPlugin( this );
    }

    /**
     * Get a resource from within this plugins jar or container. Care must be
     * taken to close the returned stream.
     *
     * @param name the full path name of this resource
     * @return the stream for getting this resource, or null if it does not
     * exist
     */
    public final InputStream resourceAsStream(String name ) {
        return getClass().getClassLoader().getResourceAsStream( name );
    }

    /**
     * Gets the data folder where this plugin may store arbitrary data.
     *
     * @return the data folder of this plugin
     */
    public final File dataFolder() {
        return new File( pluginManager().baseDirectory(), name() );
    }

    public PluginManager pluginManager() {
        return this.pluginManager;
    }

    public String name() {
        return this.name;
    }

    public PluginVersion version() {
        return this.version;
    }

    public Logger logger() {
        return this.logger;
    }

    public Scheduler scheduler() {
        return this.scheduler;
    }

    public GoMint server() {
        return this.server;
    }

    /**
     * Get loaded worlds (by {@linkplain World#folder()} name) set based on {@code worlds.yml} configuration file in
     * this plugin's data folder.
     * Returns {@code null} if plugin should run in all worlds.
     * Returned set is actively updated on world (un-)load (if not {@code null}).
     *
     * @return The set of worlds (by {@linkplain World#folder()} name) the given plugin should be active in. Returns
     * {@code null} if plugin should run in all worlds.
     * @see #activeInWorld(World)
     */
    @Nullable
    public ConcurrentHashMap.KeySetView<String, Boolean> activeWorlds() {
        return this.activeWorlds;
    }

    /**
     * Checks whether the plugin should be active in the given world. Based on {@code worlds.yml} configuration file in
     * this plugin's data folder.
     *
     * @param world The world to check
     * @return whether the plugin should be active in the given world
     * @see #activeWorlds()
     */
    public boolean activeInWorld(World world) {
        return this.activeWorlds == null || this.activeWorlds.contains(world.folder());
    }

}
