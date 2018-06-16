/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil;

import io.gomint.server.world.WorldLoadException;
import io.gomint.taglib.NBTStream;

import java.io.*;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class RegionFile {

    private final File regionFile;
    private final AnvilWorldAdapter world;
    private final ThreadLocal<RandomAccessFile> file;
    private final List<RandomAccessFile> openFDs = Collections.synchronizedList( new ArrayList<>() );
    private final int[][] lookupCache = new int[4096][];
    private final int[] lastSaveTimes = new int[4096];

    // Lock for caches
    private final ReadWriteLock lock = new ReentrantReadWriteLock( true );

    /**
     * Constructs a new region file that will load from the specified file.
     *
     * @param world The world the region file belongs to
     * @param file  The file to load
     * @throws IOException Thrown in case the file was not found / could not be opened
     */
    RegionFile( AnvilWorldAdapter world, File file ) throws IOException {
        this.regionFile = file;
        this.world = world;
        this.file = new ThreadLocal<>();

        RandomAccessFile randomFile = this.getRandomAccessFile();
        if ( randomFile.length() < 8192 ) {
            // Add the region file metadata table / header:
            byte[] header = new byte[8192];
            randomFile.seek( 0L );
            randomFile.write( header );
        }

        // Read offset header
        for ( int i = 0; i < 1024; i++ ) {
            // First int is the offset in the file
            byte[] buffer = new byte[4];
            randomFile.read( buffer, 0, 3 );

            int offset = ( ( (int) buffer[0] << 16 & 0xFF0000 ) | ( (int) buffer[1] << 8 & 0xFF00 ) | ( (int) buffer[2] & 0xFF ) );
            int length = randomFile.read();

            this.lookupCache[i] = new int[]{ offset, length };
        }

        // Read timestamps
        if ( randomFile.length() > 8192 ) { // Files without any chunks don't include timestamps
            for ( int i = 0; i < 1024; i++ ) {
                this.lastSaveTimes[i] = randomFile.readInt();
            }
        }
    }

    private RandomAccessFile getRandomAccessFile() throws FileNotFoundException {
        if ( this.file.get() != null ) {
            return this.file.get();
        }

        this.file.set( new RandomAccessFile( this.regionFile, "rw" ) );
        this.openFDs.add( this.file.get() );
        return this.file.get();
    }

    /**
     * Attempts to load an anvil chunk from the NBT data found inside this region file
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return The chunk if found or null
     * @throws IOException        Thrown in case an I/O error occurs
     * @throws WorldLoadException Thrown in the case that the chunk loaded was corrupted
     */
    AnvilChunkAdapter loadChunk( int x, int z ) throws IOException, WorldLoadException {
        InputStream input;

        this.lock.readLock().lock();
        try {
            int chunkIndex = ( ( x & 31 ) + ( ( z & 31 ) << 5 ) );
            int[] lookup = this.lookupCache[chunkIndex];

            int offset = lookup[0];
            int length = lookup[1];

            if ( offset == 0 || length == 0 ) {
                return null;
            }

            long fileOffset = (long) offset << 12;

            // Seek chunk data:
            RandomAccessFile randomFile = this.getRandomAccessFile();

            byte[] buffer = new byte[4];
            randomFile.seek( fileOffset );
            randomFile.read( buffer, 0, 4 );

            int exactLength = ( ( (int) buffer[0] << 24 & 0xFF000000 ) | ( (int) buffer[1] << 16 & 0xFF0000 ) | ( (int) buffer[2] << 8 & 0xFF00 ) | ( (int) buffer[3] & 0xFF ) );
            int compressionScheme = randomFile.read();
            byte[] nbtBuffer = new byte[exactLength];
            randomFile.read( nbtBuffer );

            switch ( compressionScheme ) {
                case 1:
                    Inflater gzipInflater = new Inflater( true );
                    gzipInflater.setInput( nbtBuffer );

                    ByteArrayOutputStream decompressOutput = new ByteArrayOutputStream();
                    byte[] decompressBuffer = new byte[16 * 1024];

                    try {
                        int amountOfBytesDecompressed = -1;
                        while ( ( amountOfBytesDecompressed = gzipInflater.inflate( decompressBuffer ) ) > 0 ) {
                            decompressOutput.write( decompressBuffer, 0, amountOfBytesDecompressed );
                        }
                    } catch ( DataFormatException e ) {
                        throw new IOException( "Could not decompress data due to zlib error: ", e );
                    }

                    input = new ByteArrayInputStream( decompressOutput.toByteArray() );
                    break;

                case 2:
                    Inflater inflater = new Inflater();
                    inflater.setInput( nbtBuffer );

                    decompressOutput = new ByteArrayOutputStream();
                    decompressBuffer = new byte[16 * 1024];

                    try {
                        int amountOfBytesDecompressed = -1;
                        while ( ( amountOfBytesDecompressed = inflater.inflate( decompressBuffer ) ) > 0 ) {
                            decompressOutput.write( decompressBuffer, 0, amountOfBytesDecompressed );
                        }
                    } catch ( DataFormatException e ) {
                        throw new IOException( "Could not decompress data due to zlib error: ", e );
                    }

                    input = new ByteArrayInputStream( decompressOutput.toByteArray() );
                    break;

                default:
                    throw new IOException( "Unsupported compression scheme for chunk data (" + compressionScheme + ")" );
            }

            NBTStream nbtStream = new NBTStream( input, ByteOrder.BIG_ENDIAN );
            AnvilChunkAdapter chunkAdapter = new AnvilChunkAdapter( this.world, x, z, TimeUnit.SECONDS.toMillis( this.lastSaveTimes[chunkIndex] ) );
            chunkAdapter.loadFromNBT( nbtStream );

            if ( !chunkAdapter.isPopulated() ) {
                this.world.addPopulateTask( chunkAdapter );
            }

            return chunkAdapter;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * Write a chunk into this regionfile
     *
     * @param chunk The chunk which should be written to the region file
     * @throws IOException A exception which get thrown when a I/O error occurred
     */
    void saveChunk( AnvilChunkAdapter chunk ) throws IOException {
        this.lock.writeLock().lock();
        try {
            int x = chunk.getX();
            int z = chunk.getZ();

            int chunkIndex = ( ( x & 31 ) + ( ( z & 31 ) << 5 ) );
            long fileOffset = chunkIndex << 2;

            // Navigate to entry in location table:
            RandomAccessFile randomFile = this.getRandomAccessFile();
            randomFile.seek( fileOffset );

            byte[] buffer = new byte[4];
            randomFile.read( buffer, 0, 3 );

            int sectorOffset = ( ( (int) buffer[0] << 16 & 0xFF0000 ) | ( (int) buffer[1] << 8 & 0xFF00 ) | ( (int) buffer[2] & 0xFF ) );
            int sectorLength = randomFile.read();

            long byteOffset;
            long byteLength;

            // Create compressed NBT data in order to know actual byteLength:
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DeflaterOutputStream dout = new DeflaterOutputStream( bout );
            chunk.saveToNBT( dout );

            byte[] nbtData = bout.toByteArray();
            byteLength = (long) nbtData.length + 5;

            // Determine accurate byte offset and make sure chunk fits into its allocated sector(s):
            if ( sectorOffset == 0 || sectorLength == 0 || ( ( byteLength + 4095 ) >> 12 ) > sectorLength ) {
                byteOffset = randomFile.length();
                if ( ( byteOffset & 4095 ) != 0 ) {
                    throw new IOException( "Failed to save chunk: Misaligned region file" );
                }

                sectorOffset = (int) ( byteOffset >> 12 );
                sectorLength = (int) ( ( byteLength + 4095 ) >> 12 );
            } else {
                byteOffset = sectorOffset << 12;
            }

            // Write Metadata:
            randomFile.seek( fileOffset );
            buffer[0] = (byte) ( ( sectorOffset >> 16 ) & 0xFF );
            buffer[1] = (byte) ( ( sectorOffset >> 8 ) & 0xFF );
            buffer[2] = (byte) ( sectorOffset & 0xFF );
            buffer[3] = (byte) sectorLength;

            randomFile.write( buffer );

            // Update cache
            this.lookupCache[chunkIndex] = new int[]{ sectorOffset, sectorLength };

            // Adjust timestamp:
            int timestamp = (int) ( chunk.getLastSavedTimestamp() / 1000 );
            randomFile.skipBytes( 4092 );
            randomFile.writeInt( timestamp );

            // Finally write out the actual chunk data:
            randomFile.seek( byteOffset );
            randomFile.writeInt( (int) byteLength );
            randomFile.writeByte( 0x02 );
            randomFile.write( nbtData );

            // Finish up by padding chunk data to 4KiB boundary:
            if ( ( byteLength & 4095 ) != 0 ) {
                byte[] zeroPad = new byte[4096 - (int) ( byteLength & 4095 )];
                randomFile.write( zeroPad );
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void closeFDs() {
        for ( RandomAccessFile fd : this.openFDs ) {
            try {
                fd.close();
            } catch ( IOException e ) {
                // Ignore
            }
        }
    }

}
