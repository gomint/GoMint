/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import io.gomint.command.Command;
import io.gomint.event.Event;
import io.gomint.event.EventListener;
import io.gomint.event.plugin.PluginInstallEvent;
import io.gomint.event.plugin.PluginUninstallEvent;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginManager;
import io.gomint.plugin.PluginVersion;
import io.gomint.plugin.StartupPriority;
import io.gomint.plugin.injection.InjectPlugin;
import io.gomint.server.GoMintServer;
import io.gomint.server.command.CommandManager;
import io.gomint.server.event.EventManager;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.scheduler.CoreScheduler;
import io.gomint.server.scheduler.PluginScheduler;
import io.gomint.server.util.CallerDetectorUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Attribute;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.ModuleVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author geNAZt
 * @version 1.0
 */
@Component
public class SimplePluginManager implements PluginManager, EventCaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePluginManager.class);

    private final GoMintServer server;
    private final CoreScheduler scheduler;
    private final File pluginFolder;

    private final List<PluginMeta> detectedPlugins = new ArrayList<>();
    private final Map<String, Plugin> loadedPlugins = new LinkedHashMap<>();
    private final Map<String, Plugin> installedPlugins = new LinkedHashMap<>();
    private final Map<String, PluginMeta> metadata = new HashMap<>();

    private final EventManager eventManager = new EventManager();
    @Getter
    private final CommandManager commandManager;

    private Field loggerField;
    private Field nameField;
    private Field pluginManagerField;
    private Field versionField;
    private Field schedulerField;
    private Field serverField;
    private Field listenerListField;

    /**
     * Build a new plugin manager for detecting, loading and managing (install, uninstall) plugins
     *
     * @param server which started this manager
     */
    public SimplePluginManager(GoMintServer server) {
        this.server = server;
        this.scheduler = server.getScheduler();
        this.pluginFolder = new File("plugins");
        this.commandManager = new CommandManager();

        if (!this.pluginFolder.exists() && !this.pluginFolder.mkdirs()) {
            LOGGER.warn("Plugin folder was not there and could not be created, plugins will not be available");
        }

        // Prepare the field injections
        try {
            this.loggerField = Plugin.class.getDeclaredField("logger");
            this.loggerField.setAccessible(true);

            this.nameField = Plugin.class.getDeclaredField("name");
            this.nameField.setAccessible(true);

            this.pluginManagerField = Plugin.class.getDeclaredField("pluginManager");
            this.pluginManagerField.setAccessible(true);

            this.versionField = Plugin.class.getDeclaredField("version");
            this.versionField.setAccessible(true);

            this.schedulerField = Plugin.class.getDeclaredField("scheduler");
            this.schedulerField.setAccessible(true);

            this.serverField = Plugin.class.getDeclaredField("server");
            this.serverField.setAccessible(true);

            this.listenerListField = Plugin.class.getDeclaredField("listeners");
            this.listenerListField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            LOGGER.error("Could not reflect needed access into Plugin base class", e);
        }
    }

    public void detectPlugins() {
        // Search the plugins folder for valid .jar files
        for (File file : this.pluginFolder.listFiles(pathname -> (pathname.getAbsolutePath().endsWith(".jar")))) {
            PluginMeta metadata = getMetadata(file);
            if (metadata != null) {
                this.metadata.put(metadata.getName(), metadata);
                this.detectedPlugins.add(metadata);
            }
        }
    }

    /**
     * Load and startup plugins
     *
     * @param prio for which we want to load and startup plugins
     */
    public void loadPlugins(StartupPriority prio) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Loading all plugins which have start priority: {}", prio.name());
        }

        // Create a copy of the detected plugins
        for (PluginMeta pluginMeta : new ArrayList<>(this.detectedPlugins)) {
            if (!this.detectedPlugins.contains(pluginMeta)) {
                continue;
            }

            if (pluginMeta.getPriority() == prio) {
                LOGGER.info("Loading plugin {}", pluginMeta.getName());

                loadPlugin(pluginMeta);

                // Check if the plugin did shutdown the server
                if (!this.server.isRunning()) {
                    return;
                }
            }
        }
    }

    private void loadPlugin(PluginMeta pluginMeta) {
        // Check for depends
        if (pluginMeta.getDepends() != null && !pluginMeta.getDepends().isEmpty()) {
            for (String dependPlugin : pluginMeta.getDepends()) {
                // If the depend plugin is already loaded, skip it
                if (this.loadedPlugins.containsKey(dependPlugin)) {
                    continue;
                }

                LOGGER.info("Searching depend for {}: {}", pluginMeta.getName(), dependPlugin);

                // We need to check if the depend plugin is detected
                boolean found = false;
                for (PluginMeta detectedPlugin : new ArrayList<>(this.detectedPlugins)) {
                    if (detectedPlugin.getName().equals(dependPlugin)) {
                        loadPlugin(detectedPlugin);

                        // Check if the plugin did shutdown the server
                        if (!this.server.isRunning()) {
                            return;
                        }

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    LOGGER.warn("Could not load plugin {} since the depend {} could not be found", pluginMeta.getName(), dependPlugin);
                    this.metadata.remove(pluginMeta.getName());
                    return;
                }
            }
        }

        // Check for soft depends
        if (pluginMeta.getSoftDepends() != null && !pluginMeta.getSoftDepends().isEmpty()) {
            for (String dependPlugin : pluginMeta.getSoftDepends()) {
                // If the depend plugin is already loaded, skip it
                if (this.loadedPlugins.containsKey(dependPlugin)) {
                    continue;
                }

                // We need to check if the depend plugin is detected
                for (PluginMeta detectedPlugin : new ArrayList<>(this.detectedPlugins)) {
                    if (detectedPlugin.getName().equals(dependPlugin)) {
                        loadPlugin(pluginMeta);

                        // Check if the plugin did shutdown the server
                        if (!this.server.isRunning()) {
                            return;
                        }

                        break;
                    }
                }
            }
        }

        // Ok everything is fine now, load the plugin
        PluginClassloader loader = null;
        try {
            LOGGER.info("Starting to load plugin {}", pluginMeta.getName());
            loader = new PluginClassloader(pluginMeta);

            Path[] finderFiles = new Path[pluginMeta.getModuleDependencies() != null ? pluginMeta.getModuleDependencies().size() + 1 : 1];
            finderFiles[0] = pluginMeta.getPluginFile().toPath();
            if (pluginMeta.getModuleDependencies() != null) {
                int index = 1;
                for (File moduleDependency : pluginMeta.getModuleDependencies()) {
                    finderFiles[index++] = moduleDependency.toPath();
                }
            }

            ModuleFinder finder = ModuleFinder.of(finderFiles);
            ModuleFinder empty = ModuleFinder.of();
            ModuleLayer bootLayer = ModuleLayer.boot();

            Configuration configuration = bootLayer.configuration();
            Configuration newConfiguration = configuration.resolve(finder, empty, new HashSet<>() {{
                add(pluginMeta.getModuleName());
            }});

            PluginClassloader finalLoader = loader;
            ModuleLayer.Controller moduleLayerController = ModuleLayer.defineModules(newConfiguration, Collections.singletonList(bootLayer), s -> finalLoader);

            ModuleLayer moduleLayer = moduleLayerController.layer();
            Module pluginModule = moduleLayer.findModule(pluginMeta.getModuleName()).get();
            Module currentModule = SimplePluginManager.class.getModule();

            for (String aPackage : pluginMeta.getPackages()) {
                moduleLayerController.addExports(pluginModule, aPackage, currentModule);
                moduleLayerController.addOpens(pluginModule, aPackage, currentModule);
                moduleLayerController.addReads(pluginModule, currentModule);
                currentModule.addReads(pluginModule);
                currentModule.addExports("io.gomint.server.event", pluginModule);
            }

            ClassLoader mLoader = moduleLayer.findLoader(pluginMeta.getModuleName());

            Plugin clazz = (Plugin) constructAndInject(pluginMeta.getMainClass(), mLoader);
            if (clazz == null) {
                return;
            }

            // Reflect the logger and stuff in
            this.loggerField.set(clazz, LoggerFactory.getLogger(loader.loadClass(pluginMeta.getMainClass())));
            this.pluginManagerField.set(clazz, this);
            this.schedulerField.set(clazz, new PluginScheduler(clazz, this.scheduler));
            this.nameField.set(clazz, pluginMeta.getName());
            this.versionField.set(clazz, pluginMeta.getVersion());
            this.serverField.set(clazz, this.server);

            clazz.onStartup();

            this.loadedPlugins.put(pluginMeta.getName(), clazz);
            this.detectedPlugins.remove(pluginMeta);

            // Injection stuff
            if (pluginMeta.getInjectionCommands() != null) {
                for (String commandClass : pluginMeta.getInjectionCommands()) {
                    Object maybeCommand = constructAndInject(commandClass, loader);
                    if (maybeCommand instanceof Command) {
                        Command command = (Command) maybeCommand;
                        this.commandManager.register(clazz, command);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Error whilst starting plugin " + pluginMeta.getName(), e);
            this.metadata.remove(pluginMeta.getName());

            // Unload if needed
            if (loader != null) {
                loader.remove();
            }
        }
    }

    private Object constructAndInject(String clazz, ClassLoader loader) {
        try {
            Class<?> cl = loader.loadClass(clazz);

            try {
                Object built = cl.newInstance();

                // Check all fields for injection
                for (Field field : cl.getDeclaredFields()) {
                    // Is there @InjectPlugin present? If so, check for plugin and inject
                    if (field.isAnnotationPresent(InjectPlugin.class)) {
                        String plugin = field.getAnnotation(InjectPlugin.class).value();
                        if (plugin.equals("detect")) {
                            // Get the fields type
                            Class<?> type = field.getType();

                            // Check loaded plugins first
                            for (Plugin foundPlugin : this.loadedPlugins.values()) {
                                if (foundPlugin.getClass().equals(type)) {
                                    field.setAccessible(true);
                                    field.set(built, foundPlugin);
                                    break;
                                }
                            }
                        } else {
                            field.setAccessible(true);
                            field.set(built, getPlugin(plugin));
                        }
                    }
                }

                return built;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void installPlugins() {
        for (Map.Entry<String, Plugin> entry : this.loadedPlugins.entrySet()) {
            Plugin plugin = entry.getValue();
            String name = entry.getKey();

            try {
                LOGGER.info("Installing plugin {}", name);
                plugin.onInstall();
                this.installedPlugins.put(name, plugin);
                this.callEvent(new PluginInstallEvent(plugin));
            } catch (Exception e) {
                LOGGER.error("Plugin did startup but could not be installed: " + name, e);
                this.metadata.remove(plugin.getName());
                ReportUploader.create().exception(e).upload("Plugin could not be installed");
            }

            // Check if the plugin did shutdown the server
            if (!this.server.isRunning()) {
                return;
            }
        }

        this.loadedPlugins.clear();
    }

    private AnnotationVisitor readDependAnnotation(AtomicReference<Set<String>> dep) {
        return new AnnotationVisitor(Opcodes.ASM7) {
            @Override
            public AnnotationVisitor visitArray(String name) {
                dep.set(new HashSet<>());
                return new AnnotationVisitor(Opcodes.ASM7) {
                    @Override
                    public void visit(String name, Object value) {
                        dep.get().add((String) value);
                    }
                };
            }
        };
    }

    private PluginMeta getMetadata(File file) {
        // Open the jar
        try (JarFile jar = new JarFile(file)) {
            Enumeration<JarEntry> jarEntries = jar.entries();

            // It seems like the jar is empty
            if (jarEntries == null || !jarEntries.hasMoreElements()) {
                LOGGER.warn("Could not load Plugin. File {} is empty", file);
                return null;
            }

            PluginMeta meta = new PluginMeta(file);
            File depModules = new File("modules/plugin/" + file.getName().replace(".jar", ""));

            // Try to read every file in the jar
            try {
                while (jarEntries.hasMoreElements()) {
                    JarEntry jarEntry = jarEntries.nextElement();

                    // If we found a jar inside a jar extract it
                    if (jarEntry != null && jarEntry.getName().endsWith(".jar")) {
                        depModules.mkdirs(); // We simply ignore it, if the creation failed the extraction will error either way

                        File jarFile = Paths.get(depModules.getAbsolutePath(), jarEntry.getName()).toFile();
                        jarFile.getParentFile().mkdirs(); // Ignore, it will fail in the copy

                        try (FileOutputStream toFile = new FileOutputStream(jarFile)) {
                            jar.getInputStream(jarEntry).transferTo(toFile);
                        }

                        if ( meta.getModuleDependencies() == null ) {
                            meta.setModuleDependencies( new HashSet<>() );
                        }

                        meta.getModuleDependencies().add(jarFile);
                    }

                    // When the entry is valid and ends with a .class its a java class and we need to scan it
                    if (jarEntry != null && jarEntry.getName().endsWith(".class")) {
                        ClassReader cr = new ClassReader(new DataInputStream(jar.getInputStream(jarEntry)));

                        // Sort out *info.class files
                        if (cr.getSuperName() == null) {
                            cr.accept(new ClassVisitor(Opcodes.ASM7) {
                                @Override
                                public ModuleVisitor visitModule(String name, int access, String version) {
                                    meta.setModuleName(name);
                                    return super.visitModule(name, access, version);
                                }
                            }, 0);

                            continue;
                        } else {
                            cr.accept(new ClassVisitor(Opcodes.ASM7) {
                                @Override
                                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                                    String packageName = name.replace("/", ".");
                                    int lastIndex = packageName.lastIndexOf(".");
                                    packageName = packageName.substring(0, lastIndex);

                                    meta.addPackage(packageName);
                                    super.visit(version, access, name, signature, superName, interfaces);
                                }
                            }, 0);
                        }

                        // Does this class extend the plugin class?
                        if (cr.getSuperName().equals("io/gomint/plugin/Plugin")) {
                            final AtomicReference<String> name = new AtomicReference<>();
                            final AtomicReference<PluginVersion> version = new AtomicReference<>();
                            final AtomicReference<Set<String>> depends = new AtomicReference<>();
                            final AtomicReference<Set<String>> softDepends = new AtomicReference<>();
                            final AtomicReference<String> startup = new AtomicReference<>(StartupPriority.STARTUP.name());

                            cr.accept(new ClassVisitor(Opcodes.ASM7) {
                                @Override
                                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                                    switch (descriptor) {
                                        case "Lio/gomint/plugin/PluginName;":
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visit(String key, Object value) {
                                                    name.set((String) value);
                                                }
                                            };
                                        case "Lio/gomint/plugin/Version;":
                                            version.set(new PluginVersion());
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visit(String key, Object value) {
                                                    switch (key) {
                                                        case "major":
                                                            version.get().setMajor((Integer) value);
                                                            break;
                                                        case "minor":
                                                            version.get().setMinor((Integer) value);
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                }
                                            };
                                        case "Lio/gomint/plugin/Startup;":
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visitEnum(String name, String descriptor, String value) {
                                                    startup.set(value);
                                                }
                                            };
                                        case "Lio/gomint/plugin/Depends;":
                                            return readDependAnnotation(depends);
                                        case "Lio/gomint/plugin/Softdepends":
                                            return readDependAnnotation(softDepends);
                                        default:
                                            break;
                                    }

                                    return super.visitAnnotation(descriptor, visible);
                                }
                            }, 0);

                            // We at least need the name and the version of the plugin
                            if (name.get() == null || version.get() == null) {
                                LOGGER.warn("It seems like there is a plugin in the jar. But its missing the @Name or @Version annotation");
                                return null;
                            }

                            meta.setName(name.get());
                            meta.setVersion(version.get());
                            meta.setPriority(StartupPriority.valueOf(startup.get()));
                            meta.setDepends(depends.get());
                            meta.setSoftDepends(softDepends.get());
                            meta.setMainClass(cr.getClassName().replace("/", "."));
                        } else {
                            AtomicInteger neededArguments = new AtomicInteger();

                            // Ok it did, time to parse the needed and optional annotations
                            cr.accept(new ClassVisitor(Opcodes.ASM7) {
                                @Override
                                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                                    switch (descriptor) {
                                        case "Lio/gomint/command/annotation/Description;":
                                        case "Lio/gomint/command/annotation/Name;":
                                            neededArguments.incrementAndGet();
                                            break;
                                        default:
                                            break;
                                    }

                                    return super.visitAnnotation(descriptor, visible);
                                }
                            }, 0);

                            // Do we have @Name and @Description attached?
                            if (neededArguments.get() == 2) {
                                if (meta.getInjectionCommands() == null) {
                                    meta.setInjectionCommands(new HashSet<>());
                                }

                                meta.getInjectionCommands().add(cr.getClassName().replace("/", "."));
                            }
                        }
                    }
                }

                // Check if we found a valid plugin
                if (meta.getMainClass() != null) {
                    return meta;
                }

                return null;
            } catch (IOException e) {
                LOGGER.warn("Could not load Plugin. File " + file + " is corrupted", e);
                return null;
            }
        } catch (Exception ex) {
            LOGGER.warn("Could not load plugin from file " + file, ex);
            return null;
        }
    }

    @Override
    public void uninstallPlugin(Plugin plugin) {
        // Check for security
        if (!CallerDetectorUtil.getCallerPlugin().equals(plugin.getClass())) {
            throw new SecurityException("Plugins can only disable themselves");
        }

        // Check if plugin is enabled
        if (!this.installedPlugins.containsValue(plugin)) {
            return;
        }

        uninstallPlugin0(plugin);
    }

    private void uninstallPlugin0(Plugin plugin) {
        // Did we already disable this plugin?
        if (!this.installedPlugins.containsKey(plugin.getName())) {
            return;
        }

        // Check for plugins with hard depends
        new HashMap<>(this.metadata).forEach((name, meta) -> {
            if (meta.getDepends() != null && meta.getDepends().contains(plugin.getName())) {
                Plugin pluginToUninstall = installedPlugins.get(name);
                if (pluginToUninstall != null) {
                    uninstallPlugin0(pluginToUninstall);
                }
            }
        });

        this.callEvent(new PluginUninstallEvent(plugin));

        // Unregister listeners
        try {
            List<EventListener> listeners = (List<EventListener>) this.listenerListField.get(plugin);
            for (EventListener listener : listeners) {
                this.eventManager.unregisterListener(listener);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Cancel all tasks and cleanup scheduler
        try {
            PluginScheduler pluginScheduler = (PluginScheduler) this.schedulerField.get(plugin);
            pluginScheduler.cleanup();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // CHECKSTYLE:OFF
        try {
            LOGGER.info("Starting to shutdown {}", plugin.getName());
            plugin.onUninstall();
        } catch (Exception e) {
            LOGGER.warn("Plugin throw an exception whilst uninstalling: " + plugin.getName(), e);
        }
        // CHECKSTYLE:ON

        LOGGER.info("Uninstalled plugin " + plugin.getName());
        this.installedPlugins.remove(plugin.getName());
        this.metadata.remove(plugin.getName());

        // Unload the loader
        PluginClassloader classloader = (PluginClassloader) plugin.getClass().getClassLoader();
        classloader.remove();
    }

    @Override
    public String getBaseDirectory() {
        return this.pluginFolder.getAbsolutePath();
    }

    @Override
    public <T extends Plugin> T getPlugin(String name) {
        Plugin plugin = this.loadedPlugins.get(name);
        if (plugin != null) {
            return (T) plugin;
        }

        return (T) this.installedPlugins.get(name);
    }

    @Override
    public boolean isPluginInstalled(String name) {
        return this.installedPlugins.containsKey(name);
    }

    @Override
    public <T extends Event> T callEvent(T event) {
        LOGGER.debug("Calling event {}", event);
        this.eventManager.triggerEvent(event);
        return event;
    }

    @Override
    public void registerListener(Plugin plugin, EventListener listener) {
        if (!plugin.getClass().getClassLoader().equals(listener.getClass().getClassLoader())) {
            throw new SecurityException("Wanted to register listener for another plugin");
        }

        this.eventManager.registerListener(listener);
    }

    @Override
    public void unregisterListener(Plugin plugin, EventListener listener) {
        if (!plugin.getClass().getClassLoader().equals(listener.getClass().getClassLoader())) {
            throw new SecurityException("Wanted to unregister listener for another plugin");
        }

        this.eventManager.unregisterListener(listener);
    }

    @Override
    public void registerCommand(Plugin plugin, Command command) {
        this.commandManager.register(plugin, command);
    }

    /**
     * Cleanup / uninstall all plugins
     */
    public void close() {
        for (Plugin plugin : new ArrayList<>(this.loadedPlugins.values())) {
            uninstallPlugin0(plugin);
        }

        for (Plugin plugin : new ArrayList<>(this.installedPlugins.values())) {
            uninstallPlugin0(plugin);
        }
    }

}
