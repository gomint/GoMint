package io.gomint.command.validator;

import io.gomint.command.ParamType;
import io.gomint.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BooleanValidator extends EnumValidator {

    public BooleanValidator() {
        super( new ArrayList<String>(){{
            add( "true" );
            add( "false" );
        }} );
    }

    @Override
    public ParamType getType() {
        return ParamType.BOOL;
    }

    @Override
    public Object validate( List<String> input, Entity entity ) {
        String values = (String) super.validate( input, entity );
        if ( values == null ) {
            return null;
        }

        return values.equals( "true" ) ? Boolean.TRUE : Boolean.FALSE;
    }

}
