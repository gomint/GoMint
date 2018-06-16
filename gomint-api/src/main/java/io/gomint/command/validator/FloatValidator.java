package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FloatValidator extends ParamValidator {

    @Override
    public ParamType getType() {
        return ParamType.FLOAT;
    }

    @Override
    public boolean hasValues() {
        return false;
    }

    @Override
    public List<String> values() {
        return null;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        String toCheck = input.get( 0 );

        try {
            return Float.parseFloat( toCheck );
        } catch ( NumberFormatException e ) {
            return null;
        }
    }

    @Override
    public int consumesParts() {
        return 1;
    }

    @Override
    public String getHelpText() {
        return "float";
    }

}
