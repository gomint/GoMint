/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
public class RandomBenchmark {

    private int blockHash = ThreadLocalRandom.current().nextInt();
    private int lcg = blockHash;

    @Benchmark
    public int intShifted() {
        blockHash >>= 12;
        return blockHash & 0xfff;
    }

    @Benchmark
    public int intLCG() {
        lcg = lcg * 3 + 1013904223;
        return lcg >> 2;
    }

    @Benchmark
    public int intThreadLocalRandom() {
        return ThreadLocalRandom.current().nextInt(4096);
    }


}
