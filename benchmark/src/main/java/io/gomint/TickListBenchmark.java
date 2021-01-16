/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import io.gomint.math.BlockPosition;
import io.gomint.server.world.TickList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
public class TickListBenchmark {

    private static final BlockPosition POS = new BlockPosition(0, 0, 0);

    private class TickableBlockPosition implements Comparable<TickableBlockPosition> {

        private final long time;
        private final BlockPosition position;

        public TickableBlockPosition(long nextLong, BlockPosition blockPosition) {
            this.time = nextLong;
            this.position = blockPosition;
        }

        @Override
        public int compareTo(TickableBlockPosition o) {
            return Long.compare(this.time, o.time);
        }

    }

    private TickList tickList = new TickList();
    private PriorityQueue<TickableBlockPosition> priorityQueue = new PriorityQueue<>();

    @Benchmark
    public void fillTickList() {
        this.tickList.add(ThreadLocalRandom.current().nextBoolean() ? 1500 : 2500, POS);
    }

    @TearDown
    public void teardown() {
        this.priorityQueue.clear();
        this.tickList.clear();
    }

}
