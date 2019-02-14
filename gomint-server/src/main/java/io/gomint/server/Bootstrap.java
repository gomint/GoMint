/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import io.gomint.server.maintenance.ReportUploader;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

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
    public static void main(String[] args) {
        // If not 32bit, print out a error and halt
        if (!"amd64".equals(System.getProperty("os.arch"))) {
            System.err.println("GoMint only support x86_64 (64bit) Java. Please install 64bit java");

            try {
                System.in.read();
            } catch (IOException ignored) {
            }

            System.exit(-1);
        }

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
        parser.accepts("convertOnly", "Scans root dir for folders with a anvil based world in it and converts it. After converting the process dies");

        OptionSet options = parser.parse(args);

        // Set custom log level
        if (options.has("dbg-net")) {
            Configurator.setLevel("io.gomint.server.network.NetworkManager", Level.TRACE);
        }

        // Check if we need to create the libs Folder
        File libsFolder = new File("libs/");
        if (libsFolder.exists()) {
            File[] files = libsFolder.listFiles();
            if (files == null) {
                LOGGER.error("Library Directory is corrupted");
                System.exit(-1);
            }
          

            // Scan the libs/ Directory for .jar Files
            for (File file : files) {
                if (file.getAbsolutePath().endsWith(".jar")) {
                    try {
                        LOGGER.info("Loading lib: " + file.getAbsolutePath());
                        addJARToClasspath(file);
                    } catch (IOException e) {
                        LOGGER.warn("Error attaching library to system classpath: ", e);
                    }
                }
            }
        }

        // Load the Class entrypoint
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.registerBean(OptionSet.class, () -> options); // Register CLI options
            context.scan("io.gomint.server");

            context.refresh();

            GoMintServer server = context.getBean(GoMintServer.class);
            server.startAfterRegistryInit(options);
        } catch (Throwable t) {
            LOGGER.error("GoMint crashed: ", t);
            ReportUploader.create().exception(t).property("crash", "true").upload();
        }
    }

    /**
     * Appends a JAR into the System Classloader
     *
     * @param moduleFile which should be added to the classpath
     * @throws IOException
     */
    private static void addJARToClasspath(File moduleFile) throws IOException {
        URL moduleURL = moduleFile.toURI().toURL();

        // Check if classloader has been changed (it should be a URLClassLoader)
        if (!(ClassLoader.getSystemClassLoader() instanceof URLClassLoader)) {
            // This is invalid for Java 9/10, they use a UCP inside a wrapper loader
            try {
                Field ucpField = ClassLoader.getSystemClassLoader().getClass().getDeclaredField("ucp");
                ucpField.setAccessible(true);

                Object ucp = ucpField.get(ClassLoader.getSystemClassLoader());
                Method addURLucp = ucp.getClass().getDeclaredMethod("addURL", URL.class);
                addURLucp.invoke(ucp, moduleURL);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Class[] parameters = new Class[]{URL.class};

            ClassLoader sysloader = ClassLoader.getSystemClassLoader();
            Class sysclass = URLClassLoader.class;

            try {
                Method method = sysclass.getDeclaredMethod("addURL", parameters);
                method.setAccessible(true);
                method.invoke(sysloader, moduleURL);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IOException("Error, could not add URL to system classloader");
            }
        }
    }

}
