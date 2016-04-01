/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import com.google.common.base.Preconditions;
import io.gomint.command.Command;
import io.gomint.command.CommandSender;
import io.gomint.event.*;
import io.gomint.event.EventListener;
import io.gomint.plugin.*;
import io.gomint.server.GoMintServer;
import io.gomint.server.command.CommandManager;
import io.gomint.server.event.EventManager;
import io.gomint.server.scheduler.CoreScheduler;
import io.gomint.server.util.CallerDetectorUtil;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * An implementation of {@link PluginManager}
 *
 * @author Fabian
 * @author Digot
 * @version 1.0
 */
public class SimplePluginManager implements PluginManager {
    private final Logger logger = LoggerFactory.getLogger( SimplePluginManager.class );
    private final CoreScheduler scheduler;
    private final File pluginsFolder;

    private final EventManager eventManager;
    private final CommandManager commandManager;

    private final Set<Plugin> installedPlugins;
    private final Set<PluginMeta> detectedPlugins;

    public SimplePluginManager( GoMintServer server ) {
        this.scheduler = new CoreScheduler( server.getExecutorService(), server.getSyncTaskManager() );
        this.installedPlugins = new HashSet<>();
        this.detectedPlugins = new LinkedHashSet<>();
        this.pluginsFolder = new File( "plugins/" ); //Can be customizable in the future using command line args or config

        this.eventManager = new EventManager();
        this.commandManager = new CommandManager();
    }

    @Override
    public void disablePlugin( Plugin plugin ) {
        // Check for security
        if ( !CallerDetectorUtil.getCallerClassName( 2 ).equals( plugin.getClass().getName() ) ) {
            throw new SecurityException( "Plugins can only disable themselves" );
        }
    }

    @Override
    public String getBaseDirectory() {
        return this.pluginsFolder.getAbsolutePath();
    }

    @Override
    public boolean isPluginInstalled( String pluginName ) {
        return this.installedPlugins.stream().anyMatch(plugin -> plugin.getName().equalsIgnoreCase( pluginName ) );
    }

    public void loadPlugins(){
        //Only GoMint Server should be allowed to call this
        if ( !CallerDetectorUtil.getCallerClassName( 1 ).equals( GoMintServer.class.getName() ) ) {
            throw new SecurityException( "The plugins can only be loaded by the GoMint Server class" );
        }

        // STEP 1: DETECTION - Scan the plugins directory
        if( !this.scanForPlugins() ) return;
        // STEP 2: INSTALLATION - Load the classes of the plugins and construct them
        this.installPlugins();
        // STEP 3: STARTUP - Invoke the startup hook on all plugins
        this.enablePlugins();
    }

    private void enablePlugins ( ) {
       for ( Plugin installedPlugin : this.installedPlugins ) {
            try{
                installedPlugin.onStartup(); //Not sure if onInstall would be called here
                this.logger.info( "Enabled " + installedPlugin.toString() );
            }
            catch ( Exception e ) {
                this.logger.error( "Failed to enable " + installedPlugin.getName() + "!");
                e.printStackTrace();
            }
        }
    }

    private void installPlugins () {

        //Sort the plugins so plugins with a lower startup priority are loaded sooner.
        List<PluginMeta> pluginMetaList = new ArrayList<>( this.detectedPlugins );
        pluginMetaList.sort( ( o1, o2 ) -> o1.getPriority().compareTo( o2.getPriority() ) );

        for ( PluginMeta detectedPlugin : pluginMetaList ) {
            try {
                //Load the main class
                ClassLoader classLoader = new PluginClassLoader(new URL[] { detectedPlugin.getPluginFile().toURI().toURL() } );
                Class<?> pluginClass = classLoader.loadClass( detectedPlugin.getMainClass() );

                //Retrieve the constructor
                Constructor<?> constructor = pluginClass.getConstructor();
                constructor.setAccessible( true );

                //Initialize the instance
                Plugin plugin = ( Plugin ) constructor.newInstance();
                plugin.loadMetaData( detectedPlugin );
                plugin.setPluginManager( this );

                //Invoke the install hook
                plugin.onInstall();  //Not sure if onStartup would be called here

                //Add to installed plugins
                this.installedPlugins.add( plugin );
            } catch ( Exception e ) {
                this.logger.error( "Failed to install plugin " + detectedPlugin.getName() + ": " + e.getMessage() );
            }
        }

        //We don't need the detected plugin collection anymore. Clear it.
        this.detectedPlugins.clear();
    }

    /**
     * Scans the plugin folder (and creates it if it doesn't exist) for plugins
     * @return if any plugins were found
     */
    private boolean scanForPlugins(){
        //Check if the plugin directory exists
        if( !this.pluginsFolder.exists() ) {
            //It doesn't exist - create it
            if( !this.pluginsFolder.mkdir() ){
                this.logger.error( "Couldn't create plugins folder. Check your file permissions!" );
                return false;
            }
        }

        //Retrieve a list of all files inside the plugins folder
        File[] files = this.pluginsFolder.listFiles();

        if( files == null ||  files.length == 0 ) {
           //Do nothing
            return false;
        }

        //Start scanning
        for ( File file : files ) {
            if( file.getName().toLowerCase().endsWith( ".jar" )) {
                try( JarFile jarFile = new JarFile( file )) {
                    //Lets find the main class of the plugin
                    ClassFile mainClassFile = null;
                    File mainFile = null;

                    //Retrieve all elements in the plugin jar file
                    Enumeration<JarEntry> entryEnumeration = jarFile.entries();
                    while( entryEnumeration.hasMoreElements() ) {
                        JarEntry jarEntry = entryEnumeration.nextElement();

                        //Check if its a class file
                        if( jarEntry.getName().toLowerCase().endsWith( ".class" )) {
                            ClassFile classFile = new ClassFile( new DataInputStream( jarFile.getInputStream( jarEntry ) ) );

                            if( classFile.getSuperclass().equals( Plugin.class.getName() ) ) {
                                if( mainClassFile == null ) {
                                    //Set it as the main class for now
                                    mainClassFile = classFile;
                                    mainFile = file;
                                }
                                else {
                                    //There are more than one main class
                                    throw new IllegalStateException( "Multiple plugin main classes found in " + file.getName() );
                                }
                            }
                        }
                    }

                    //Check if the main class was found
                    if( mainClassFile == null ) {
                        throw new IllegalStateException( "No class that extends Plugin found in " + file.getName() );
                    }

                    //We have the main class now - let's try to load metadata
                    AnnotationsAttribute annotations = (AnnotationsAttribute) mainClassFile.getAttribute( AnnotationsAttribute.visibleTag );

                    String pluginName = null;
                    PluginVersion pluginVersion = null;
                    StartupPriority pluginStartupPriority = StartupPriority.LOAD; //Default to load

                    for ( Annotation annotation : annotations.getAnnotations() ) {
                        switch ( annotation.getTypeName() ) {
                            case "io.gomint.plugin.Name":
                                pluginName = ( (StringMemberValue ) annotation.getMemberValue( "value" ) ).getValue();
                                break;
                            case "io.gomint.plugin.Version":
                                int major = ( (IntegerMemberValue ) annotation.getMemberValue( "major" ) ).getValue();
                                int minor = ( (IntegerMemberValue) annotation.getMemberValue( "minor" ) ).getValue();
                                pluginVersion = new PluginVersion( major, minor );
                                break;
                            case "io.gomint.plugin.Startup":
                                pluginStartupPriority = StartupPriority.valueOf( ( (EnumMemberValue ) annotation.getMemberValue( "value" ) ).getValue() );
                                break;
                        }
                    }

                    //Validate
                    if( pluginName == null || pluginVersion == null ) {
                        this.logger.error( "Couldn't find the name or the version of " + file.getName() + ". Are the annotations missing?" );
                        return false;
                    }

                    //Fill plugin meta
                    PluginMeta pluginMeta = new PluginMeta(
                            pluginName, pluginVersion, pluginStartupPriority, mainClassFile.getName(), mainFile
                    );

                    //Done! Add to detected plugins
                    this.detectedPlugins.add( pluginMeta );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    @Override
    public void registerListener ( EventListener eventListener ) {
        Preconditions.checkArgument( eventListener != null, "Given EventListener is null" );
        this.eventManager.registerListener( eventListener );
    }

    @Override
    public void registerCommand ( Command command ) {
        Preconditions.checkArgument( command != null, "Command is null" );
        this.commandManager.registerCommand( command );
    }

    @Override
    public void callEvent ( Event event ) {
        Preconditions.checkArgument( event != null, "Given event is null" );
        this.eventManager.triggerEvent( event );
    }

    @Override
    public void executeCommand ( CommandSender sender, String name, String... args ) {
        this.commandManager.executeCommand( sender, name, args );
    }

    @Override
    public void executeCommand ( CommandSender sender, String name ) {
        this.commandManager.executeCommand( sender, name, null );
    }
}
