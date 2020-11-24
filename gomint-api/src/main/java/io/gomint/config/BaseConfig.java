/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import io.gomint.config.annotation.PreserveStatic;
import io.gomint.config.annotation.SerializeOptions;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class BaseConfig implements Serializable {

    protected transient File configFile = null;
    protected transient String[] configHeader = null;
    protected transient ConfigMode configMode = ConfigMode.DEFAULT;
    protected transient boolean skipFailedObjects = false;
    protected transient InternalConverter converter = new InternalConverter( this );

    /**
     * This function gets called after the File has been loaded and before the converter gets it.
     * This is used to manually edit the configSection when you updated the config or something
     *
     * @param section The root ConfigSection with all sub-nodes loaded into
     */
    public void update( ConfigSection section ) {
        /*
         * This is a hook point for custom classes to overwrite when needed to specify a update path
         */
    }

    /**
     * Add a Custom converter. A converter can take Objects and return a pretty Object which gets saved/loaded from
     * the converter. How a converter must be build can be looked up in the converter Interface.
     *
     * @param converter converter to be added
     * @throws InvalidConverterException If the converter has any errors this Exception tells you what
     */
    public void addConverter( Class converter ) throws InvalidConverterException {
        this.converter.addCustomConverter( converter );
    }

    protected void configureFromSerializeOptionsAnnotation() {
        if ( !this.getClass().isAnnotationPresent( SerializeOptions.class ) ) {
            return;
        }

        SerializeOptions options = this.getClass().getAnnotation( SerializeOptions.class );
        this.configHeader = options.configHeader();
        this.configMode = options.configMode();
        this.skipFailedObjects = options.skipFailedObjects();
    }

    /**
     * Check if we need to skip the given field
     *
     * @param field which may be skipped
     * @return true when it should be skipped, false when not
     */
    boolean doSkip( Field field ) {
        if ( Modifier.isTransient( field.getModifiers() ) || Modifier.isFinal( field.getModifiers() ) ) {
            return true;
        }

        if ( Modifier.isStatic( field.getModifiers() ) ) {
            if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                return true;
            }

            PreserveStatic presStatic = field.getAnnotation( PreserveStatic.class );
            return !presStatic.value();
        }

        return false;
    }

}
