/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.maintenance;

import io.gomint.GoMint;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.maintenance.report.PlayerReportData;
import io.gomint.server.maintenance.report.WorldData;
import io.gomint.server.plugin.PluginClassloader;
import io.gomint.server.world.WorldAdapter;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.interfaces.ExceptionInterface;
import io.sentry.event.interfaces.SentryException;
import io.sentry.event.interfaces.SentryStackTraceElement;
import io.sentry.event.interfaces.StackTraceInterface;
import oshi.SystemInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public final class ReportUploader {

    private static String HOST;

    static {
        try {
            HOST = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            HOST = "UNKNOWN";
        }
    }

    private final SentryClient client;
    private final Context context;

    private Map<String, WorldData> worlds = new HashMap<>();
    private Map<String, PlayerReportData> players = new HashMap<>();
    private Throwable exception = null;
    private StackTraceElement[] stacktrace = null;

    private ReportUploader() {
        // Setup sentry
        System.setProperty("stacktrace.app.packages", "");

        this.client = SentryClientFactory.sentryClient("https://e5fb5572f7e849b8b4a6fd80e3fa0ebc@o219027.ingest.sentry.io/1362506?async=true");
        this.client.setRelease(((GoMintServer) GoMint.instance()).gitHash());
        this.client.setServerName(HOST);

        this.context = this.client.getContext();
        this.context.addTag("java_version", System.getProperty("java.vm.name") + " (" + System.getProperty("java.runtime.version") + ")");

        // Ask for OS and CPU info
        SystemInfo systemInfo = new SystemInfo();
        this.context.addExtra("system.os", systemInfo.getOperatingSystem().getFamily() + " [" + systemInfo.getOperatingSystem().getVersionInfo().getVersion() + "]");
        this.context.addExtra("system.memory", getCount(systemInfo.getHardware().getMemory().getTotal()));
        this.context.addExtra("system.cpu", systemInfo.getHardware().getProcessor().getProcessorIdentifier().getName());

        // Basic process stats
        this.context.addExtra("system.process_memory_total", getCount(Runtime.getRuntime().totalMemory()));
        this.context.addExtra("system.process_memory_free", getCount(Runtime.getRuntime().freeMemory()));

        this.context.addExtra("system.current_thread", Thread.currentThread().getName());
    }

    private static String getCount(long bytes) {
        // Do we need to check for suffix
        if (bytes < 1024) {
            return bytes + " B";
        }

        // Get exp and get the correct suffix
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %siB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Include world data
     *
     * @return the report uploader for chaining
     */
    public ReportUploader includeWorlds() {
        GoMintServer server = (GoMintServer) GoMint.instance();
        for (WorldAdapter adapter : server.worldManager().worlds()) {
            this.worlds.put(adapter.folder(), new WorldData(adapter.chunkCache().size()));
        }

        return this;
    }

    /**
     * Include player data
     *
     * @return the report uploader for chaining
     */
    public ReportUploader includePlayers() {
        for (EntityPlayer player : GoMint.instance().onlinePlayers()) {
            String key = player.name() + ":" + player.uuid().toString();
            Location location = player.location();
            this.players.put(key, new PlayerReportData(location.world().folder(), location.x(), location.y(), location.z()));
        }

        return this;
    }

    /**
     * Convert a exception to a string and append it to the report
     *
     * @param exception which should be reported
     * @return the report uploader for chaining
     */
    public ReportUploader exception(Throwable exception) {
        this.exception = exception;
        return this;
    }

    /**
     * Add a new property to this upload request
     *
     * @param key   of the property
     * @param value of the property
     * @return the report uploader for chaining
     */
    public ReportUploader property(String key, String value) {
        this.context.addExtra("property." + key, value);
        return this;
    }

    public void upload() {
        this.upload(null);
    }

    /**
     * Upload this report
     *
     * @param message which should be used if no additional data has been given
     */
    public void upload(String message) {
        // Check if reporting has been disabled
        GoMintServer server = (GoMintServer) GoMint.instance();
        this.context.addExtra("config.server", server.serverConfig());

        if (this.worlds.size() > 0) {
            this.worlds.forEach((worldName, worldData) -> this.context.addExtra("world." + worldName, worldData));
        }

        if (this.players.size() > 0) {
            this.players.forEach((playerName, playerData) -> this.context.addExtra("player." + playerName, playerData));
        }

        // Check for plugin crashes
        this.client.addBuilderHelper(eventBuilder -> eventBuilder.getEvent().getSentryInterfaces().values().forEach(sentryInterface -> {
            if (sentryInterface instanceof ExceptionInterface) {
                Deque<SentryException> throwables = ((ExceptionInterface) sentryInterface).getExceptions();
                for (SentryException throwable : throwables) {
                    for (SentryStackTraceElement traceElement : throwable.getStackTraceInterface().getStackTrace()) {
                        String plugin = PluginClassloader.getPluginWhichLoaded(traceElement.getModule());
                        if (plugin != null) {
                            eventBuilder.withTag("plugin.crash", "true");
                            eventBuilder.withExtra("plugin", plugin);
                            return;
                        }
                    }
                }
            }
        }));

        // Only send with supported release
        this.client.addShouldSendEventCallback(event -> !event.getRelease().equals("dev/unsupported"));

        if (this.exception != null) {
            this.client.sendException(this.exception);
        } else if (this.stacktrace != null) {
            this.client.addBuilderHelper(eventBuilder -> eventBuilder.withSentryInterface(new StackTraceInterface(this.stacktrace)));
            this.client.sendMessage(message == null ? "Nulled message" : message);
        } else {
            this.client.sendMessage(message == null ? "Nulled message" : message);
        }
    }

    /**
     * Create a new report uploader
     *
     * @return a report uploader
     */
    public static ReportUploader create() {
        return new ReportUploader();
    }

    public ReportUploader tag(String tag) {
        this.context.addTag(tag, "true");
        return this;
    }

    public ReportUploader stacktrace(StackTraceElement[] stackTrace) {
        this.stacktrace = stackTrace;
        return this;
    }

}
