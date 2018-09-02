/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;

import io.gomint.emulator.client.Client;
import io.gomint.emulator.client.EncryptionHandler;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PostProcessWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger( PostProcessWorker.class );
    private static final NativeCode<ZLib> ZLIB = new NativeCode<>( "zlib", JavaZLib.class, NativeZLib.class );
    private static final ThreadLocal<ZLib> COMPRESSOR = new ThreadLocal<>();

    static {
        ZLIB.load();
    }

    private final Client connection;
    private final Packet[] packets;

    public PostProcessWorker( Client connection, Packet[] packets ) {
        this.connection = connection;
        this.packets = packets;
    }

    private ZLib getCompressor() {
        if ( COMPRESSOR.get() == null ) {
            ZLib zLib = ZLIB.newInstance();
            zLib.init( true, false, 7 );
            COMPRESSOR.set( zLib );
            return zLib;
        }

        return COMPRESSOR.get();
    }

    @Override
    public void run() {
        ZLib compressor = this.getCompressor();
        ByteBuf inBuf = PooledByteBufAllocator.DEFAULT.directBuffer();

        // Write all packets into the inBuf for compression
        for ( Packet packet : this.packets ) {
            PacketBuffer buffer = new PacketBuffer( 64 );
            packet.serializeHeader( buffer );
            packet.serialize( buffer, 282 );

            writeVarInt( buffer.getPosition(), inBuf );
            inBuf.writeBytes( buffer.getBuffer(), buffer.getBufferOffset(), buffer.getPosition() - buffer.getBufferOffset() );
        }

        // Create the output buffer
        ByteBuf outBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 8192 ); // We will write at least once so ensureWrite will realloc to 8192 so or so

        LOGGER.debug( "Compressing {} bytes", inBuf.readableBytes() );

        try {
            compressor.process( inBuf, outBuf );
        } catch ( Exception e ) {
            LOGGER.error( "Could not compress data for network", e );
            outBuf.release();
            return;
        } finally {
            inBuf.release();
        }

        byte[] data = new byte[outBuf.readableBytes()];
        outBuf.readBytes( data );
        outBuf.release();

        PacketBatch batch = new PacketBatch();
        batch.setPayload( data );

        EncryptionHandler encryptionHandler = this.connection.getEncryptionHandler();
        if ( encryptionHandler != null ) {
            batch.setPayload( encryptionHandler.encryptInputForServer( batch.getPayload() ) );
        }

        this.connection.send( batch );
    }

    private void writeVarInt( int value, ByteBuf stream ) {
        int copyValue = value;

        while ( ( copyValue & -128 ) != 0 ) {
            stream.writeByte( copyValue & 127 | 128 );
            copyValue >>>= 7;
        }

        stream.writeByte( copyValue );
    }

}
