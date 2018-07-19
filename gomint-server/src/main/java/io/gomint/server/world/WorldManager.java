/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.GoMint;
import io.gomint.server.GoMintServer;
import io.gomint.server.world.anvil.AnvilWorldAdapter;
import io.gomint.server.world.gomint.GomintWorldAdapter;
import io.gomint.server.world.inmemory.InMemoryWorldAdapter;
import io.gomint.server.world.leveldb.LevelDBWorldAdapter;
import io.gomint.server.world.leveldb.ZippedLevelDBWorldAdapter;
import io.gomint.world.World;
import io.gomint.world.generator.CreateOptions;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class WorldManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( WorldManager.class );
    private final GoMintServer server;
    private Object2ObjectMap<String, WorldAdapter> loadedWorlds;

    /**
     * Constructs a new world manager that does not yet hold any worlds.
     *
     * @param server The server for which this WorldManager handles world for
     */
    public WorldManager( GoMintServer server ) {
        this.server = server;
        this.loadedWorlds = new Object2ObjectOpenHashMap<>();
    }

    /**
     * Ticks all worlds that are currently loaded.
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentTimeMS, float dT ) {
        for ( WorldAdapter world : this.getWorlds() ) {
            world.update( currentTimeMS, dT );
        }
    }

    /**
     * Gets a collection of all worlds held by the world manager.
     *
     * @return A collection of all worlds held by the world manager
     */
    public Collection<WorldAdapter> getWorlds() {
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Getting worlds from an async thread. This is not safe and can lead to CME", new Exception() );
        }

        return this.loadedWorlds.values();
    }

    /**
     * Gets the world with the given name out of the world manager if any such
     * world exists.
     *
     * @param name The name of the world to be retrieved
     * @return The world if found or null otherwise
     */
    public WorldAdapter getWorld( String name ) {
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Getting a world from an async thread. This is not safe and can lead to CME", new Exception() );
        }

        return this.loadedWorlds.get( name );
    }

    /**
     * Adds the given world to the world manager. It may be retrieved by name
     * from now on.
     *
     * @param world The world to be added
     */
    private void addWorld( WorldAdapter world ) {
        this.loadedWorlds.put( world.getWorldName(), world );
    }

    /**
     * Loads the world from the specified path. This method will attempt to detect the
     * format of the world automatically. On success the world will be added to the
     * world manage immediately.
     *
     * @param path The path of the world
     * @return the world which has been loaded
     * @throws WorldLoadException Thrown in case the world could not be loaded
     */
    public World loadWorld( String path ) throws WorldLoadException {
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Loading worlds from an async thread. This is not safe and can lead to CME", new Exception() );
        }

        LOGGER.info( "Attempting to load world '{}'", path );

        File file = new File( path );
        if ( file.exists() ) {
            if ( file.isDirectory() ) {
                // Anvil world:
                File regionFolder = new File( file, "region" );
                if ( regionFolder.exists() && regionFolder.isDirectory() ) {
                    LOGGER.info( "Detected anvil world '{}'", path );
                    return this.loadAnvilWorld( file );
                }

                // LevelDB world:
                File dbFolder = new File( file, "db" );
                if ( dbFolder.exists() && dbFolder.isDirectory() ) {
                    LOGGER.info( "Detected leveldb world '{}'", path );
                    return this.loadLevelDBWorld( file );
                }
            } else {
                throw new WorldLoadException( "World does not exist" );
            }
        } else {
            File mcWorldFile = new File( file + ".mcworld" );
            if ( mcWorldFile.exists() ) {
                LOGGER.info( "Detected zipped leveldb world '{}'", path );
                return this.loadZippedLevelDBWorld( mcWorldFile, path );
            } else {
                throw new WorldLoadException( "World does not exist" );
            }
        }

        throw new WorldLoadException( "Could not detect world format" );
    }

    private World loadZippedLevelDBWorld( File path, String name ) throws WorldLoadException {
        LevelDBWorldAdapter world = ZippedLevelDBWorldAdapter.load( this.server, path, name );
        this.addWorld( world );
        LOGGER.info( "Successfully loaded world '{}'", name );
        return world;
    }

    private World loadLevelDBWorld( File path ) throws WorldLoadException {
        LevelDBWorldAdapter world = LevelDBWorldAdapter.load( this.server, path );
        this.addWorld( world );
        LOGGER.info( "Successfully loaded world '{}'", path.getName() );
        return world;
    }

    private World loadAnvilWorld( File path ) throws WorldLoadException {
        AnvilWorldAdapter world = AnvilWorldAdapter.load( this.server, path );
        this.addWorld( world );
        LOGGER.info( "Successfully loaded world '{}'", path.getName() );
        return world;
    }

    /**
     * Close and save all worlds
     */
    public void close() {
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Closing worlds from an async thread. This is not safe and can lead to CME", new Exception() );
        }

        for ( WorldAdapter loadedWorld : new ArrayList<>( this.getWorlds() ) ) {
            loadedWorld.unload( null );
        }
    }

    /**
     * Unload given world from RAM
     *
     * @param worldAdapter which should be unloaded
     */
    void unloadWorld( WorldAdapter worldAdapter ) {
        this.loadedWorlds.remove( worldAdapter.getWorldName() );
    }

    /**
     * Create a new world
     *
     * @param name    of the new world
     * @param options with which this world should be generated
     * @return generated world
     */
    public World createWorld( String name, CreateOptions options ) {
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Creating worlds from an async thread. This is not safe and can lead to CME", new Exception() );
        }

        // Check which type of world we want to create
        WorldAdapter world;
        switch ( options.worldType() ) {
            case GOMINT:
            case ANVIL:
                try {
                    world = AnvilWorldAdapter.create( this.server, name, options.generator() );
                } catch ( WorldCreateException e ) {
                    LOGGER.error( "Could not create new world", e );
                    return null;
                }

                break;

            case IN_MEMORY:
                try {
                    world = InMemoryWorldAdapter.create( this.server, name, options.generator() );
                } catch ( WorldCreateException e ) {
                    LOGGER.error( "Could not create new world", e );
                    return null;
                }

                break;

            default:
                return null;
        }

        this.loadedWorlds.put( world.getWorldName(), world );
        return world;
    }

}
