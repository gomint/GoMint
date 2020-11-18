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
        String template = "/*\n" +
            " * Copyright (c) 2020 Gomint team\n" +
            " *\n" +
            " * This code is licensed under the BSD license found in the\n" +
            " * LICENSE file in the root directory of this source tree.\n" +
            " */\n" +
            "\n" +
            "package io.gomint.server.world.biome;\n" +
            "\n" +
            "import io.gomint.server.registry.RegisterInfo;\n" +
            "import io.gomint.server.world.biome.component.ClimateComponent;\n" +
            "\n" +
            "@RegisterInfo(sId = \"%BIOME_ID%\", id = %BIOME_PERSISTENT_ID%)\n" +
            "public class %BIOME_NAME% extends AbstractBiome {\n" +
            "\n" +
            "    /**\n" +
            "     * Construct the %BIOME_ID% biome config\n" +
            "     */\n" +
            "    public %BIOME_NAME%() {\n" +
            "        super(\n" +
            "            new ClimateComponent(%TEMPERATURE%, %DOWNFALL%),\n" +
            "            new GroundComponent(%MIN_EVALUATION%, %MAX_EVALUATION%),\n" +
            "        );\n" +
            "    }\n" +
            "\n" +
            "}\n";

        for (Biome value : Biome.values()) {
            value.getName()
        }

        // If not 32bit, print out a error and halt
        if (!System.getProperty("os.arch").contains("64")) {
            System.err.println("GoMint only support x86_64 (64bit) Java. Please install 64bit java");

            try {
                System.in.read();
            } catch (IOException e) {
                // Ignored
            }

            System.exit(-1);
        }

        // Enable reflection access to JDK NIO buffers for netty
        System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        System.setProperty("io.netty.maxDirectMemory", "0");

        // Setup additional debug
        if ("true".equals(System.getProperty("gomint.debug_events"))) {
            Configurator.setLevel("io.gomint.server.event.EventHandlerList", Level.DEBUG);
        }

        // Performance hacks
        System.setSecurityManager(null);

        // User agent
        System.setProperty("http.agent", "GoMint/1.0");

        // Parse options first
        OptionParser parser = new OptionParser();
        parser.accepts("lp").withRequiredArg().ofType(Integer.class);
        parser.accepts("lh").withRequiredArg();
        parser.accepts("slc");
        parser.accepts("dbg-net");
        parser.accepts("exit-after-boot");

        OptionSet options = parser.parse(args);

        // Set custom log level
        if (options.has("dbg-net")) {
            Configurator.setLevel("io.gomint.server.network.NetworkManager", Level.TRACE);
        }

        // Load the Class entrypoint
        try {
            GoMintServer server = new GoMintServer();
            server.startAfterRegistryInit(options);
        } catch (Throwable t) {
            LOGGER.error("GoMint crashed: ", t);
            ReportUploader.create().exception(t).property("crash", "true").upload();
        }
    }

}
