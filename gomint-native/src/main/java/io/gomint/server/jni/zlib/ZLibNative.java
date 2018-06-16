/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jni.zlib;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ZLibNative {

    int consumed;
    boolean finished;

    static
    {
        initFields();
    }

    static native void initFields();

    native void end(long ctx, boolean compress);

    native void reset(long ctx, boolean compress);

    native long init(boolean compress, int compressionLevel);

    native int process(long ctx, long in, int inLength, long out, int outLength, boolean compress);

}
