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

public interface ZLib {

    public int HARD_LIMIT = 64 * 1024 * 1024;

    void init( boolean compress, boolean gzip, int level );

    void free();

    void process(ByteBuffer in, ByteBuf out) throws DataFormatException;

}
