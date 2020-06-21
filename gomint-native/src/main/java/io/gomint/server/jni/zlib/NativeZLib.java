/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jni.zlib;

import com.google.common.base.Preconditions;
import io.gomint.server.jni.exception.NativeException;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NativeZLib implements ZLib {

    private static final Logger LOGGER = LoggerFactory.getLogger( NativeZLib.class );
    private final ZLibNative nativeCompress = new ZLibNative();

    /*============================================================================*/
    private boolean compress;
    private long ctx;

    @Override
    public void init( boolean compress, boolean gzip, int level ) {
        free();

        this.compress = compress;
        this.ctx = nativeCompress.init( compress, gzip, level );
    }

    @Override
    public void free() {
        if ( ctx != 0 ) {
            nativeCompress.end( ctx, compress );
            ctx = 0;
        }

        nativeCompress.consumed = 0;
        nativeCompress.finished = false;
    }

    @Override
    public void process(ByteBuffer in, ByteBuf out ) throws DataFormatException {
        // Smoke tests
        if (!in.isDirect()) {
            throw new IllegalArgumentException("input is not a direct buffer");
        }

        out.memoryAddress();
        Preconditions.checkState( ctx != 0, "Invalid pointer to compress!" );

        long getBaseAddress;
        try {
            getBaseAddress = getAddress(in);
        } catch (Exception e) {
            throw new DataFormatException("could not get input base address");
        }

        try {
            while ( !nativeCompress.finished && ( compress || in.remaining() > 0 ) ) {
                out.ensureWritable( 8192 );

                int processed = nativeCompress.process( ctx, getBaseAddress + in.position(), in.remaining(), out.memoryAddress() + out.writerIndex(), out.writableBytes(), compress );

                in.position( in.position() + nativeCompress.consumed );
                out.writerIndex( out.writerIndex() + processed );

                // Check for hard limit
                if ( out.writerIndex() > ZLib.HARD_LIMIT ) {
                    throw new DataFormatException( "Hard limit of 64 MB reached" );
                }
            }
        } catch ( NativeException e ) {
            LOGGER.error( "Native compression error", e );
            throw new DataFormatException( "Error in native compression" );
        } finally {
            nativeCompress.reset( ctx, compress );
            nativeCompress.consumed = 0;
            nativeCompress.finished = false;
        }
    }

    private long getAddress(ByteBuffer in) {
        return PlatformDependent.directBufferAddress(in);
    }

}
