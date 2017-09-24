/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.GoMintServer;
import io.gomint.server.world.anvil.AnvilWorldAdapter;
import io.gomint.server.world.leveldb.LevelDBWorldAdapter;
import io.gomint.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class WorldManager {

    private static final Logger logger = LoggerFactory.getLogger( WorldManager.class );
    private final GoMintServer server;
    private List<WorldAdapter> loadedWorlds;

    // Internal ticking
    private long currentTickTime;
    private float lastTickDiff;
    private final Consumer<WorldAdapter> worldAdapterConsumer = new Consumer<WorldAdapter>() {
        @Override
        public void accept( WorldAdapter worldAdapter ) {
            worldAdapter.update( currentTickTime, lastTickDiff );
        }
    };

    /**
     * Constructs a new world manager that does not yet hold any worlds.
     *
     * @param server The server for which this WorldManager handles world for
     */
    public WorldManager( GoMintServer server ) {
        this.server = server;
        this.loadedWorlds = new ArrayList<>();
    }

    /**
     * Ticks all worlds that are currently loaded.
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentTimeMS, float dT ) {
        this.currentTickTime = currentTimeMS;
        this.lastTickDiff = dT;
        this.loadedWorlds.forEach( this.worldAdapterConsumer );
    }

    /**
     * Gets a collection of all worlds held by the world manager.
     *
     * @return A collection of all worlds held by the world manager
     */
    public Collection<WorldAdapter> getWorlds() {
        return this.loadedWorlds;
    }

    /**
     * Gets the world with the given name out of the world manager if any such
     * world exists.
     *
     * @param name The name of the world to be retrieved
     * @return The world if found or null otherwise
     */
    public WorldAdapter getWorld( String name ) {
        for ( WorldAdapter world : this.loadedWorlds ) {
            if ( world.getWorldName().equals( name ) ) {
                return world;
            }
        }

        return null;
    }

    /**
     * Adds the given world to the world manager. It may be retrieved by name
     * from now on.
     *
     * @param world The world to be added
     */
    public void addWorld( WorldAdapter world ) {
        this.loadedWorlds.add( world );
    }

    /**
     * Loads the world from the specified path. This method will attempt to detect the
     * format of the world automatically. On success the world will be added to the
     * world manage immediately.
     *
     * @param path The path of the world
     * @return the world which has been loaded
     * @throws Exception Thrown in case the world could not be loaded
     */
    public World loadWorld( String path ) throws Exception {
        logger.info( "Attempting to load world '" + path + "'" );

        File file = new File( path );
        if ( !file.exists() ) {
            throw new IOException( "World does not exist" );
        }

        if ( file.isDirectory() ) {
            // Anvil world:
            File regionFolder = new File( file, "region" );
            if ( regionFolder.exists() && regionFolder.isDirectory() ) {
                logger.info( "Detected anvil world '" + path + "'" );
                return this.loadAnvilWorld( file );
            }

            // LevelDB world:
            File dbFolder = new File( file, "db" );
            if ( dbFolder.exists() && dbFolder.isDirectory() ) {
                logger.info( "Detected leveldb world '" + path + "'" );
                return this.loadLevelDBWorld( file );
            }
        }

        throw new IOException( "Could not detect world format" );
    }

    private World loadLevelDBWorld( File path ) throws Exception {
        LevelDBWorldAdapter world = LevelDBWorldAdapter.load( this.server, path );
        this.addWorld( world );
        logger.info( "Successfully loaded world '" + path.getName() + "'" );
        return world;
    }

    private World loadAnvilWorld( File path ) throws Exception {
        AnvilWorldAdapter world = AnvilWorldAdapter.load( this.server, path );
        this.addWorld( world );
        logger.info( "Successfully loaded world '" + path.getName() + "'" );
        return world;
    }

    public void close() {
        for ( WorldAdapter loadedWorld : this.loadedWorlds ) {
            loadedWorld.close();
        }
    }

}
