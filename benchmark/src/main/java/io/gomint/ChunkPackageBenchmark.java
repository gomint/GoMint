/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import io.gomint.server.GoMintServer;
import io.gomint.server.network.packet.PacketWorldChunk;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkSlice;
import io.gomint.world.World;
import io.gomint.world.WorldType;
import io.gomint.world.generator.CreateOptions;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class ChunkPackageBenchmark {

    private AnnotationConfigApplicationContext context;
    private World world;
    private ChunkAdapter chunk;

    @Setup
    public void init() {
        OptionParser parser = new OptionParser();
        OptionSet options = parser.parse();

        context = new AnnotationConfigApplicationContext();
        context.registerBean(OptionSet.class, () -> options); // Register CLI options
        context.scan("io.gomint.server");

        context.refresh();

        GoMintServer server = context.getBean(GoMintServer.class);
        // server.startAfterRegistryInit(options);

        this.world = server.createWorld("test", new CreateOptions().worldType(WorldType.IN_MEMORY));

        this.chunk = (ChunkAdapter) this.world.getChunk(1,1);
        for (int i = 0; i < 16; i++) {
            ChunkSlice slice = this.chunk.ensureSlice(i);
            for (int ii = 0; ii < 4096; ii++) {
                slice.setRuntimeIdInternal((short) ii, 0, ii);
            }
        }
    }

    @Benchmark
    public PacketWorldChunk packageChunk() {
        PacketWorldChunk worldChunk = this.chunk.createPackagedData(null, false);
        worldChunk.release();
        return worldChunk;
    }

    @TearDown
    public void teardown() {
        GoMintServer server = context.getBean(GoMintServer.class);
        server.shutdown();

        context.close();
    }

}
