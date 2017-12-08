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
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class RegionFile {

    private final AnvilWorldAdapter world;
    private final RandomAccessFile file;
    private final Map<Integer, int[]> lookupCache = new HashMap<>();

    // Caches
    private final byte[] decompressBuffer = new byte[64 * 1024];
    private final ByteArrayOutputStream decompressOutput = new ByteArrayOutputStream();
    private final Inflater inflater = new Inflater();
    private final Inflater gzipInflater = new Inflater( true );

    /**
     * Constructs a new region file that will load from the specified file.
     *
     * @param world The world the region file belongs to
     * @param file  The file to load
     * @throws IOException Thrown in case the file was not found / could not be opened
     */
    RegionFile( AnvilWorldAdapter world, File file ) throws IOException {
        this.world = world;
        this.file = new RandomAccessFile( file, "rw" );

        if ( this.file.length() < 8192 ) {
            // Add the region file metadata table / header:
            byte[] header = new byte[8192];
            this.file.seek( 0L );
            this.file.write( header );
        }

        // Read offset header
        for ( int i = 0; i < 1024; i++ ) {
            // First int is the offset in the file
            byte[] buffer = new byte[4];
            this.file.read( buffer, 0, 3 );

            int offset = ( ( (int) buffer[0] << 16 & 0xFF0000 ) | ( (int) buffer[1] << 8 & 0xFF00 ) | ( (int) buffer[2] & 0xFF ) );
            int length = this.file.read();

            this.lookupCache.put( i, new int[]{ offset, length } );
        }
    }

    /**
     * Attempts to load an anvil chunk from the NBT data found inside this region file
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return The chunk if found
     * @throws IOException        Thrown in case an I/O error occurs or the chunk was not found
     * @throws WorldLoadException Thrown in the case that the chunk loaded was corrupted
     */
    AnvilChunkAdapter loadChunk( int x, int z ) throws IOException, WorldLoadException {
        int chunkIndex = ( ( x & 31 ) + ( ( z & 31 ) << 5 ) );
        int[] lookup = this.lookupCache.get( chunkIndex );

        int offset = lookup[0];
        int length = lookup[1];

        if ( offset == 0 || length == 0 ) {
            throw new IOException( "Chunk not found inside region" );
        }

        long fileOffset = (long) offset << 12;

        // Seek chunk data:
        byte[] buffer = new byte[4];
        this.file.seek( fileOffset );
        this.file.read( buffer, 0, 4 );

        int exactLength = ( ( (int) buffer[0] << 24 & 0xFF000000 ) | ( (int) buffer[1] << 16 & 0xFF0000 ) | ( (int) buffer[2] << 8 & 0xFF00 ) | ( (int) buffer[3] & 0xFF ) );
        int compressionScheme = this.file.read();
        byte[] nbtBuffer = new byte[exactLength];
        this.file.read( nbtBuffer );

        InputStream input;
        switch ( compressionScheme ) {
            case 1:
                this.gzipInflater.setInput( nbtBuffer );

                this.decompressOutput.reset();
                try {
                    int amountOfBytesDecompressed = -1;
                    while ( ( amountOfBytesDecompressed = this.gzipInflater.inflate( this.decompressBuffer ) ) > 0 ) {
                        this.decompressOutput.write( this.decompressBuffer, 0, amountOfBytesDecompressed );
                    }
                } catch ( DataFormatException e ) {
                    throw new IOException( "Could not decompress data due to zlib error: ", e );
                }

                this.gzipInflater.reset();
                input = new ByteArrayInputStream( this.decompressOutput.toByteArray() );
                break;

            case 2:
                this.inflater.setInput( nbtBuffer );

                this.decompressOutput.reset();
                try {
                    int amountOfBytesDecompressed = -1;
                    while ( ( amountOfBytesDecompressed = this.inflater.inflate( this.decompressBuffer ) ) > 0 ) {
                        this.decompressOutput.write( this.decompressBuffer, 0, amountOfBytesDecompressed );
                    }
                } catch ( DataFormatException e ) {
                    throw new IOException( "Could not decompress data due to zlib error: ", e );
                }

                this.inflater.reset();
                input = new ByteArrayInputStream( this.decompressOutput.toByteArray() );
                break;

            default:
                throw new IOException( "Unsupported compression scheme for chunk data (" + compressionScheme + ")" );
        }

        NBTStream nbtStream = new NBTStream( input, ByteOrder.BIG_ENDIAN );
        AnvilChunkAdapter chunkAdapter = new AnvilChunkAdapter( this.world, x, z );
        chunkAdapter.loadFromNBT( nbtStream );
        return chunkAdapter;
    }

    /**
     * Write a chunk into this regionfile
     *
     * @param chunk The chunk which should be written to the region file
     * @throws IOException A exception which get thrown when a I/O error occurred
     */
    void saveChunk( AnvilChunkAdapter chunk ) throws IOException {
        int x = chunk.getX();
        int z = chunk.getZ();

        int chunkIndex = ( ( x & 31 ) + ( ( z & 31 ) << 5 ) );
        long fileOffset = chunkIndex << 2;

        // Navigate to entry in location table:
        this.file.seek( fileOffset );

        byte[] buffer = new byte[4];
        this.file.read( buffer, 0, 3 );

        int sectorOffset = ( ( (int) buffer[0] << 16 & 0xFF0000 ) | ( (int) buffer[1] << 8 & 0xFF00 ) | ( (int) buffer[2] & 0xFF ) );
        int sectorLength = this.file.read();

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
            byteOffset = this.file.length();
            if ( ( byteOffset & 4095 ) != 0 ) {
                throw new IOException( "Failed to save chunk: Misaligned region file" );
            }

            sectorOffset = (int) ( byteOffset >> 12 );
            sectorLength = (int) ( ( byteLength + 4095 ) >> 12 );
        } else {
            byteOffset = sectorOffset << 12;
        }

        // Write Metadata:
        this.file.seek( fileOffset );
        buffer[0] = (byte) ( ( sectorOffset >> 16 ) & 0xFF );
        buffer[1] = (byte) ( ( sectorOffset >> 8 ) & 0xFF );
        buffer[2] = (byte) ( sectorOffset & 0xFF );
        buffer[3] = (byte) sectorLength;

        this.file.write( buffer );

        // Update cache
        this.lookupCache.put( chunkIndex, new int[]{ sectorOffset, sectorLength } );

        // Adjust timestamp:
        int timestamp = (int) ( System.currentTimeMillis() / 1000 );
        this.file.skipBytes( 4092 );
        this.file.writeInt( timestamp );

        // Finally write out the actual chunk data:
        this.file.seek( byteOffset );
        this.file.writeInt( (int) byteLength );
        this.file.writeByte( 0x02 );
        this.file.write( nbtData );

        // Finish up by padding chunk data to 4KiB boundary:
        if ( ( byteLength & 4095 ) != 0 ) {
            byte[] zeroPad = new byte[4096 - (int) ( byteLength & 4095 )];
            this.file.write( zeroPad );
        }
    }

}
