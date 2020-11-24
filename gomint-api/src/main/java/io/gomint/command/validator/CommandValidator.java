/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;

import java.util.Iterator;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class CommandValidator extends EnumValidator {

    public CommandValidator() {
        super( null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate( String input, CommandSender commandSender ) {
        return input.equals( values().get( 0 ) ) ? true : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String consume( Iterator<String> data ) {
        if ( data.hasNext() ) {
            return data.next();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.COMMAND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHelpText() {
        return "commandName";
    }

}
