/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCompressor extends MessageToByteEncoder<ByteBuf> {

    private static final NativeCode<ZLib> ZLIB_NATIVE = new NativeCode<>( "zlib", JavaZLib.class, NativeZLib.class );

    static {
        ZLIB_NATIVE.load();
    }

    private final ZLib zlib = ZLIB_NATIVE.newInstance();

    @Override
    public void handlerAdded( ChannelHandlerContext ctx ) throws Exception {
        zlib.init( true, true, 7 );
    }

    @Override
    public void handlerRemoved( ChannelHandlerContext ctx ) throws Exception {
        zlib.free();
    }

    @Override
    protected void encode( ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out ) throws Exception {
        int origSize = msg.readableBytes();
        if ( origSize < 256 ) {
            out.writeInt( 0 );
            out.writeBytes( msg );
        } else {
            out.writeInt( origSize );
            zlib.process( msg.nioBuffer(), out );
        }
    }

}
