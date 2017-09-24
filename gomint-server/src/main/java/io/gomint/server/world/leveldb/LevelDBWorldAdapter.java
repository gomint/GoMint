/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkCache;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTStream;
import io.gomint.world.block.Air;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LevelDBWorldAdapter extends WorldAdapter {

    private DB db;

    /**
     * Construct and init a new levedb based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     */
    private LevelDBWorldAdapter( GoMintServer server, File worldDir ) {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        // CHECKSTYLE:OFF
        try {
            this.loadLevelDat();
            this.db = Iq80DBFactory.factory.open( new File( this.worldDir, "db" ), new Options().createIfMissing( true ) );
            this.prepareSpawnRegion();

            // This is needed due to the fact that only the y coordinate is sometimes 32k+
            int spawnX = (int) this.spawn.getX();
            int spawnZ = (int) this.spawn.getZ();
            int y = 255;

            while ( getBlockAt( new BlockPosition( spawnX, y, spawnZ ) ) instanceof Air ) {
                y--;
            }

            this.spawn.setY( y );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        // CHECKSTYLE:ON
    }

    /**
     * Loads an leveldb world given the path to the world's directory. This operation
     * performs synchronously and will at least load the entire spawn region before
     * completing.
     *
     * @param server      The GoMint Server which runs this
     * @param pathToWorld The path to the world's directory
     * @return The leveldb world adapter used to access the world
     * @throws Exception Thrown in case the world could not be loaded successfully
     */
    public static LevelDBWorldAdapter load( GoMintServer server, File pathToWorld ) throws Exception {
        return new LevelDBWorldAdapter( server, pathToWorld );
    }

    private byte[] getKey( int chunkX, int chunkZ, byte dataType ) {
        return new byte[]{
                (byte) ( chunkX & 0xFF ),
                (byte) ( ( chunkX >>> 8 ) & 0xFF ),
                (byte) ( ( chunkX >>> 16 ) & 0xFF ),
                (byte) ( ( chunkX >>> 24 ) & 0xFF ),
                (byte) ( chunkZ & 0xFF ),
                (byte) ( ( chunkZ >>> 8 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 16 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 24 ) & 0xFF ),
                dataType
        };
    }

    private byte[] getKeySubChunk( int chunkX, int chunkZ, byte dataType, byte subChunk ) {
        PacketBuffer packetBuffer = new PacketBuffer( 10 );
        packetBuffer.writeLInt( chunkX );
        packetBuffer.writeLInt( chunkZ );
        packetBuffer.writeByte( dataType );
        packetBuffer.writeByte( subChunk );
        return packetBuffer.getBuffer();
    }

    private void loadLevelDat() throws Exception {
        File levelDat = new File( this.worldDir, "level.dat" );
        if ( !levelDat.exists() || !levelDat.isFile() ) {
            throw new IOException( "Missing level.dat" );
        }

        // Default the settings
        this.levelName = "";
        this.spawn = new Location( this, 0, 0, 0 );

        try ( FileInputStream stream = new FileInputStream( levelDat ) ) {
            // Skip some data. For example the amount of bytes of this NBT Tag
            stream.skip( 8 );

            NBTStream nbtStream = new NBTStream( stream, ByteOrder.LITTLE_ENDIAN );
            nbtStream.addListener( ( path, value ) -> {
                System.out.println( path + " -> " + value + "(" + value.getClass() + ")" );

                switch ( path ) {
                    case ".LevelName":
                        LevelDBWorldAdapter.this.levelName = (String) value;
                        break;
                    case ".SpawnX":
                        LevelDBWorldAdapter.this.spawn.setX( (int) value );
                        break;
                    case ".SpawnY":
                        LevelDBWorldAdapter.this.spawn.setY( (int) value );
                        break;
                    case ".SpawnZ":
                        LevelDBWorldAdapter.this.spawn.setZ( (int) value );
                        break;
                    default:
                        break;
                }
            } );
            nbtStream.parse();
        }
    }

    @Override
    protected ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk == null ) {
            // Get version bit
            byte[] version = this.db.get( this.getKey( x, z, (byte) 0x76 ) );
            if ( version == null ) {
                // TODO: Generator
                return null;
            }

            LevelDBChunk loadingChunk = new LevelDBChunk( this, x, z );

            if ( version[0] == 3 ) {        // 1.0+ version
                for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                    try {
                        byte[] chunkData = this.db.get( this.getKeySubChunk( x, z, (byte) 0x2f, (byte) sectionY ) );
                        if ( chunkData != null ) {
                            loadingChunk.loadSection( sectionY, chunkData );
                        } else {
                            break;
                        }
                    } catch ( DbImpl.BackgroundProcessingException ignored ) {
                        break;
                    }
                }
            }

            try {
                byte[] tileEntityData = this.db.get( this.getKey( x, z, (byte) 0x31 ) );
                if ( tileEntityData != null ) {
                    loadingChunk.loadTileEntities( tileEntityData );
                }
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            try {
                byte[] entityData = this.db.get( this.getKey( x, z, (byte) 0x32 ) );
                if ( entityData != null ) {
                    loadingChunk.loadEntities( entityData );
                }
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            this.chunkCache.putChunk( loadingChunk );
            return loadingChunk;

            /*
                       byte[] extraData = null;
            try {
                extraData = this.db.get( this.getKey( x, z, (byte) 0x34 ) );
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            if ( extraData != null ) {
                DumpUtil.dumpByteArray( extraData );
            }

            if ( chunkData != null ) {
                chunk = new LevelDBChunk( this, x, z, chunkData, tileEntityData, entityData );
                this.chunkCache.putChunk( chunk );
            } else if ( generate ) {
                // TODO: Implement chunk generation here
            }*/
        }

        return chunk;
    }

    @Override
    protected void saveChunk( ChunkAdapter chunk ) {
        if ( chunk == null ) {
            return;
        }

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        WriteBatch writeBatch = this.db.createWriteBatch();
        writeBatch.put( this.getKey( chunkX, chunkZ, (byte) 0x30 ), ( (LevelDBChunk) chunk ).getSaveData() );
        this.db.write( writeBatch );
    }

}