package io.gomint.server.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.EnumMap;

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

    private EnumMap<AttributeModifier, Float> modifiers = new EnumMap<>( AttributeModifier.class );

    AttributeInstance( String key, float minValue, float maxValue, float value ) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.defaultValue = value;
        this.dirty = true;
    }

    public void setModifier( AttributeModifier modifier, float amount ) {
        this.modifiers.put( modifier, amount );
        this.value += amount;
        this.dirty = true;
    }

    public void removeModifier( AttributeModifier modifier ) {
        Float amount = this.modifiers.remove( modifier );
        if ( amount != null ) {
            this.value -= amount;
            this.dirty = true;
        }
    }

    public void setValue( float value ) {
        if ( value < this.minValue || value > this.maxValue ) {
            throw new IllegalArgumentException( "Value is not withing bounds: " + value + "; max: " + this.maxValue + "; min: " + this.minValue );
        }

        this.value = value;
        this.dirty = true;
    }

    public boolean isDirty() {
        boolean val = this.dirty;
        this.dirty = false;
        return val;
    }

    public void reset() {
        this.modifiers.clear();
        this.value = this.defaultValue;
        this.dirty = true;
    }

}
