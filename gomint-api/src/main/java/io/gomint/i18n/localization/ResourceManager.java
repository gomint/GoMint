/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n.localization;

import io.gomint.i18n.LocaleManager;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ResourceManager {

    // Save all loaded Locales with their loadString
    private final HashMap<Locale, String> loadedLocaleLoadStrings = new HashMap<>();

    // Save all loaded Locales
    private final HashMap<Locale, SoftReference<ResourceLoader<?>>> loadedLocales = new HashMap<>();

    // The list of all available ResourceLoaders
    private final ArrayList<ResourceLoader<?>> registerdLoaders = new ArrayList<>();

    // Construct a object which can be locked on
    private final Object sharedLock = new Object();

    // The ClassLoader for which this Manager manages Resources
    private ClassLoader classLoader;

    /**
     * Constructs a new ResourceManager which handles the loading and getting, reloading and cleanup for Resources
     *
     * @param classLoader The classLoader for which this ResourceManager should load Resources
     */
    public ResourceManager( ClassLoader classLoader ) {
        this.classLoader = classLoader;
    }

    /**
     * Register a new ResourceLoader. It must implement the {@link ResourceLoadFailedException}
     * and have a Constructor which takes two Arguments, JavaPlugin as first and a String as second Parameter. It also
     * needs to have a empty default Constructor so you can register the ResourceLoader without it having loaded any
     * Resource in it.
     *
     * The JavaPlugin will be the Plugin for which this loader should load.
     * The String is the second parameter from {@link LocaleManager#load(Locale, String)}
     *
     * @param loader New loader which can be used to load Resources
     */
    public synchronized ResourceManager registerLoader( ResourceLoader<?> loader ) {
        synchronized ( sharedLock ) {
            registerdLoaders.add( loader );
        }

        return this;
    }

    /**
     * Try to load the ResourceLoader for this locale and param
     *
     * @param locale The locale which should be loaded for
     * @param param  The param which the ResourceLoader should load
     * @throws ResourceLoadFailedException when loading failed
     */
    private synchronized ResourceManager loadLocale( Locale locale, String param ) throws ResourceLoadFailedException {
        //Get the correct loader for this param
        for ( ResourceLoader<?> loader : registerdLoaders ) {
            for ( String ending : loader.formats() ) {
                if ( param.endsWith( ending ) ) {
                    try {
                        synchronized ( sharedLock ) {
                            loadedLocales.put( locale, new SoftReference<>( buildNewResourceLoader( loader, param ) ) );
                            loadedLocaleLoadStrings.put( locale, param );
                        }
                    } catch ( RuntimeException e ) {
                        throw new ResourceLoadFailedException( e );
                    }
                }
            }
        }

        return this;
    }

    /**
     * Load a new Resource for the locale. The Loaders gets selected based of the ending of the Parameter. If
     * a Locale has been loaded before it gets unloaded and the new one gets loaded instead. So you can overload
     * old Resources with new ones
     *
     * @param locale Locale for which this Resource should be loaded
     * @param param  The param from {@link LocaleManager#load(Locale, String)}
     * @throws ResourceLoadFailedException when loading failed
     */
    public synchronized ResourceManager load( Locale locale, String param ) throws ResourceLoadFailedException {
        //Check if locale has already been loaded
        if ( loadedLocales.containsKey( locale ) ) {
            synchronized ( sharedLock ) {
                //Unload the locale and get the new one
                ResourceLoader<?> loader = loadedLocales.get( locale ).get();
                if ( loader != null ) {
                    loader.cleanup();
                }

                loadedLocales.remove( locale );
                loadedLocaleLoadStrings.remove( locale );
            }
        }

        return loadLocale( locale, param );
    }

    /**
     * Checks if the GC has unloaded this Locale ResourceLoader for the need of more RAM, if so try to reload the Locale
     *
     * @param locale The Locale to check for
     * @throws ResourceLoadFailedException
     */
    private synchronized void reloadIfGCCleared( Locale locale ) throws ResourceLoadFailedException {
        if ( loadedLocales.get( locale ).get() == null ) {
            synchronized ( sharedLock ) {
                loadLocale( locale, loadedLocaleLoadStrings.get( locale ) );
            }
        }
    }

    /**
     * Get the String for key out of the correct Loader. If the Locale has not been loaded you will get an
     * {@link ResourceNotLoadedException}. If the Resource has been loaded but
     * it missed a translation you will get null
     *
     * @param locale Locale to lookup
     * @param key    Key to search in the loader
     * @return The String which has been resolved by the Loader
     * @throws ResourceLoadFailedException
     */
    public String get( Locale locale, String key ) throws ResourceLoadFailedException {
        //If Locale is not loaded throw a ResourceNotLoadedException
        if ( loadedLocales.containsKey( locale ) ) {
            //Check if this Locale contains the key searched for
            reloadIfGCCleared( locale );

            if ( loadedLocales.get( locale ).get().keys().contains( key ) ) {
                return loadedLocales.get( locale ).get().get( key );
            }
        }

        //Check if there is a Resource for the language only (so you can inherit en to en_US for example)
        Locale baseLocale = new Locale( locale.getLanguage() );

        //If Locale is not loaded throw a ResourceNotLoadedException
        if ( loadedLocales.containsKey( baseLocale ) ) {
            //Check if this Locale contains the key searched for
            reloadIfGCCleared( baseLocale );

            if ( loadedLocales.get( baseLocale ).get().keys().contains( key ) ) {
                return loadedLocales.get( baseLocale ).get().get( key );
            }
        }

        return null;
    }

    /**
     * Checks if a Locale has been loaded into this ResourceManager
     *
     * @param locale Locale which should be checked for
     * @return true if loaded / false if not
     */
    public boolean isLoaded( Locale locale ) {
        return loadedLocales.containsKey( locale );
    }

    /**
     * Tries to construct a new ResourceLoader from the given Template
     *
     * @param loader   The ResourceLoader which should be duplicated
     * @param argument The argument which should be given as Loadstring
     * @return A hopefully new ResourceLoader
     * @throws RuntimeException
     */
    private synchronized ResourceLoader<?> buildNewResourceLoader( ResourceLoader<?> loader, String argument ) {
        try {
            Constructor<?> constructor = loader.getClass().getConstructor( ClassLoader.class, String.class );
            return (ResourceLoader<?>) constructor.newInstance( this.classLoader, argument );
        } catch ( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e ) {
            throw new RuntimeException( "Could not construct new ResourceLoader", e );
        }
    }

    /**
     * Reload all ResourceLoaders
     *
     * If one of the ResourceLoaders reports an error upon reloading it will get printed to the Plugins Logger
     */
    public synchronized ResourceManager reload() {
        //Reload all ResourceLoaders
        for ( SoftReference<ResourceLoader<?>> loader : loadedLocales.values() ) {
            try {
                if ( loader != null && loader.get() != null ) {
                    loader.get().reload();
                }
            } catch ( ResourceLoadFailedException e ) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /**
     * If the Plugin should be unloaded remove all loaded Things and unref the plugin
     */
    public synchronized void cleanup() {
        // Cleanup all ResourceLoaders
        for ( SoftReference<ResourceLoader<?>> loader : loadedLocales.values() ) {
            if ( loader != null && loader.get() != null ) {
                loader.get().cleanup();
            }
        }

        // Remove all refs
        classLoader = null;
    }

    /**
     * Get a copy of the Key from the loaded Locales Map
     *
     * @return ArrayList of Locales
     */
    public List<Locale> getLoadedLocales() {
        return new ArrayList<>( loadedLocales.keySet() );
    }

}
