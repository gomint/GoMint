/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import io.gomint.command.Command;
import io.gomint.event.Event;
import io.gomint.event.EventListener;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginManager;
import io.gomint.plugin.PluginVersion;
import io.gomint.plugin.StartupPriority;
import io.gomint.plugin.injection.InjectPlugin;
import io.gomint.server.GoMintServer;
import io.gomint.server.command.CommandManager;
import io.gomint.server.event.EventManager;
import io.gomint.server.scheduler.CoreScheduler;
import io.gomint.server.scheduler.PluginScheduler;
import io.gomint.server.util.CallerDetectorUtil;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SimplePluginManager implements PluginManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( SimplePluginManager.class );

    private final GoMintServer server;
    private final CoreScheduler scheduler;
    private final File pluginFolder;

    private final List<PluginMeta> detectedPlugins = new ArrayList<>();
    private final Map<String, Plugin> loadedPlugins = new LinkedHashMap<>();
    private final Map<String, Plugin> installedPlugins = new LinkedHashMap<>();
    private final Map<String, PluginMeta> metadata = new HashMap<>();

    private final EventManager eventManager = new EventManager();
    @Getter
    private final CommandManager commandManager;

    private Field loggerField;
    private Field nameField;
    private Field pluginManagerField;
    private Field versionField;
    private Field schedulerField;
    private Field serverField;
    private Field listenerListField;

    /**
     * Build a new plugin manager for detecting, loading and managing (install, uninstall) plugins
     *
     * @param server which started this manager
     */
    public SimplePluginManager( GoMintServer server ) {
        this.server = server;
        this.scheduler = new CoreScheduler( server.getExecutorService(), server.getSyncTaskManager() );
        this.pluginFolder = new File( "plugins" );
        this.commandManager = new CommandManager();

        if ( !this.pluginFolder.exists() && !this.pluginFolder.mkdirs() ) {
            LOGGER.warn( "Plugin folder was not there and could not be created, plugins will not be available" );
        }

        // Prepare the field injections
        try {
            this.loggerField = Plugin.class.getDeclaredField( "logger" );
            this.loggerField.setAccessible( true );

            this.nameField = Plugin.class.getDeclaredField( "name" );
            this.nameField.setAccessible( true );

            this.pluginManagerField = Plugin.class.getDeclaredField( "pluginManager" );
            this.pluginManagerField.setAccessible( true );

            this.versionField = Plugin.class.getDeclaredField( "version" );
            this.versionField.setAccessible( true );

            this.schedulerField = Plugin.class.getDeclaredField( "scheduler" );
            this.schedulerField.setAccessible( true );

            this.serverField = Plugin.class.getDeclaredField( "server" );
            this.serverField.setAccessible( true );

            this.listenerListField = Plugin.class.getDeclaredField( "listeners" );
            this.listenerListField.setAccessible( true );
        } catch ( NoSuchFieldException e ) {
            LOGGER.error( "Could not reflect needed access into Plugin base class", e );
        }
    }

    public void detectPlugins() {
        // Search the plugins folder for valid .jar files
        for ( File file : this.pluginFolder.listFiles( new FileFilter() {
            @Override
            public boolean accept( File pathname ) {
                return ( pathname.getAbsolutePath().endsWith( ".jar" ) );
            }
        } ) ) {
            PluginMeta metadata = getMetadata( file );
            if ( metadata != null ) {
                this.metadata.put( metadata.getName(), metadata );
                this.detectedPlugins.add( metadata );
            }
        }
    }

    /**
     * Load and startup plugins
     *
     * @param prio for which we want to load and startup plugins
     */
    public void loadPlugins( StartupPriority prio ) {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "Loading all plugins which have start priority: {}", prio.name() );
        }

        // Create a copy of the detected plugins
        for ( PluginMeta pluginMeta : new ArrayList<>( this.detectedPlugins ) ) {
            if ( !this.detectedPlugins.contains( pluginMeta ) ) {
                continue;
            }

            if ( pluginMeta.getPriority() == prio ) {
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Loading plugin {}", pluginMeta.getName() );
                }

                loadPlugin( pluginMeta );

                // Check if the plugin did shutdown the server
                if ( !this.server.isRunning() ) {
                    return;
                }
            }
        }
    }

    private void loadPlugin( PluginMeta pluginMeta ) {
        // Check for depends
        if ( pluginMeta.getDepends() != null && !pluginMeta.getDepends().isEmpty() ) {
            for ( String dependPlugin : pluginMeta.getDepends() ) {
                // If the depend plugin is already loaded, skip it
                if ( this.loadedPlugins.containsKey( dependPlugin ) ) {
                    continue;
                }

                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Searching depend for {}: {}", pluginMeta.getName(), dependPlugin );
                }

                // We need to check if the depend plugin is detected
                boolean found = false;
                for ( PluginMeta detectedPlugin : new ArrayList<>( this.detectedPlugins ) ) {
                    if ( detectedPlugin.getName().equals( dependPlugin ) ) {
                        loadPlugin( detectedPlugin );

                        // Check if the plugin did shutdown the server
                        if ( !this.server.isRunning() ) {
                            return;
                        }

                        found = true;
                        break;
                    }
                }

                if ( !found ) {
                    LOGGER.warn( "Could not load plugin {} since the depend {} could not be found", pluginMeta.getName(), dependPlugin );
                    this.metadata.remove( pluginMeta.getName() );
                    return;
                }
            }
        }

        // Check for soft depends
        if ( pluginMeta.getSoftDepends() != null && !pluginMeta.getSoftDepends().isEmpty() ) {
            for ( String dependPlugin : pluginMeta.getSoftDepends() ) {
                // If the depend plugin is already loaded, skip it
                if ( this.loadedPlugins.containsKey( dependPlugin ) ) {
                    continue;
                }

                // We need to check if the depend plugin is detected
                for ( PluginMeta detectedPlugin : new ArrayList<>( this.detectedPlugins ) ) {
                    if ( detectedPlugin.getName().equals( dependPlugin ) ) {
                        loadPlugin( pluginMeta );

                        // Check if the plugin did shutdown the server
                        if ( !this.server.isRunning() ) {
                            return;
                        }

                        break;
                    }
                }
            }
        }

        // Ok everything is fine now, load the plugin
        PluginClassloader loader = null;
        try {
            loader = new PluginClassloader( pluginMeta.getPluginFile() );
            Plugin clazz = (Plugin) constructAndInject( pluginMeta.getMainClass(), loader );
            if ( clazz == null ) {
                return;
            }

            // Reflect the logger and stuff in
            this.loggerField.set( clazz, LoggerFactory.getLogger( loader.loadClass( pluginMeta.getMainClass() ) ) );
            this.pluginManagerField.set( clazz, this );
            this.schedulerField.set( clazz, new PluginScheduler( clazz, this.scheduler ) );
            this.nameField.set( clazz, pluginMeta.getName() );
            this.versionField.set( clazz, pluginMeta.getVersion() );
            this.serverField.set( clazz, this.server );

            clazz.onStartup();

            this.loadedPlugins.put( pluginMeta.getName(), clazz );
            this.detectedPlugins.remove( pluginMeta );

            // Injection stuff
            if ( pluginMeta.getInjectionCommands() != null ) {
                for ( String commandClass : pluginMeta.getInjectionCommands() ) {
                    Object maybeCommand = constructAndInject( commandClass, loader );
                    if ( maybeCommand instanceof Command ) {
                        Command command = (Command) maybeCommand;
                        this.commandManager.register( clazz, command );
                    }
                }
            }
        } catch ( Exception e ) {
            LOGGER.warn( "Error whilst starting plugin " + pluginMeta.getName(), e );
            this.metadata.remove( pluginMeta.getName() );

            // Unload if needed
            if ( loader != null ) {
                loader.remove();
            }
        }
    }

    private Object constructAndInject( String clazz, PluginClassloader loader ) {
        try {
            Class<?> cl = loader.loadClass( clazz );

            try {
                Object built = cl.newInstance();

                // Check all fields for injection
                for ( Field field : cl.getDeclaredFields() ) {
                    // Is there @InjectPlugin present? If so, check for plugin and inject
                    if ( field.isAnnotationPresent( InjectPlugin.class ) ) {
                        String plugin = field.getAnnotation( InjectPlugin.class ).value();
                        if ( plugin.equals( "detect" ) ) {
                            // Get the fields type
                            Class<?> type = field.getType();

                            // Check loaded plugins first
                            for ( Plugin foundPlugin : this.loadedPlugins.values() ) {
                                if ( foundPlugin.getClass().equals( type ) ) {
                                    field.setAccessible( true );
                                    field.set( built, foundPlugin );
                                    break;
                                }
                            }
                        } else {
                            field.setAccessible( true );
                            field.set( built, getPlugin( plugin ) );
                        }
                    }
                }

                return built;
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        return null;
    }

    public void installPlugins() {
        for ( Map.Entry<String, Plugin> entry : this.loadedPlugins.entrySet() ) {
            Plugin plugin = entry.getValue();
            String name = entry.getKey();

            try {
                plugin.onInstall();
                this.installedPlugins.put( name, plugin );
            } catch ( Exception e ) {
                LOGGER.error( "Plugin did startup but could not be installed: " + name, e );
                this.metadata.remove( plugin.getName() );
            }

            // Check if the plugin did shutdown the server
            if ( !this.server.isRunning() ) {
                return;
            }
        }

        this.loadedPlugins.clear();
    }

    private PluginMeta getMetadata( File file ) {
        // Open the jar
        try ( JarFile jar = new JarFile( file ) ) {
            Enumeration<JarEntry> jarEntries = jar.entries();

            // It seems like the jar is empty
            if ( jarEntries == null || !jarEntries.hasMoreElements() ) {
                LOGGER.warn( "Could not load Plugin. File {} is empty", file );
                return null;
            }

            PluginMeta meta = new PluginMeta( file );

            // Try to read every file in the jar
            try {
                while ( jarEntries.hasMoreElements() ) {
                    JarEntry jarEntry = jarEntries.nextElement();

                    // When the entry is valid and ends with a .class its a java class and we need to scan it
                    if ( jarEntry != null && jarEntry.getName().endsWith( ".class" ) ) {
                        ClassFile classFile = new ClassFile( new DataInputStream( jar.getInputStream( jarEntry ) ) );

                        // Does this class extend the plugin class?
                        if ( classFile.getSuperclass().equals( "io.gomint.plugin.Plugin" ) ) {
                            String name = null;
                            PluginVersion version = null;
                            Set<String> depends = null;
                            Set<String> softDepends = null;
                            String startup = StartupPriority.STARTUP.name();

                            // Ok it did, time to parse the needed and optional annotations
                            AnnotationsAttribute visible = (AnnotationsAttribute) classFile.getAttribute( AnnotationsAttribute.visibleTag );
                            for ( Annotation annotation : visible.getAnnotations() ) {
                                switch ( annotation.getTypeName() ) {
                                    case "io.gomint.plugin.Name":   // Deprecated
                                    case "io.gomint.plugin.PluginName":
                                        name = ( (StringMemberValue) annotation.getMemberValue( "value" ) ).getValue();
                                        break;

                                    case "io.gomint.plugin.Version":
                                        version = new PluginVersion( ( (IntegerMemberValue) annotation.getMemberValue( "major" ) ).getValue(),
                                            ( (IntegerMemberValue) annotation.getMemberValue( "minor" ) ).getValue() );
                                        break;

                                    case "io.gomint.plugin.Depends":
                                        MemberValue[] dependsValues = ( (ArrayMemberValue) annotation.getMemberValue( "value" ) ).getValue();
                                        depends = new HashSet<>();
                                        for ( MemberValue value : dependsValues ) {
                                            depends.add( ( (StringMemberValue) value ).getValue() );
                                        }
                                        break;

                                    case "io.gomint.plugin.Softdepends":
                                        dependsValues = ( (ArrayMemberValue) annotation.getMemberValue( "value" ) ).getValue();
                                        softDepends = new HashSet<>();
                                        for ( MemberValue value : dependsValues ) {
                                            softDepends.add( ( (StringMemberValue) value ).getValue() );
                                        }
                                        break;

                                    case "io.gomint.plugin.Startup":
                                        startup = ( (EnumMemberValue) annotation.getMemberValue( "value" ) ).getValue();
                                        break;

                                    default:
                                        break;
                                }
                            }

                            // We at least need the name and the version of the plugin
                            if ( name == null || version == null ) {
                                LOGGER.warn( "It seems like there is a plugin in the jar. But its missing the @Name or @Version annotation" );
                                return null;
                            }

                            meta.setName( name );
                            meta.setVersion( version );
                            meta.setPriority( StartupPriority.valueOf( startup ) );
                            meta.setDepends( depends );
                            meta.setSoftDepends( softDepends );
                            meta.setMainClass( classFile.getName() );
                        } else {
                            byte neededArguments = 0;

                            // Ok it did, time to parse the needed and optional annotations
                            AnnotationsAttribute visible = (AnnotationsAttribute) classFile.getAttribute( AnnotationsAttribute.visibleTag );
                            if ( visible == null || visible.getAnnotations() == null ) {
                                continue;
                            }

                            for ( Annotation annotation : visible.getAnnotations() ) {
                                switch ( annotation.getTypeName() ) {
                                    case "io.gomint.command.annotation.Name":
                                        neededArguments++;
                                        break;

                                    case "io.gomint.command.annotation.Description":
                                        neededArguments++;
                                        break;
                                }
                            }

                            // Do we have @Name and @Description attached?
                            if ( neededArguments == 2 ) {
                                if ( meta.getInjectionCommands() == null ) {
                                    meta.setInjectionCommands( new HashSet<>() );
                                }

                                meta.getInjectionCommands().add( classFile.getName() );
                            }
                        }
                    }
                }

                // Check if we found a valid plugin
                if ( meta.getMainClass() != null ) {
                    return meta;
                }

                return null;
            } catch ( IOException e ) {
                LOGGER.warn( "Could not load Plugin. File " + file + " is corrupted", e );
                return null;
            }
        } catch ( Exception ex ) {
            LOGGER.warn( "Could not load plugin from file " + file, ex );
            return null;
        }
    }

    @Override
    public void uninstallPlugin( Plugin plugin ) {
        // Check for security
        if ( !CallerDetectorUtil.getCallerPlugin().equals( plugin.getClass() ) ) {
            throw new SecurityException( "Plugins can only disable themselves" );
        }

        // Check if plugin is enabled
        if ( !this.installedPlugins.containsValue( plugin ) ) {
            return;
        }

        uninstallPlugin0( plugin );
    }

    private void uninstallPlugin0( Plugin plugin ) {
        // Did we already disable this plugin?
        if ( !this.installedPlugins.containsKey( plugin.getName() ) ) {
            return;
        }

        // Check for plugins with hard depends
        new HashMap<>( this.metadata ).forEach( ( name, meta ) -> {
            if ( meta.getDepends() != null && meta.getDepends().contains( plugin.getName() ) ) {
                Plugin pluginToUninstall = installedPlugins.get( name );
                if ( pluginToUninstall != null ) {
                    uninstallPlugin0( pluginToUninstall );
                }
            }
        } );

        // Unregister listeners
        try {
            List<EventListener> listeners = (List<EventListener>) this.listenerListField.get( plugin );
            for ( EventListener listener : listeners ) {
                this.eventManager.unregisterListener( listener );
            }
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }

        // Cancel all tasks and cleanup scheduler
        try {
            PluginScheduler pluginScheduler = (PluginScheduler) this.schedulerField.get( plugin );
            pluginScheduler.cleanup();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }

        // CHECKSTYLE:OFF
        try {
            LOGGER.debug( "Starting to shutdown {}", plugin.getName() );
            plugin.onUninstall();
        } catch ( Exception e ) {
            LOGGER.warn( "Plugin throw an exception whilst uninstalling: " + plugin.getName(), e );
        }
        // CHECKSTYLE:ON

        LOGGER.info( "Uninstalled plugin " + plugin.getName() );
        this.installedPlugins.remove( plugin.getName() );
        this.metadata.remove( plugin.getName() );

        // Unload the loader
        PluginClassloader classloader = (PluginClassloader) plugin.getClass().getClassLoader();
        classloader.remove();
    }

    @Override
    public String getBaseDirectory() {
        return this.pluginFolder.getAbsolutePath();
    }

    @Override
    public <T extends Plugin> T getPlugin( String name ) {
        Plugin plugin = this.loadedPlugins.get( name );
        if ( plugin != null ) {
            return (T) plugin;
        }

        return (T) this.installedPlugins.get( name );
    }

    @Override
    public <T extends Event> T callEvent( T event ) {
        LOGGER.debug( "Calling event {}", event );
        this.eventManager.triggerEvent( event );
        return event;
    }

    @Override
    public void registerListener( Plugin plugin, EventListener listener ) {
        if ( !plugin.getClass().getClassLoader().equals( listener.getClass().getClassLoader() ) ) {
            throw new SecurityException( "Wanted to register listener for another plugin" );
        }

        this.eventManager.registerListener( listener );
    }

    @Override
    public void unregisterListener( Plugin plugin, EventListener listener ) {
        if ( !plugin.getClass().getClassLoader().equals( listener.getClass().getClassLoader() ) ) {
            throw new SecurityException( "Wanted to unregister listener for another plugin" );
        }

        this.eventManager.unregisterListener( listener );
    }

    @Override
    public void registerCommand( Plugin plugin, Command command ) {
        this.commandManager.register( plugin, command );
    }

    /**
     * Cleanup / uninstall all plugins
     */
    public void close() {
        for ( Plugin plugin : new ArrayList<>( this.loadedPlugins.values() ) ) {
            uninstallPlugin0( plugin );
        }

        for ( Plugin plugin : new ArrayList<>( this.installedPlugins.values() ) ) {
            uninstallPlugin0( plugin );
        }
    }

}
