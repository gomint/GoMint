/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import com.google.common.base.Preconditions;
import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketDecompressor extends MessageToMessageDecoder<ByteBuf> {

    private static final NativeCode<ZLib> ZLIB_NATIVE = new NativeCode<>( "zlib", JavaZLib.class, NativeZLib.class );

    static {
        ZLIB_NATIVE.load();
    }

    private final ZLib zlib = ZLIB_NATIVE.newInstance();

    @Override
    public void handlerAdded( ChannelHandlerContext ctx ) throws Exception {
        zlib.init( false, true, 0 );
    }

    @Override
    public void handlerRemoved( ChannelHandlerContext ctx ) throws Exception {
        zlib.free();
    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) throws Exception {
        int size = in.readInt();
        if ( size == 0 ) {
            out.add( in.slice().retain() );
            in.skipBytes( in.readableBytes() );
        } else {
            ByteBuf decompressed = ctx.alloc().directBuffer();

            try {
                zlib.process( in.nioBuffer(), decompressed );
                Preconditions.checkState( decompressed.readableBytes() == size, "Decompressed packet size mismatch" );

                out.add( decompressed );
                decompressed = null;
            } finally {
                if ( decompressed != null ) {
                    decompressed.release();
                }
            }
        }
    }

}
