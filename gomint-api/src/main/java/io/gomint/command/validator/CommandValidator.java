/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandValidator extends EnumValidator {

    public CommandValidator() {
        super( null );
    }

    @Override
    public ParamType getType() {
        return ParamType.COMMAND;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        return input.get( 0 ).equals( values().get( 0 ) ) ? true : null;
    }

    @Override
    public int consumesParts() {
        return 1;
    }

    @Override
    public String getHelpText() {
        return "commandName";
    }

}
