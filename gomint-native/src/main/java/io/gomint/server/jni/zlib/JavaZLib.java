/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jni.zlib;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author geNAZt
 * @version 1.0
 */
public class JavaZLib implements ZLib {

    private final byte[] buffer = new byte[8192];
    //
    private boolean compress;
    private Deflater deflater;
    private Inflater inflater;

    @Override
    public void init( boolean compress, boolean gzip, int level ) {
        this.compress = compress;
        free();

        if ( compress ) {
            deflater = new Deflater( level, gzip );
        } else {
            inflater = new Inflater( gzip );
        }
    }

    @Override
    public void free() {
        if ( deflater != null ) {
            deflater.end();
        }

        if ( inflater != null ) {
            inflater.end();
        }
    }

    @Override
    public void process( ByteBuffer in, ByteBuf out ) throws DataFormatException {
        if ( compress ) {
            try {
                deflater.setInput( in );
                deflater.finish();

                while ( !deflater.finished() ) {
                    int count = deflater.deflate( buffer );
                    out.writeBytes( buffer, 0, count );

                    // Check for hard limit
                    if ( out.writerIndex() > ZLib.HARD_LIMIT ) {
                        throw new DataFormatException( "Hard limit of 64 MB reached" );
                    }
                }
            } finally {
                deflater.reset();
            }
        } else {
            try {
                inflater.setInput( in );

                int needed = in.remaining();
                while ( !inflater.finished() && inflater.getTotalIn() < needed ) {
                    int count = inflater.inflate( buffer );
                    out.writeBytes( buffer, 0, count );

                    // Check for hard limit
                    if ( out.writerIndex() > ZLib.HARD_LIMIT ) {
                        throw new DataFormatException( "Hard limit of 64 MB reached" );
                    }
                }
            } finally {
                inflater.reset();
            }
        }
    }

}
