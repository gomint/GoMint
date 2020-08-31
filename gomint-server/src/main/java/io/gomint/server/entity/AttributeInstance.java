package io.gomint.server.entity;

import io.gomint.math.MathUtils;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AttributeInstance {

    private static final Logger LOGGER = LoggerFactory.getLogger( AttributeInstance.class );

    private final String key;
    private float minValue;
    private float maxValue;
    private float defaultValue;
    private float value;
    private boolean dirty;

    private Map<AttributeModifierType, Map<AttributeModifier, Float>> modifiers = new EnumMap<>( AttributeModifierType.class );

    AttributeInstance( String key, float minValue, float maxValue, float value ) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.defaultValue = value;
        this.dirty = true;
    }

    public void setModifier( AttributeModifier modifier, AttributeModifierType type, float amount ) {
        Map<AttributeModifier, Float> mods = this.getModifiers( type );
        mods.put( modifier, amount );
        this.recalc();
    }

    private Map<AttributeModifier, Float> getModifiers( AttributeModifierType type ) {
        Map<AttributeModifier, Float> modifier = this.modifiers.get( type );
        if ( modifier == null ) {
            modifier = new EnumMap<>( AttributeModifier.class );
            this.modifiers.put( type, modifier );
        }

        return modifier;
    }

    private void recalc() {
        this.value = this.defaultValue;

        for ( Map.Entry<AttributeModifierType, Map<AttributeModifier, Float>> entry : this.modifiers.entrySet() ) {
            this.calcModifiers( entry.getKey(), entry.getValue() );
        }

        // Clamp
        this.value = MathUtils.clamp( this.value, this.minValue, this.maxValue );
        this.dirty = true;
    }

    private void calcModifiers( AttributeModifierType type, Map<AttributeModifier, Float> value ) {
        switch ( type ) {
            case ADDITION:
                for ( Float aFloat : value.values() ) {
                    this.value += aFloat;
                }

                break;

            case ADDITION_MULTIPLY:
                for ( Float aFloat : value.values() ) {
                    this.value += this.defaultValue * aFloat;
                }

                break;

            case MULTIPLY:
                for ( Float aFloat : value.values() ) {
                    this.value *= 1f + aFloat;
                }

                break;
        }
    }

    public void removeModifier( AttributeModifier modifier ) {
        for ( Map.Entry<AttributeModifierType, Map<AttributeModifier, Float>> entry : this.modifiers.entrySet() ) {
            entry.getValue().remove( modifier );
        }

        this.recalc();
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

    public void setMaxValue( float maxValue ) {
        this.maxValue = maxValue;
    }

    public void initFromNBT( NBTTagCompound compound ) {
        this.defaultValue = compound.getFloat( "Base", this.defaultValue );
        this.value = compound.getFloat("Current", this.value);
        this.maxValue = compound.getFloat("Max", this.maxValue);

        List<Object> nbtAmplifiers = compound.getList( "Modifiers", false );
        if ( nbtAmplifiers != null ) {
            for ( Object amplifier: nbtAmplifiers ) {
                NBTTagCompound nbtAmplifier = (NBTTagCompound) amplifier;

                String name = nbtAmplifier.getString( "Name", "" );
                AttributeModifier modifier = null;
                for ( AttributeModifier attributeModifier : AttributeModifier.values() ) {
                    if ( attributeModifier.getName().equals( name ) ) {
                        modifier = attributeModifier;
                        break;
                    }
                }

                if ( modifier == null ) {
                    LOGGER.warn( "Unknown modifier: {}", name );
                }

                int operation = nbtAmplifier.getInteger( "Operation", 0 );
                float amount = nbtAmplifier.getFloat( "Amount", 0f );

                if ( modifier != null && amount != 0 ) {
                    switch ( operation ) {
                        case 0:
                            this.setModifier( modifier, AttributeModifierType.ADDITION, amount );
                            break;
                        case 1:
                            this.setModifier( modifier, AttributeModifierType.MULTIPLY, amount );
                            break;
                        case 2:
                            this.setModifier( modifier, AttributeModifierType.ADDITION_MULTIPLY, amount );
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public NBTTagCompound persistToNBT() {
        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Name", this.key );
        compound.addValue( "Base", this.defaultValue );
        compound.addValue("Current", this.value);
        compound.addValue("Max", this.maxValue);

        // Check for 0 mode multipliers (simple addition)
        List<NBTTagCompound> nbtModifiers = new ArrayList<>();
        if ( !this.modifiers.isEmpty() ) {
            for ( Map.Entry<AttributeModifierType, Map<AttributeModifier, Float>> entry: this.modifiers.entrySet() ) {
                for (Map.Entry<AttributeModifier, Float> modifierEntry : entry.getValue().entrySet()) {
                    NBTTagCompound nbtTagCompound = new NBTTagCompound( "" );
                    nbtTagCompound.addValue( "Name", modifierEntry.getKey().getName() );
                    nbtTagCompound.addValue( "Operation", entry.getKey().ordinal() );
                    nbtTagCompound.addValue( "Amount", (double) modifierEntry.getValue() );
                    nbtModifiers.add( nbtTagCompound );
                }
            }
        }

        compound.addValue( "Modifiers", nbtModifiers );
        return compound;
    }

    public String getKey() {
        return key;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public float getValue() {
        return value;
    }

    public Map<AttributeModifierType, Map<AttributeModifier, Float>> getModifiers() {
        return modifiers;
    }

    @Override
    public String toString() {
        return "AttributeInstance{" +
            "key='" + key + '\'' +
            ", minValue=" + minValue +
            ", maxValue=" + maxValue +
            ", defaultValue=" + defaultValue +
            ", value=" + value +
            ", dirty=" + dirty +
            ", modifiers=" + modifiers +
            '}';
    }
}
