/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate( String input, CommandSender commandSender ) {
        String values = (String) super.validate( input, commandSender );
        if ( values == null ) {
            return null;
        }

        return values.equals( "true" ) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.BOOL;
    }

}
