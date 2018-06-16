/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author geNAZt
 * @version 1.0
 *
 * This is needed since a single thread can only handle ~150 players on a ~4.2 ghz ish 6700k. To handle more player than
 * that we need more threads to decompress stuff. Problem is we need to do this in order for each connection so we can't
 * use normal executor services because then packets would get out of order. We need to ping users to certain executors.
 */
public class PostProcessExecutorService {

    private List<PostProcessExecutor> executors = new CopyOnWriteArrayList<>();

    public PostProcessExecutor getExecutor() {
        for ( PostProcessExecutor executor : this.executors ) {
            if ( executor.getConnectionsInUse().get() < 100 ) {
                executor.getConnectionsInUse().incrementAndGet();
                return executor;
            }
        }

        PostProcessExecutor executor = new PostProcessExecutor();
        executor.getConnectionsInUse().incrementAndGet();
        this.executors.add( executor );
        return executor;
    }

    public void releaseExecutor( PostProcessExecutor executor ) {
        executor.getConnectionsInUse().decrementAndGet();
    }

}
