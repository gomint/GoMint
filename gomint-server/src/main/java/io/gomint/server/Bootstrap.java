/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import io.gomint.server.maintenance.ReportUploader;
import io.gomint.world.Biome;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This Bootstrap downloads all Libraries given inside of the "libs.dep" File in the Root
 * of the Application Workdir and then instanciates the Class which is given as Application
 * entry point.
 *
 * @author geNAZt
 * @version 1.0
 */
public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * Main entry point. May be used for custom dependency injection, dynamic
     * library class loaders and other experiments which need to be done before
     * the actual main entry point is executed.
     *
     * @param args The command-line arguments to be passed to the entryClass
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            bootstrap(args);
        } catch (Exception cause) {
            LOGGER.error("GoMint crashed: ", cause);
            reportExceptionToSentry(cause);
        } 
    }

    private static void reportExceptionToSentry(Exception cause) {
        ReportUploader.create()
            .exception(cause)
            .property("crash", "true")
            .upload();
    }

    private static void bootstrap(String[] args) throws Exception {
        var commandLineOptions = parseCommandLineOptions(args);

        enableNettyReflectionAccess();

        if (eventDebuggingWanted()) {
            enableEventDebugging();
        }
        
        if (networkDebuggingWanted(commandLineOptions)) {
            enableNetworkDebugging();
        }

        disableSecurityManager();
        assignCustomHTTPAgent();

        var server = new GoMintServer();
        server.startAfterRegistryInit(commandLineOptions);
    }

    private static void enableNettyReflectionAccess() {
        System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        System.setProperty("io.netty.maxDirectMemory", "0");
    }

    private static boolean eventDebuggingWanted() {
        return "true".equals(System.getProperty("gomint.debug_events"));
    }

    private static void enableEventDebugging() {
       Configurator.setLevel("io.gomint.server.event.EventHandlerList", Level.DEBUG); 
    }

    private static boolean networkDebuggingWanted(OptionSet options) {
        return options.has("dbg-net");
    }

    private static void enableNetworkDebugging() {
        Configurator.setLevel("io.gomint.server.network.NetworkManager", Level.TRACE);
    }

    private static void disableSecurityManager() {
        System.setSecurityManager(null);
    }

    private static void assignCustomHTTPAgent() {
        System.setProperty("http.agent", "GoMint/1.0");
    }

    private static OptionSet parseCommandLineOptions(String[] args) {
        var parser = new OptionParser();
        parser.accepts("lp").withRequiredArg().ofType(Integer.class);
        parser.accepts("lh").withRequiredArg();
        parser.accepts("slc");
        parser.accepts("dbg-net");
        parser.accepts("exit-after-boot");
        return parser.parse(args);
    }

}
