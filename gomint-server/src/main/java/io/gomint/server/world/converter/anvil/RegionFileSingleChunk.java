/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil;

import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class RegionFileSingleChunk {

    private static final NativeCode<ZLib> ZLIB = new NativeCode<>( "zlib", JavaZLib.class, NativeZLib.class );
    private static final ZLib DECOMPRESSOR;

    static {
        ZLIB.load();

        DECOMPRESSOR = ZLIB.newInstance();
        DECOMPRESSOR.init( false, false, 7 );
    }

    private final RandomAccessFile regionFile;
    private final int[][] lookupCache = new int[1024][];

    /**
     * Constructs a new region file that will load from the specified file.
     *
     * @param file The file to load
     * @throws IOException Thrown in case the file was not found / could not be opened
     */
    RegionFileSingleChunk( File file ) throws IOException {
        this.regionFile = new RandomAccessFile( file, "r" );

        if ( this.regionFile.length() < 8192 ) {
            // Add the region file metadata table / header:
            byte[] header = new byte[8192];
            this.regionFile.seek( 0L );
            this.regionFile.write( header );
        }

        // Read offset header
        for ( int i = 0; i < 1024; i++ ) {
            // First int is the offset in the file
            byte[] buffer = new byte[4];
            this.regionFile.read( buffer, 0, 3 );

            int offset = ( ( (int) buffer[0] << 16 & 0xFF0000 ) | ( (int) buffer[1] << 8 & 0xFF00 ) | ( (int) buffer[2] & 0xFF ) );
            int length = this.regionFile.read();

            this.lookupCache[i] = new int[]{ offset, length };
        }
    }

    /**
     * Attempts to load an anvil chunk from the NBT data found inside this region file
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return The chunk if found or null
     * @throws IOException Thrown in case an I/O error occurs
     */
    NBTTagCompound loadChunk( int x, int z ) throws IOException {
        int chunkIndex = ( ( x & 31 ) + ( ( z & 31 ) << 5 ) );
        int[] lookup = this.lookupCache[chunkIndex];

        int offset = lookup[0];
        int length = lookup[1];

        if ( offset == 0 || length == 0 ) {
            return null;
        }

        long fileOffset = (long) offset << 12;

        byte[] buffer = new byte[4];
        this.regionFile.seek( fileOffset );
        this.regionFile.read( buffer, 0, 4 );

        int exactLength = ( ( (int) buffer[0] << 24 & 0xFF000000 ) | ( (int) buffer[1] << 16 & 0xFF0000 ) | ( (int) buffer[2] << 8 & 0xFF00 ) | ( (int) buffer[3] & 0xFF ) );
        int compressionScheme = this.regionFile.read();
        byte[] nbtBuffer = new byte[exactLength];
        this.regionFile.read( nbtBuffer );

        InputStream input;
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
                ByteBuf inBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
                inBuf.writeBytes( nbtBuffer );

                ByteBuf outBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 8192 ); // We will write at least once so ensureWrite will realloc to 8192 so or so

                try {
                    DECOMPRESSOR.process( inBuf, outBuf );
                } catch ( Exception e ) {
                    e.printStackTrace();
                    outBuf.release();
                    return null;
                } finally {
                    inBuf.release();
                }

                byte[] data = new byte[outBuf.readableBytes()];
                outBuf.readBytes( data );
                outBuf.release();

                input = new ByteArrayInputStream( data );
                break;

            default:
                throw new IOException( "Unsupported compression scheme for chunk data (" + compressionScheme + ")" );
        }

        NBTReader nbtStream = new NBTReader( input, ByteOrder.BIG_ENDIAN );
        return nbtStream.parse();
    }

    public void close() {
        try {
            this.regionFile.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
