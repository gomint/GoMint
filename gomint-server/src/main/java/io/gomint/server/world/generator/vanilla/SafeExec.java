/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import io.gomint.server.GoMintServer;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@Component
public class SafeExec {

    private final GoMintServer server;
    private String javaStarter;
    private Process process;
    private Map<Long, Consumer<String>> outputConsumer = new ConcurrentHashMap<>();

    private final Object sync = new Object();
    private long processId;

    /**
     * Build up a new safe executor for third party programs
     *
     * @param server which started this
     */
    public SafeExec(GoMintServer server) {
        this.server = server;

        String javaHome = System.getProperty("java.home");
        File javaHomeFolder = new File(javaHome);
        File binFolder = new File(javaHomeFolder, "bin");
        File javaExe = new File(binFolder, "java.exe");
        if (javaExe.exists()) {
            this.javaStarter = javaExe.getAbsolutePath();
        } else {
            File javaBinary = new File(binFolder, "java");
            if (javaBinary.exists()) {
                this.javaStarter = javaBinary.getAbsolutePath();
            }
        }
    }

    private synchronized void ensureStarted() {
        if (this.process == null) {
            // Check if temp is healthy
            File temp = new File("temp");
            if (!temp.exists()) {
                temp.mkdirs();
            }

            File safeExec = new File(temp, "safeexec.jar");
            if (!safeExec.exists()) {
                // Get safeexec.jar from resource folder
                try (InputStream in = SafeExec.class.getResourceAsStream("/safeexec.jar")) {
                    Files.copy(in, safeExec.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            // Start the new executor
            ProcessBuilder builder = new ProcessBuilder(this.javaStarter, "-jar", safeExec.getAbsolutePath());

            try {
                this.process = builder.start();

                Thread stdReader = new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            if (line.startsWith("started:")) {
                                this.processId = Long.parseLong(line.substring("started:".length()));
                                synchronized (this.sync) {
                                    this.sync.notify();
                                }
                            } else {
                                for (Map.Entry<Long, Consumer<String>> entry : this.outputConsumer.entrySet()) {
                                    if (line.startsWith(entry.getKey() + ":")) {
                                        entry.getValue().accept(line.replace(entry.getKey() + ":", "").trim());
                                    }
                                }
                            }
                        }
                    } catch (IOException ignored) {

                    }
                });

                stdReader.setDaemon(true);
                stdReader.start();

                ListeningScheduledExecutorService service = this.server.getExecutorService();
                service.scheduleAtFixedRate(() -> {
                    if (this.process.isAlive()) {
                        try {
                            OutputStream stdin = this.process.getOutputStream();
                            stdin.write("ping\n".getBytes());
                            stdin.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 500, 500, TimeUnit.MILLISECONDS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public long exec(String cmd, String workPath, Consumer<String> stdoutConsumer) {
        this.ensureStarted();

        try {
            String startLine = ("exec w=\"" + workPath.replace("\\", "\\\\") + "\" c=\"" + cmd.replace("\\", "\\\\") + "\"\n");

            OutputStream stdin = this.process.getOutputStream();
            stdin.write(startLine.getBytes());
            stdin.flush();

            synchronized (this.sync) {
                this.sync.wait();
                this.outputConsumer.put(this.processId, stdoutConsumer);
            }

            return this.processId;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void stop(long processId) {
        if (processId != -1) {
            this.outputConsumer.remove(processId);

            String stopLine = ("stop " + processId + "\n");

            try {
                OutputStream stdin = this.process.getOutputStream();
                stdin.write(stopLine.getBytes());
                stdin.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
