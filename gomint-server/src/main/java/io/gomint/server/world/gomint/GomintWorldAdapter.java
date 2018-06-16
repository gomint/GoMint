/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.gomint;

import com.google.common.io.Files;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.GoMintServer;
import io.gomint.server.plugin.PluginClassloader;
import io.gomint.server.world.*;
import io.gomint.server.world.gomint.io.Section;
import io.gomint.server.world.gomint.io.SectionFile;
import io.gomint.world.Chunk;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author geNAZt
 * @version 1.0
 */
public final class GomintWorldAdapter extends WorldAdapter {

    private GomintWorldAdapter( final GoMintServer server, final String name, final Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        super( server, new File( name ) );
        this.chunkCache = new ChunkCache( this );
        this.levelName = name;

        // Build up generator
        GeneratorContext context = new GeneratorContext();
        this.constructGenerator( generator, context );

        // Generate a spawnpoint
        BlockPosition spawnPoint = this.chunkGenerator.getSpawnPoint();
        this.spawn = new Location( this, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ() );

        // Write world.index
        try {
            this.saveWorldInit();
        } catch ( IOException e ) {
            throw new WorldCreateException( "world.index for world '" + name + "' could not be saved", e );
        }

        // Prepare spawn region
        this.prepareSpawnRegion();
    }

    private GomintWorldAdapter( final GoMintServer server, final File worldDir ) throws WorldLoadException {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        // Read world.index
        try ( SectionFile file = new SectionFile( new File( worldDir, "world.index" ) ) ) {
            String generatorClass = "";
            try ( Section section = file.getSection() ) {
                ByteBuffer in = section.getInput();

                // Read level name
                short strLength = in.getShort();
                byte[] strContent = new byte[strLength];
                in.get( strContent );

                this.levelName = new String( strContent );

                // Read spawn location
                int x = in.getInt();
                int y = in.getInt();
                int z = in.getInt();
                
                this.spawn = new Location( this, x, y, z, 0, 0 );

                // Read gamerule
                in.getInt();

                // Get chunk generator
                strLength = in.getShort();
                strContent = new byte[strLength];
                in.get( strContent );

                generatorClass = new String( strContent );

                strLength = in.getShort();
                strContent = new byte[strLength];
                in.get( strContent );

                String generatorContext = new String( strContent );

                JSONParser parser = new JSONParser();
                JSONObject contextData = (JSONObject) parser.parse( generatorContext );
                GeneratorContext context = new GeneratorContext();
                contextData.forEach( ( o, o2 ) -> context.put( (String) o, o2 ) );

                Class<? extends ChunkGenerator> chunkGeneratorClass = (Class<? extends ChunkGenerator>) PluginClassloader.find( generatorClass );
                this.constructGenerator( chunkGeneratorClass, context );
            } catch ( WorldCreateException e ) {
                this.logger.error( "Found chunk generator for {} but it did throw an error on instancing", generatorClass, e );
            } catch ( ClassNotFoundException e ) {
                this.logger.warn( "Could not find original chunk generator: {}", generatorClass );
            }
        } catch ( IOException | ParseException e ) {
            throw new WorldLoadException( "Could not load world '" + worldDir.getName() + "'", e );
        }

        // Prepare spawn region
        this.prepareSpawnRegion();
    }

    public static GomintWorldAdapter load( GoMintServer server, File path ) throws WorldLoadException {
        return new GomintWorldAdapter( server, path );
    }

    private void saveWorldInit() throws IOException {
        File worldIndex = new File( this.worldDir, "world.index" );
        if ( worldIndex.exists() ) {
            // Backup old world.index
            Files.copy( worldIndex, new File( this.worldDir, "world.index.bak" ) );

            // Delete the old one
            worldIndex.delete();
        }

        try ( SectionFile file = new SectionFile( worldIndex ) ) {
            try ( Section worldSection = file.createSection() ) {
                DataOutputStream out = worldSection.getOutput();

                // Write level name
                out.writeUTF( this.levelName );

                // Write spawn
                out.writeInt( MathUtils.fastFloor( this.spawn.getX() ) );
                out.writeInt( MathUtils.fastFloor( this.spawn.getY() ) );
                out.writeInt( MathUtils.fastFloor( this.spawn.getZ() ) );

                // Write gamerules
                out.writeInt( 0 ); // TODO: write game rules

                // Write generator stuff
                out.writeUTF( this.chunkGenerator.getClass().getName() );
                out.writeUTF( this.chunkGenerator.getContext().toString() );
            }
        }
    }

    @Override
    public ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        // Check cache
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk != null ) {
            return chunk;
        }

        // Check if chunk exists
        File chunkFile = new File( this.worldDir, MathUtils.fastCeil( x / 128f ) + "/" + MathUtils.fastCeil( z / 128f ) + "/" + x + "_" + z + ".chunkdata" );
        if ( !chunkFile.exists() ) {
            if ( generate ) {
                ChunkAdapter adapter = this.generate( x, z );
                adapter.calculateHeightmap( 16 );
                return adapter;
            }

            return null;
        }

        // Read chunk
        try ( SectionFile file = new SectionFile( chunkFile ) ) {
            GomintChunkAdapter chunkAdapter = new GomintChunkAdapter( this, x, z, System.currentTimeMillis() );
            chunkAdapter.loadFromSection( file );
            this.chunkCache.putChunk( chunkAdapter );
            return chunkAdapter;
        } catch ( IOException e ) {
            this.logger.warn( "Could not read chunk {} / {}", x, z, e );
        }

        return null;
    }

    @Override
    protected void saveChunk( ChunkAdapter chunk ) {
        // Check if chunk exists
        File chunkFile = new File( this.worldDir, MathUtils.fastCeil( chunk.getX() / 128f ) + "/" + MathUtils.fastCeil( chunk.getZ() / 128f ) + "/" + chunk.getX() + "_" + chunk.getZ() + ".chunkdata" );
        File backupFile = null;
        if ( chunkFile.exists() ) {
            // Copy it
            try {
                backupFile = new File( this.worldDir, MathUtils.fastCeil( chunk.getX() / 128f ) + "/" + MathUtils.fastCeil( chunk.getZ() / 128f ) + "/" + chunk.getX() + "_" + chunk.getZ() + ".chunkdata.bak" );
                Files.copy( chunkFile, backupFile );
            } catch ( IOException e ) {
                // We can't create a backup, error out
                this.logger.error( "Could not create chunk backup. Will not save chunk {} / {}", chunk.getX(), chunk.getZ(), e );
                return;
            }

            chunkFile.delete();
        } else {
            File zFolder = chunkFile.getParentFile();
            if ( !zFolder.exists() ) {
                File xFolder = zFolder.getParentFile();
                if ( !xFolder.exists() ) {
                    xFolder.mkdir();
                }

                zFolder.mkdir();
            }
        }

        // Create new section file
        try ( SectionFile sectionFile = new SectionFile( chunkFile ) ) {
            ( (GomintChunkAdapter) chunk ).saveToSection( sectionFile );

            if ( backupFile != null && backupFile.exists() ) {
                backupFile.delete();
            }
        } catch ( IOException e ) {
            this.logger.error( "Could not save chunk. You may need to restore chunk {} / {}", chunk.getX(), chunk.getZ(), e );
        }
    }

    @Override
    protected void closeFDs() {

    }

    @Override
    public Chunk generateEmptyChunk( int x, int z ) {
        return new GomintChunkAdapter( this, x, z, System.currentTimeMillis() );
    }

    /**
     * Create a new gomint based world. This will not override old worlds. It will fail with a WorldCreateException when
     * a folder has been found with the same name (regardless of the content of that folder)
     *
     * @param server    which wants to create this world
     * @param name      of the new world
     * @param generator which is used to generate this worlds chunks and spawn point
     * @return new world
     * @throws WorldCreateException when there already is a world or a error during creating occured
     */
    public static GomintWorldAdapter create( GoMintServer server, String name, Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        File worldFolder = new File( name );
        if ( worldFolder.exists() ) {
            throw new WorldCreateException( "Folder with name '" + name + "' already exists" );
        }

        if ( !worldFolder.mkdir() ) {
            throw new WorldCreateException( "World '" + name + "' could not be created. Folder could not be created" );
        }

        return new GomintWorldAdapter( server, name, generator );
    }

}
