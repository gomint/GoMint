/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.module.ResolvedModule;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ClassPath {

    private static final Logger LOGGER = LoggerFactory.getLogger( ClassPath.class );

    private final Set<File> scannedUris = new HashSet<>();
    private final Set<ClassInfo> classes = new HashSet<>();

    public ClassPath( String preFilter ) throws IOException {
        UnmodifiableIterator<?> var2 = getClassPathEntries( ClassPath.class.getClassLoader() ).entrySet().iterator();

        preFilter = preFilter.replace( ".", "/" );

        while ( var2.hasNext() ) {
            Map.Entry<File, ClassLoader> entry = (Map.Entry<File, ClassLoader>) var2.next();
            this.scan( preFilter, entry.getKey() );
        }
    }

    private void scan( String preFilter, File file ) throws IOException {
        if ( this.scannedUris.add( file.getCanonicalFile() ) ) {
            this.scanFrom( preFilter, file );
        }
    }

    private void scanFrom( String preFilter, File file ) throws IOException {
        try {
            if ( !file.exists() ) {
                return;
            }
        } catch ( SecurityException var4 ) {
            LOGGER.warn( "Cannot access {}", file, var4 );
            return;
        }

        if ( file.isDirectory() ) {
            this.scanDirectory( preFilter, file );
        } else {
            this.scanJar( preFilter, file );
        }

    }

    private void scanJarFile( String preFilter, JarFile file ) {
        // Prefilter path is not in jar, get out
        if ( file.getEntry( preFilter ) == null ) {
            return;
        }

        Enumeration<?> entries = file.entries();
        while ( entries.hasMoreElements() ) {
            JarEntry entry = (JarEntry) entries.nextElement();
            if ( !entry.isDirectory() && entry.getName().endsWith( ".class" ) ) {
                String className = entry.getName();
                if ( className.startsWith( preFilter ) ) {
                    this.classes.add(new ClassInfo(className.replace("/", ".").replace(".class", "")));
                }
            }
        }
    }

    private void scanDirectory( String preFilter, File directory ) throws IOException {
        Set<File> currentPath = new HashSet<>();
        currentPath.add( directory.getCanonicalFile() );
        this.scanDirectory( preFilter, directory, "", currentPath );
    }

    private void scanDirectory( String preFilter, File directory, String packagePrefix, Set<File> currentPath ) throws IOException {
        File[] files = directory.listFiles();
        if ( files == null ) {
            LOGGER.warn( "Cannot read directory {}", directory );
        } else {
            int var7 = files.length;

            for (File f : files) {
                String name = f.getName();
                if (f.isDirectory()) {
                    File deref = f.getCanonicalFile();
                    if (currentPath.add(deref)) {
                        this.scanDirectory(preFilter, deref, packagePrefix + name + "/", currentPath);
                        currentPath.remove(deref);
                    }
                } else {
                    String resourceName = packagePrefix + name;
                    if (!resourceName.equals("META-INF/MANIFEST.MF") && resourceName.endsWith(".class")) {
                        if (resourceName.startsWith(preFilter)) {
                            this.classes.add(new ClassInfo(resourceName.replace("/", ".").replace(".class", "")));
                        }
                    }
                }
            }

        }
    }

    private void scanJar( String preFilter, File file ) {
        JarFile jarFile;

        try {
            jarFile = new JarFile( file );
        } catch ( IOException var13 ) {
            return;
        }

        try {
            this.scanJarFile( preFilter, jarFile );
        } finally {
            try {
                jarFile.close();
            } catch ( IOException var12 ) {
                // Ignored
            }
        }
    }

    private File toFile( URL url ) {
        Preconditions.checkArgument( url.getProtocol().equals( "file" ) );

        try {
            return new File( url.toURI() );
        } catch ( URISyntaxException var2 ) {
            return new File( url.getPath() );
        }
    }

    private ImmutableMap<File, ClassLoader> getClassPathEntries( ClassLoader classloader ) {
        LinkedHashMap<File, ClassLoader> entries = Maps.newLinkedHashMap();
        ClassLoader parent = classloader.getParent();
        if ( parent != null ) {
            entries.putAll( getClassPathEntries( parent ) );
        }

        for (URL url : getClassLoaderUrls(classloader)) {
            if (url.getProtocol().equals("file")) {
                File file = toFile(url);
                if (!entries.containsKey(file)) {
                    entries.put(file, classloader);
                }
            }
        }

        return ImmutableMap.copyOf( entries );
    }

    private ImmutableList<URL> getClassLoaderUrls( ClassLoader classloader ) {
        if ( classloader instanceof URLClassLoader ) {
            return ImmutableList.copyOf( ( (URLClassLoader) classloader ).getURLs() );
        } else {
            return classloader.equals( ClassLoader.getSystemClassLoader() ) ? parseJavaClassPathAndModules() : ImmutableList.of();
        }
    }

    private ImmutableList<URL> parseJavaClassPathAndModules() {
        com.google.common.collect.ImmutableList.Builder<URL> urls = ImmutableList.builder();

        // Scan classpath first

        for (String entry : Splitter
            .on(Objects.requireNonNull(StandardSystemProperty.PATH_SEPARATOR.value()))
            .split(Objects.requireNonNull(StandardSystemProperty.JAVA_CLASS_PATH.value()))) {
            try {
                try {
                    urls.add((new File(entry)).toURI().toURL());
                } catch (SecurityException var4) {
                    urls.add(new URL("file", null, (new File(entry)).getAbsolutePath()));
                }
            } catch (MalformedURLException var5) {
                LOGGER.warn("malformed classpath entry: {}", entry, var5);
            }
        }

        // Now modules
        Module currentModule = ClassPath.class.getModule();
        if (currentModule != null) {
            ModuleLayer currentLayer = currentModule.getLayer();
            if (currentLayer != null) {
                for (ResolvedModule module : currentLayer.configuration().modules()) {
                    Optional<URI> moduleLocation = module.reference().location();
                    moduleLocation.ifPresent(url -> {
                        try {
                            urls.add(url.toURL());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }

        return urls.build();
    }

    public void getTopLevelClasses( String classPath, Consumer<ClassInfo> classInfoConsumer ) {
        for ( ClassInfo info : this.classes ) {
            String replaced = info.className.replace( classPath, "" ).substring( 1 );

            if ( info.className.startsWith( classPath ) && !replaced.contains( "." ) ) {
                classInfoConsumer.accept( info );
            }
        }
    }

    public static class ClassInfo {
        private final String className;

        public ClassInfo(String className) {
            this.className = className;
        }

        public <T extends Class<T>> Class<T> load() {
            try {
                return (T) Class.forName( this.className );
            } catch ( ClassNotFoundException e ) {
                // Ignored
            }

            return null;
        }
    }

}
