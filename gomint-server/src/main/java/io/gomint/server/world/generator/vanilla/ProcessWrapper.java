/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ProcessWrapper {

    private final Process process;

    /**
     * Wrapper around a process which uses a thread to read stdout
     *
     * @param builder        which has all info needed to start the process
     * @param stdoutConsumer which gets called for every line stdout of the process emits
     * @throws IOException when process can't start
     */
    public ProcessWrapper(ProcessBuilder builder, Consumer<String> stdoutConsumer) throws IOException {
        this.process = builder.start();

        Thread stdReader = new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    stdoutConsumer.accept(line);
                }
            } catch (Exception e) {
                // Ignored
            }
        });
        stdReader.setDaemon(true);
        stdReader.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (ProcessWrapper.this.isAlive()) kill();
        }));
    }

    public boolean isAlive() {
        return this.process.isAlive();
    }

    public void kill() {
        this.process.destroyForcibly();
    }

}
