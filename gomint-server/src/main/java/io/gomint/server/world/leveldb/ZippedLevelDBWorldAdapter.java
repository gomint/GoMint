/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.entity.EntityPlayer;
import io.gomint.server.GoMintServer;
import io.gomint.server.world.WorldLoadException;

import java.io.*;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author geNAZt
 * @version 1.0
 */
public final class ZippedLevelDBWorldAdapter extends LevelDBWorldAdapter {

    private final String name;

    /**
     * Construct and init a new levedb based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     * @param name     of the world
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    private ZippedLevelDBWorldAdapter( GoMintServer server, File worldDir, String name ) throws WorldLoadException {
        super( server, worldDir );
        this.name = name;
    }


    @Override
    public String getWorldName() {
        return this.name;
    }

    /**
     * Loads an leveldb world given the path to the world's directory. This operation
     * performs synchronously and will at least load the entire spawn region before
     * completing.
     *
     * @param server      The GoMint Server which runs this
     * @param pathToWorld The path to the world's directory
     * @param name        name of the world
     * @return The leveldb world adapter used to access the world
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    public static ZippedLevelDBWorldAdapter load( GoMintServer server, File pathToWorld, String name ) throws WorldLoadException {
        // Extract to a temporary dir
        File tempDir = new File( System.getProperty( "java.io.tmpdir" ) );
        File worldDir = new File( tempDir, "gomint_world_" + System.currentTimeMillis() );
        if ( !worldDir.mkdirs() ) {
            throw new WorldLoadException( "Could not create temporary world dir " + worldDir.getAbsolutePath() );
        }

        // mcworld data is a zip
        try {
            unzip( pathToWorld, worldDir );
        } catch ( IOException e ) {
            throw new WorldLoadException( "Could not extract to dir " + worldDir.getAbsolutePath(), e );
        }

        // Be sure to delete on exit
        worldDir.deleteOnExit();
        return new ZippedLevelDBWorldAdapter( server, worldDir, name );
    }

    private static void unzip( File source, File out ) throws IOException, WorldLoadException {
        try ( ZipInputStream zis = new ZipInputStream( new FileInputStream( source ) ) ) {
            ZipEntry entry = zis.getNextEntry();
            while ( entry != null ) {
                File file = new File( out, entry.getName() );
                if ( entry.isDirectory() ) {
                    if ( !file.mkdirs() ) {
                        throw new WorldLoadException( "Could not create dir from extraction " + file.getAbsolutePath() );
                    }
                } else {
                    File parent = file.getParentFile();
                    if ( !parent.exists() && !parent.mkdirs() ) {
                        throw new WorldLoadException( "Could not create dir from extraction " + file.getAbsolutePath() );
                    }

                    try ( BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( file ) ) ) {
                        if ( entry.getSize() > 0 ) {
                            byte[] buffer = new byte[(int) entry.getSize()];
                            int location;

                            while ( ( location = zis.read( buffer ) ) != -1 ) {
                                bos.write( buffer, 0, location );
                            }
                        }
                    }
                }

                entry = zis.getNextEntry();
            }
        }
    }

    @Override
    public void unload( Consumer<EntityPlayer> playerConsumer ) {
        super.unload( playerConsumer );

        if ( !this.worldDir.delete() ) {
            this.logger.warn( "Could not delete temp directory" );
        }
    }

}
