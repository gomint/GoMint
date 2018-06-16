/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n;

import io.gomint.i18n.localization.ResourceLoadFailedException;
import io.gomint.i18n.localization.ResourceLoader;
import io.gomint.i18n.localization.ResourceManager;
import io.gomint.i18n.localization.ResourceNotLoadedException;
import io.gomint.i18n.localization.loader.PropertiesResourceLoader;
import io.gomint.plugin.Plugin;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LocaleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( LocaleManager.class );

    // The ResourceManager to use for this LocaleManager
    private ResourceManager resourceManager;

    // The fallback Locale to use
    @Getter private Locale defaultLocale = Locale.US;

    // Whether to use the default locale also for untranslated messages
    @Getter @Setter private boolean useDefaultLocaleForMessages = true;

    // Plugin for which we have this
    private final Plugin plugin;

    /**
     * Construct a new LocaleManager for this Plugin
     *
     * @param plugin The plugin for which this LocaleManager should be loaded
     */
    public LocaleManager( Plugin plugin ) {
        this.plugin = plugin;

        this.resourceManager = new ResourceManager( plugin.getClass().getClassLoader() );
        this.resourceManager.registerLoader( new PropertiesResourceLoader() );
    }

    /**
     * Gets the list of available locales from the specified file.
     *
     * @param path The path of the file to query.
     * @return A list of supported locales as well as their meta-information or null on faillure.
     */
    public List<Locale> getAvailableLocales( File path ) {
        File[] files = path.listFiles();
        if ( files == null ) return null;

        List<Locale> supported = new ArrayList<>();
        for ( File file : files ) {
            String[] locale = file.getName().substring( 0, 5 ).split( "_" );
            supported.add( new Locale( locale[0], locale[1] ) );
        }

        return supported;
    }

    /**
     * Init / Load all Locales which could be found in the given spec file. This refreshes the languages all 5 minutes
     *
     * @param path            The path of the file to query.
     */
    public void initFromLocaleFolder( final File path ) {
        initFromLocaleFolderWithoutAutorefresh( path );
        this.plugin.getScheduler().schedule( new Runnable() {
            @Override
            public void run() {
                initFromLocaleFolderWithoutAutorefresh( path );
            }
        }, 5, 5, TimeUnit.MINUTES );
    }

    /**
     * Init / Load all Locales which could be found in the given spec file.
     *
     * @param path The path of the file to query.
     */
    public void initFromLocaleFolderWithoutAutorefresh( File path ) {
        File[] files = path.listFiles();
        if ( files == null ) return;

        for ( File file : files ) {
            String[] locale = file.getName().substring( 0, 5 ).split( "_" );

            try {
                load( new Locale( locale[0], locale[1] ), "file://" + file.getAbsolutePath() );
            } catch ( ResourceLoadFailedException e ) {
                LOGGER.warn( "Could not load i18n file {}", file.getAbsolutePath(), e );
            }
        }
    }

    /**
     * Load a new Locale into the ResourceManager for this Plugin
     *
     * @param locale Locale which should be loaded
     * @param param  The param which should be given to the ResourceLoader
     * @throws ResourceLoadFailedException if the loading has thrown any Error
     */
    public synchronized void load( Locale locale, String param ) throws ResourceLoadFailedException {
        resourceManager.load( locale, param );
    }

    /**
     * Gets the correct String out of the Locale. If the locale given is not loaded by the underlying ResourceManager
     * it takes the set default Locale to read the String from.
     *
     * @param locale Locale which should be read for
     * @param key    The key which should be looked up
     * @return The String stored in the ResourceLoader
     * @throws ResourceLoadFailedException If the Resource was cleared out and could not be reloaded into the Cache
     */
    private String getTranslationString( Locale locale, String key ) throws ResourceLoadFailedException {
        return resourceManager.get( locale, key );
    }

    /**
     * Check if the given Locale has been loaded by the ResourceManager. If not return the default Locale
     *
     * @param locale Locale which should be checked
     * @return The default locale or the param
     */
    private Locale checkForDefault( Locale locale ) {
        if ( !resourceManager.isLoaded( locale ) ) {
            return defaultLocale;
        }

        return locale;
    }

    /**
     * Change the default Locale for this plugin.
     * It must be loaded before a Locale can be set as default.
     *
     * @param locale Locale which should be used as default Fallback
     */
    public void setDefaultLocale( Locale locale ) {
        defaultLocale = locale;
    }

    /**
     * Translate the Text based on the locale.
     * If the locale is not loaded the LocaleManager will try to load it, if this fails
     * it will use the default Locale. If this is also not loaded you will get a ResourceNotLoadedException
     *
     * @param locale         Locale which should be used to translate
     * @param translationKey The key in the ResourceLoader which should be translated
     * @param args           The Arguments which will be passed into the String when translating
     * @return The translated String
     */
    public String translate( Locale locale, String translationKey, Object... args ) {
        //Get the resource and translate
        Locale playerLocale = checkForDefault( locale );

        String translationString = null;
        try {
            translationString = getTranslationString( playerLocale, translationKey );
        } catch ( ResourceLoadFailedException e ) {
            try {
                translationString = getTranslationString( playerLocale = defaultLocale, translationKey );
            } catch ( ResourceLoadFailedException e1 ) {
                // Ignore .-.
            }
        }

        // Check for untranslated messages
        if ( translationString == null ) {
            return "N/A (" + translationKey + ")";
        }

        MessageFormat msgFormat = new MessageFormat( translationString );
        msgFormat.setLocale( playerLocale );
        return msgFormat.format( args );
    }

    /**
     * Translate the Text based on the Player locale / default Locale.
     * If the locale from the player is not loaded the LocaleManager
     * will use the default Locale. If this is also not loaded it
     * will use the translationKey as text and give it back
     *
     * @param translationKey The key in the ResourceLoader which should be translated
     * @param args           The Arguments which will be passed into the String when translating
     * @return The translated String
     */
    public String translate( String translationKey, Object... args ) {
        //Get the resource and translate
        String translationString = null;
        try {
            translationString = getTranslationString( defaultLocale, translationKey );
        } catch ( ResourceLoadFailedException e ) {
            // Ignore .-.
        }

        if ( translationString == null ) {
            LOGGER.warn( "The key({}) is not present in the Locale {}", translationKey, defaultLocale );
            return "N/A (" + translationKey + ")";
        }

        MessageFormat msgFormat = new MessageFormat( translationString );
        msgFormat.setLocale( defaultLocale );
        return msgFormat.format( args );
    }

    /**
     * Register a new custom ResourceLoader. See {@link ResourceManager#registerLoader(ResourceLoader)}
     *
     * @param loader which is used to load specific locale resources
     */
    public void registerLoader( ResourceLoader loader ) {
        resourceManager.registerLoader( loader );
    }

    /**
     * Gets a list of all loaded Locales
     *
     * @return Unmodifiable List
     */
    public List<Locale> getLoadedLocales() {
        return Collections.unmodifiableList( resourceManager.getLoadedLocales() );
    }

    /**
     * Tells the ResourceManager to reload all Locale Resources which has been loaded by this Plugin
     */
    public synchronized void reload() {
        resourceManager.reload();
    }

    /**
     * Be sure to remove resources loaded and to remove refs
     */
    public synchronized void cleanup() {
        resourceManager.cleanup();
        resourceManager = null;
    }

}
