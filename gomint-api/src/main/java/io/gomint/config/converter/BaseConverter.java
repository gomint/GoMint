package io.gomint.config.converter;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public abstract class BaseConverter implements Converter {

    protected final int asInteger( Object object ) {
        if ( object instanceof Long ) {
            return ( (Long) object ).intValue();
        } else if ( object instanceof Double ) {
            return ( (Double) object ).intValue();
        } else if ( object instanceof Float ) {
            return ( (Float) object ).intValue();
        } else if ( object instanceof Short ) {
            return ( (Short) object ).intValue();
        } else if ( object instanceof Byte ) {
            return ( (Byte) object ).intValue();
        }

        return (int) object;
    }

    protected final float asFloat( Object object ) {
        if ( object instanceof Long ) {
            return ( (Long) object ).floatValue();
        } else if ( object instanceof Double ) {
            return ( (Double) object ).floatValue();
        } else if ( object instanceof Integer ) {
            return ( (Integer) object ).floatValue();
        } else if ( object instanceof Short ) {
            return ( (Short) object ).floatValue();
        } else if ( object instanceof Byte ) {
            return ( (Byte) object ).floatValue();
        }

        return (float) object;
    }

    protected final double asDouble( Object object ) {
        if ( object instanceof Long ) {
            return ( (Long) object ).doubleValue();
        } else if ( object instanceof Float ) {
            return ( (Float) object ).doubleValue();
        } else if ( object instanceof Integer ) {
            return ( (Integer) object ).doubleValue();
        } else if ( object instanceof Short ) {
            return ( (Short) object ).doubleValue();
        } else if ( object instanceof Byte ) {
            return ( (Byte) object ).doubleValue();
        }

        return (double) object;
    }

}
