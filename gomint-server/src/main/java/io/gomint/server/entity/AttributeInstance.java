package io.gomint.server.entity;

import lombok.Getter;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString
@Getter
public class AttributeInstance {

    private final String key;
    private final float minValue;
    private final float maxValue;
    private final float defaultValue;
    private float value;
    private boolean dirty;

    AttributeInstance( String key, float minValue, float maxValue, float value ) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.defaultValue = value;
        this.dirty = true;
    }

    public void setValue( float value ) {
        if ( value < this.minValue || value > this.maxValue ) {
            throw new IllegalArgumentException( "Value is not withing bounds" );
        }

        this.value = value;
        this.dirty = true;
    }

    public boolean isDirty() {
        boolean val = this.dirty;
        this.dirty = false;
        return val;
    }

}
