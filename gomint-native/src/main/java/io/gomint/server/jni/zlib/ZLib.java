/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jni.zlib;

import io.netty.buffer.ByteBuf;

import java.util.zip.DataFormatException;

public interface ZLib {

    void init( boolean compress, int level );

    void free();

    void process( ByteBuf in, ByteBuf out ) throws DataFormatException;

}
