package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BooleanValidator extends EnumValidator {

    private static final List<String> ENUM_VALUES = new ArrayList<>();

    static {
        ENUM_VALUES.add( "true" );
        ENUM_VALUES.add( "false" );
    }

    /**
     * Construct a new boolean validator which inserts "true" and "false" into an {@link EnumValidator}
     */
    public BooleanValidator() {
        super( ENUM_VALUES );
    }

    @Override
    public ParamType getType() {
        return ParamType.BOOL;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        String values = (String) super.validate( input, commandSender );
        if ( values == null ) {
            return null;
        }

        return values.equals( "true" ) ? Boolean.TRUE : Boolean.FALSE;
    }

}
