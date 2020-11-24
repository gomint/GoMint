/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.module.FindException;
import java.lang.module.InvalidModuleDescriptorException;
import java.lang.module.ModuleDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModuleDetector {

    private static final String MODULE_INFO = "module-info.class";

    private static final String SERVICES_PREFIX = "META-INF/services/";

    private static final Attributes.Name AUTOMATIC_MODULE_NAME
        = new Attributes.Name("Automatic-Module-Name");

    public ModuleDescriptor deriveModuleDescriptor(JarFile jf)
        throws IOException
    {
        // Read Automatic-Module-Name attribute if present
        Manifest man = jf.getManifest();
        Attributes attrs = null;
        String moduleName = null;
        if (man != null) {
            attrs = man.getMainAttributes();
            if (attrs != null) {
                moduleName = attrs.getValue(AUTOMATIC_MODULE_NAME);
            }
        }

        // Derive the version, and the module name if needed, from JAR file name
        String fn = jf.getName();
        int i = fn.lastIndexOf(File.separator);
        if (i != -1)
            fn = fn.substring(i + 1);

        // drop ".jar"
        String name = fn.substring(0, fn.length() - 4);
        String vs = null;

        // find first occurrence of -${NUMBER}. or -${NUMBER}$
        Matcher matcher = Patterns.DASH_VERSION.matcher(name);
        if (matcher.find()) {
            int start = matcher.start();

            // attempt to parse the tail as a version string
            try {
                String tail = name.substring(start + 1);
                ModuleDescriptor.Version.parse(tail);
                vs = tail;
            } catch (IllegalArgumentException ignore) { }

            name = name.substring(0, start);
        }

        // Create builder, using the name derived from file name when
        // Automatic-Module-Name not present
        ModuleDescriptor.Builder builder;
        if (moduleName != null) {
            try {
                builder = ModuleDescriptor.newAutomaticModule(moduleName);
            } catch (IllegalArgumentException e) {
                throw new FindException(AUTOMATIC_MODULE_NAME + ": " + e.getMessage());
            }
        } else {
            builder = ModuleDescriptor.newAutomaticModule(cleanModuleName(name));
        }

        // module version if present
        if (vs != null)
            builder.version(vs);

        // scan the names of the entries in the JAR file
        Map<Boolean, Set<String>> map = jf.versionedStream()
            .filter(e -> !e.isDirectory())
            .map(JarEntry::getName)
            .filter(e -> (e.endsWith(".class") ^ e.startsWith(SERVICES_PREFIX)))
            .collect(Collectors.partitioningBy(e -> e.startsWith(SERVICES_PREFIX),
                Collectors.toSet()));

        Set<String> classFiles = map.get(Boolean.FALSE);
        Set<String> configFiles = map.get(Boolean.TRUE);

        // the packages containing class files
        Set<String> packages = classFiles.stream()
            .map(this::toPackageName)
            .flatMap(Optional::stream)
            .distinct()
            .collect(Collectors.toSet());

        // all packages are exported and open
        builder.packages(packages);

        // map names of service configuration files to service names
        Set<String> serviceNames = configFiles.stream()
            .map(this::toServiceName)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

        // parse each service configuration file
        for (String sn : serviceNames) {
            JarEntry entry = jf.getJarEntry(SERVICES_PREFIX + sn);
            List<String> providerClasses = new ArrayList<>();
            try (InputStream in = jf.getInputStream(entry)) {
                BufferedReader reader
                    = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String cn;
                while ((cn = nextLine(reader)) != null) {
                    if (cn.length() > 0) {
                        String pn = packageName(cn);
                        if (!packages.contains(pn)) {
                            String msg = "Provider class " + cn + " not in module";
                            throw new InvalidModuleDescriptorException(msg);
                        }
                        providerClasses.add(cn);
                    }
                }
            }
            if (!providerClasses.isEmpty())
                builder.provides(sn, providerClasses);
        }

        // Main-Class attribute if it exists
        if (attrs != null) {
            String mainClass = attrs.getValue(Attributes.Name.MAIN_CLASS);
            if (mainClass != null) {
                mainClass = mainClass.replace("/", ".");
                if (Checks.isClassName(mainClass)) {
                    String pn = packageName(mainClass);
                    if (packages.contains(pn)) {
                        builder.mainClass(mainClass);
                    }
                }
            }
        }

        return builder.build();
    }

    /**
     * Maps the name of an entry in a JAR or ZIP file to a package name.
     *
     * @throws InvalidModuleDescriptorException if the name is a class file in
     *         the top-level directory of the JAR/ZIP file (and it's not
     *         module-info.class)
     */
    private Optional<String> toPackageName(String name) {
        assert !name.endsWith("/");
        int index = name.lastIndexOf("/");
        if (index == -1) {
            if (name.endsWith(".class") && !name.equals(MODULE_INFO)) {
                String msg = name + " found in top-level directory"
                    + " (unnamed package not allowed in module)";
                throw new InvalidModuleDescriptorException(msg);
            }
            return Optional.empty();
        }

        String pn = name.substring(0, index).replace('/', '.');
        if (Checks.isPackageName(pn)) {
            return Optional.of(pn);
        } else {
            // not a valid package name
            return Optional.empty();
        }
    }

    /**
     * Maps a type name to its package name.
     */
    private static String packageName(String cn) {
        int index = cn.lastIndexOf('.');
        return (index == -1) ? "" : cn.substring(0, index);
    }

    /**
     * Clean up candidate module name derived from a JAR file name.
     */
    private static String cleanModuleName(String mn) {
        // replace non-alphanumeric
        mn = Patterns.NON_ALPHANUM.matcher(mn).replaceAll(".");

        // collapse repeating dots
        mn = Patterns.REPEATING_DOTS.matcher(mn).replaceAll(".");

        // drop leading dots
        if (mn.length() > 0 && mn.charAt(0) == '.')
            mn = Patterns.LEADING_DOTS.matcher(mn).replaceAll("");

        // drop trailing dots
        int len = mn.length();
        if (len > 0 && mn.charAt(len-1) == '.')
            mn = Patterns.TRAILING_DOTS.matcher(mn).replaceAll("");

        return mn;
    }


    /**
     * Returns the service type corresponding to the name of a services
     * configuration file if it is a legal type name.
     *
     * For example, if called with "META-INF/services/p.S" then this method
     * returns a container with the value "p.S".
     */
    private Optional<String> toServiceName(String cf) {
        assert cf.startsWith(SERVICES_PREFIX);
        int index = cf.lastIndexOf("/") + 1;
        if (index < cf.length()) {
            String prefix = cf.substring(0, index);
            if (prefix.equals(SERVICES_PREFIX)) {
                String sn = cf.substring(index);
                if (Checks.isClassName(sn))
                    return Optional.of(sn);
            }
        }
        return Optional.empty();
    }

    /**
     * Reads the next line from the given reader and trims it of comments and
     * leading/trailing white space.
     *
     * Returns null if the reader is at EOF.
     */
    private String nextLine(BufferedReader reader) throws IOException {
        String ln = reader.readLine();
        if (ln != null) {
            int ci = ln.indexOf('#');
            if (ci >= 0)
                ln = ln.substring(0, ci);
            ln = ln.trim();
        }
        return ln;
    }


    /**
     * Patterns used to derive the module name from a JAR file name.
     */
    private static class Patterns {
        static final Pattern DASH_VERSION = Pattern.compile("-(\\d+(\\.|$))");
        static final Pattern NON_ALPHANUM = Pattern.compile("[^A-Za-z0-9]");
        static final Pattern REPEATING_DOTS = Pattern.compile("(\\.)(\\1)+");
        static final Pattern LEADING_DOTS = Pattern.compile("^\\.");
        static final Pattern TRAILING_DOTS = Pattern.compile("\\.$");
    }

}
