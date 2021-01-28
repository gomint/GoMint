/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n.localization.loader;

import io.gomint.i18n.localization.ResourceLoadFailedException;
import io.gomint.i18n.localization.ResourceLoader;
import io.gomint.i18n.localization.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class PropertiesResourceLoader extends FileResourceLoader implements ResourceLoader<PropertiesResourceLoader> {

    private Properties pro;
    private String file;
    private ArrayList<String> keys = new ArrayList<>();

    /**
     * Empty Constructor template for the
     * {@link ResourceManager#registerLoader(ResourceLoader)}
     */
    public PropertiesResourceLoader() {

    }

    /**
     * Load a new PropertiesResource
     *
     * @param module The module for which this Resource should be loaded
     * @param file   The file to load
     * @throws ResourceLoadFailedException if the stream could not be closed
     */
    public PropertiesResourceLoader(Module module, String file) throws ResourceLoadFailedException {
        super(module);

        this.file = file;
        this.load();
    }

    private void load() throws ResourceLoadFailedException {
        InputStreamReader stream = null;
        try {
            //Get the correct InputStreamReader for this file
            stream = fileInputStreamReader(this.file);

            //Try to parse the properties
            this.pro = new Properties();
            this.pro.load(stream);

            //Get the keys
            this.keys = new ArrayList<>();

            for (Object o : this.pro.keySet()) {
                this.keys.add((String) o);
            }
        } catch (IOException e) {
            this.pro = null;
            throw new ResourceLoadFailedException(e);
        } catch (ResourceLoadFailedException e) {
            throw e;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new ResourceLoadFailedException(e);
                }
            }
        }
    }

    /**
     * Get all keys which can be handled by this Resource
     *
     * @return List of keys available
     */
    @Override
    public List<String> keys() {
        return this.keys;
    }

    /**
     * Get the key from the Properties
     *
     * @param key Key to get
     * @return The object from Properties or null if Properties loading was an error
     */
    @Override
    public String get(String key) {
        return this.pro != null ? (String) this.pro.get(key) : null;
    }

    /**
     * Get the Formats this Loader can load
     *
     * @return A List of String as formats this Loader supports
     */
    @Override
    public List<String> formats() {
        return Arrays.asList(".properties");
    }

    /**
     * Force the reload of this Resource
     *
     * @throws ResourceLoadFailedException
     */
    @Override
    public PropertiesResourceLoader reload() throws ResourceLoadFailedException {
        this.load();
        return this;
    }

    /**
     * If plugin gets unloaded remove all refs
     */
    @Override
    public void cleanup() {
        this.pro = null;
        this.file = null;

        super.cleanup();
    }

}
