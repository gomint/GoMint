/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * Cache used for subchunk / biome data
 */
public class Cache {

    private static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);
    private final Long2ObjectOpenHashMap<ByteBuf> cacheContent = new Long2ObjectOpenHashMap<>();

    public long add(ByteBuf buf) {
        // Hash the buf
        long hash = XXHash.hash(buf, 0);

        if (!this.cacheContent.containsKey(hash)) {
            this.cacheContent.put(hash, buf);
        }

        return hash;
    }

    public void remove(long hash) {
        ByteBuf buf = this.cacheContent.remove(hash);
        if ( buf != null ) {
            buf.release();
        }
    }

    public ByteBuf get(long hash) {
        ByteBuf buf = this.cacheContent.remove(hash);
        if (buf == null) {
            // Already delivered
            return null;
        }

        if (XXHash.hash(buf, 0) != hash) {
            LOGGER.warn("Blob mismatch hash {}", hash);
            return null;
        }

        return buf;
    }

    public boolean isEmpty() {
        return this.cacheContent.size() == 0;
    }

}
