package io.gomint.config;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BaseConfig implements Serializable {

    protected transient File CONFIG_FILE = null;
    protected transient String[] CONFIG_HEADER = null;
    protected transient ConfigMode CONFIG_MODE = ConfigMode.DEFAULT;
    protected transient boolean skipFailedObjects = false;

    protected transient InternalConverter converter = new InternalConverter( this );

    /**
     * This function gets called after the File has been loaded and before the converter gets it.
     * This is used to manually edit the configSection when you updated the config or something
     *
     * @param configSection The root ConfigSection with all Subnodes loaded into
     */
    public void update( ConfigSection configSection ) {

    }

    /**
     * Add a Custom converter. A converter can take Objects and return a pretty Object which gets saved/loaded from
     * the converter. How a converter must be build can be looked up in the converter Interface.
     *
     * @param addConverter converter to be added
     * @throws InvalidConverterException If the converter has any errors this Exception tells you what
     */
    public void addConverter( Class addConverter ) throws InvalidConverterException {
        this.converter.addCustomConverter( addConverter );
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

    protected void configureFromSerializeOptionsAnnotation() {
        if ( !getClass().isAnnotationPresent( SerializeOptions.class ) ) {
            return;
        }

        SerializeOptions options = getClass().getAnnotation( SerializeOptions.class );
        CONFIG_HEADER = options.configHeader();
        CONFIG_MODE = options.configMode();
        this.skipFailedObjects = options.skipFailedObjects();
    }

}
