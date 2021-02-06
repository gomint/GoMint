/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.test;

import io.gomint.server.world.WorldAdapter;
import io.gomint.world.World;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Janmm14
 * @version 2.0
 */
public class WorldTestUtil {
    
    private WorldTestUtil() {
        throw new UnsupportedOperationException();
    }
    
    public interface ThrowingRunnable {
        void run() throws Throwable;
    }
    
    public static void runInWorldThread(World w, ThrowingRunnable runnable) {
        WorldAdapter world = (WorldAdapter) w;
        if (world.mainThread()) {
            try {
                runnable.run();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        } else {
            Object waitObj = new Object();
            AtomicBoolean done = new AtomicBoolean();
            AtomicReference<Throwable> exception = new AtomicReference<>();
            world.syncScheduler().execute(() -> {
                try {
                    runnable.run();
                } catch (Throwable t) {
                    synchronized (waitObj) {
                        exception.set(t);
                    }
                } finally {
                    synchronized (waitObj) {
                        done.set(true);
                        waitObj.notify();
                    }
                }
            });
            synchronized (waitObj) {
                if (!done.get()) {
                    try {
                        waitObj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    final Throwable throwable = exception.get();
                    if (throwable != null) {
                        throw new RuntimeException(throwable);
                    }
                }
            }
        }
    }
    
    public static void blockUntilWorldRuns(World w) {
        WorldAdapter world = (WorldAdapter) w;
        Object waitObj = new Object();
        AtomicBoolean done = new AtomicBoolean();
        world.syncScheduler().execute(() -> {
            synchronized (waitObj) {
                done.set(true);
                waitObj.notify();
            }
        });
        synchronized (waitObj) {
            if (!done.get()) {
                try {
                    waitObj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
